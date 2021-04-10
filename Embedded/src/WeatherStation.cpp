/**
 * @file    WeatherStation.cpp
 * @author  Pratchaya Khansomboon (pratchaya.k.git@gmail.com)
 * @author  Linnéa Mörk
 * @brief   WeatherStation is a microcontroller with sensor suite for reading
 *          the environment. It reports back the data to a server for logging
 *          and for later analysis.
 * @version 0.2.0
 * @date    2021-03-31
 *
 * @copyright Copyright (c) 2021
 *
 */

#include <Arduino.h>
#include <Wire.h>

#include <WiFi.h>
#include <MQTT.h>
#include <DHTesp.h>
#include "RTClib.h"
#include <Adafruit_Sensor.h>
#include "Adafruit_TSL2591.h"

#ifdef POSEIDON_CONFIGURATION
#include "PoseidonEnv.hpp"
#endif
#include "PoseidonCore.hpp"

// Filenames
constexpr auto TEMP_FILENAME = "temperature.csv";

// Sleep configurations
constexpr auto uS_TO_S_FACTOR = 1000000U;   // μs to s conversion factor
constexpr auto TIME_TO_SLEEP = 120U;        // Time to sleep in seconds
constexpr auto MAX_CONNECTION_TRIES = 20U;  // Connection max tries

// Header
String header = "device,time,temp,hum,ligh,batv\n";

// Boards baud rate
constexpr auto BAUD_RATE = 115200U;

// WiFi and MQTT
WiFiClient wifi;
MQTTClient client;
DHTesp dht;
RTC_PCF8523 rtc;
Adafruit_TSL2591 tsl = Adafruit_TSL2591(2591);

struct LightSensor {
    uint16_t ir;
    uint16_t full;
    float lux;
};

/**
 * @brief Check WiFi connection status and connect to MQTT server.
 *
 * @return `true` successfully connected, `false` failed to connect to WiFi and
 *          MQTT server
 */
bool connect() {
    uint16_t counter = 0;
    Serial.print("Checking wifi...");
    while (WiFi.status() != WL_CONNECTED) {
        Serial.print(".");
        delay(1000);
        if (counter >= MAX_CONNECTION_TRIES) {
            return false;
        }
        counter++;
    }
    counter = 0;
    Serial.println("\nConnected to WiFi.");

    Serial.println("\nConnecting to MQTT...");
    while (!client.connect("station1", MQTT_USERNAME, MQTT_KEY)) {
        Serial.print(".");
        delay(1000);
        if (counter >= MAX_CONNECTION_TRIES) {
            return false;
        }
        counter++;
    }

    return true;
}

void sleep() {
    Serial.println("Going to sleep...");
    esp_sleep_enable_timer_wakeup(TIME_TO_SLEEP * uS_TO_S_FACTOR);
    esp_deep_sleep_start();
}

void send(String& data) {
    data = header + data;
    client.publish(WEATHER_TOPIC, data, false, 2);
}

TempAndHumidity getTemp() {
    auto values = dht.getTempAndHumidity();
    return values;
}

float getBatv() {
    auto batteryValue = (analogRead(A13) / 4095.0) * 2 * 3.3 * 1.100;
    return batteryValue;
}

uint32_t getUnixTime() {
    if (rtc.initialized()) {
        return rtc.now().unixtime();
    }
    return 0;
}

LightSensor getLightData() {
    LightSensor data;
    uint32_t rawData = tsl.getFullLuminosity();
    data.ir = rawData >> 16;
    data.full = rawData & 0xFFFF;
    data.lux = tsl.calculateLux(data.full, data.ir);
    return data;
}

void setup() {
    delay(500);

    Serial.begin(BAUD_RATE);
    Serial.println("Waking up");

    dht.setup(13, DHTesp::DHT22);
    rtc.begin();
    tsl.begin();
    tsl.setGain(TSL2591_GAIN_MED);
    tsl.setTiming(TSL2591_INTEGRATIONTIME_300MS);

    // TODO: Read temperature
    auto tempandhumidity = getTemp();

    // Connect to WiFi
    WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

    client.begin(MQTT_BROKER_IP, wifi);

    if (!connect()) {
        // TODO: Log to SD-CARD
        Serial.println("Failed to connect");
        sleep();
    }

    // TODO: Read from SD-card

    // TODO: Send all data
    String data = String(DEVICE_ID) + "," + String(getUnixTime()) + "," +
                  tempandhumidity.temperature + "," + tempandhumidity.humidity +
                  "," + String(getLightData().lux) + "," + String(getBatv());
    send(data);

    // TODO: Delete the logs from SD-card

    sleep();
}

void loop() {}