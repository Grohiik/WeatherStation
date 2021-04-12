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
#include "LightSensor.hpp"

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

// Objects for the sensor components
WiFiClient wifi;
MQTTClient client;
DHTesp dht;
RTC_PCF8523 rtc;


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

/**
 * @brief Puts the micro controller into deep sleep for TIME_TO_SLEEP seconds. Is called whenever it is done with sending or writing to SD-Card
 */
void sleep() {
    Serial.println("Going to sleep...");
    esp_sleep_enable_timer_wakeup(TIME_TO_SLEEP * uS_TO_S_FACTOR);
    esp_deep_sleep_start();
}

/**
 * @brief Publishes data to WEATHER_TOPIC
 */
void send(String& data) {
    data = header + data;
    client.publish(WEATHER_TOPIC, data, false, 2);
}

/**
 * @returns Temperature and humidity
 */
TempAndHumidity getTemp() {
    auto values = dht.getTempAndHumidity();
    return values;
}

/**
 * @returns Battery voltage
 */
float getBatv() {
    auto batteryValue = (analogRead(A13) / 4095.0) * 2 * 3.3 * 1.100;
    return batteryValue;
}

/**
 * @returns Current unix time
 */
uint32_t getUnixTime() {
    if (rtc.initialized()) {
        return rtc.now().unixtime();
    }
    return 0;
}

/**
 * @brief setup and main "loop" starts all sensors, collects all data and decides what to so next
 */
void setup() {
    delay(500);

    Serial.begin(BAUD_RATE);
    Serial.println("Waking up");

    dht.setup(13, DHTesp::DHT22);
    rtc.begin();
    setupLightsensor();


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
    // TODO: Make it better, it is shit
    String data = String(DEVICE_ID) + "," + String(getUnixTime()) + "," +
                  tempandhumidity.temperature + "," + tempandhumidity.humidity +
                  "," + String(getLightData().lux) + "," + String(getBatv());
    send(data);

    // TODO: Delete the logs from SD-card

    sleep();
}

void loop() {}