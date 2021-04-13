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
#include <SPI.h>
#include <SD.h>
#include <WiFi.h>
#include <MQTT.h>

#include "sensors/Temperature.hpp"
#include "sensors/LightSensor.hpp"
#include "sensors/Clock.hpp"
#include "sensors/BatteryVoltage.hpp"

#ifdef POSEIDON_CONFIGURATION
#include "PoseidonEnv.hpp"
#endif
#include "PoseidonCore.hpp"

// Filenames
constexpr auto DATALOG_FILENAME = "/datalog.csv";
constexpr auto SD_CARD_CHIP_SELECT = 33;

// Sleep configurations
constexpr auto uS_TO_S_FACTOR = 1000000U;  // μs to s conversion factor
constexpr auto TIME_TO_SLEEP = 10U;        // Time to sleep in seconds
constexpr auto MAX_CONNECTION_TRIES = 1U;  // Connection max tries

// Header
constexpr auto MESSAGE_HEADER = "device,time,temp,hum,ligh,batv";
constexpr auto MESSAGE_BUFFER_SIZE = 256;
// Boards baud rate
constexpr auto BAUD_RATE = 115200U;

char messageBuffer[MESSAGE_BUFFER_SIZE];

// Objects for the sensor components
WiFiClient wifi;
MQTTClient client;
File dataLogger;

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
            Serial.println();
            return false;
        }
        counter++;
    }
    counter = 0;
    Serial.println("\nConnected to WiFi!");

    Serial.println("\nConnecting to MQTT...");
    while (!client.connect("station1", MQTT_USERNAME, MQTT_KEY)) {
        Serial.print(".");
        delay(1000);
        if (counter >= MAX_CONNECTION_TRIES) {
            Serial.println();
            return false;
        }
        counter++;
    }
    Serial.println("\nConnected to MQTT!");

    return true;
}

/**
 * @brief Puts the micro controller into deep sleep for TIME_TO_SLEEP seconds.
 *        Is called whenever it is done with sending or writing to SD-Card
 */
void sleep() {
    Serial.println("Going to sleep...");
    esp_sleep_enable_timer_wakeup(TIME_TO_SLEEP * uS_TO_S_FACTOR);
    esp_deep_sleep_start();
}

/**
 * @brief Publishes data to WEATHER_TOPIC
 */
void send(const char* data) {
    String message = MESSAGE_HEADER;
    message += "\n";
    message += data;
    client.publish(WEATHER_TOPIC, message, false, 2);
}

/**
 * @brief setup and main "loop" starts all sensors, collects all data and
 * decides what to so next
 */
void setup() {
    delay(500);

    Serial.begin(BAUD_RATE);
    const bool sdStatus = SD.begin(SD_CARD_CHIP_SELECT);

    Serial.println("Waking up");
    setupTemperatureAndHumiditySensor();
    setupLightsensor();

    // Connect to WiFi
    WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
    client.begin(MQTT_BROKER_IP, wifi);

    TempAndHumidity tempandhumidity = getTemp();

    const auto isConnected = connect();

    if (sdStatus && isConnected) {
        char* collectedData = nullptr;
        String sdCardData;

        dataLogger = SD.open(DATALOG_FILENAME, FILE_READ);

        const auto size = dataLogger.size();

        if (size != 0) collectedData = new char[size + 1];
        if (collectedData != nullptr) {
            size_t dataIndex = 0;
            while (dataLogger.available() && dataIndex < size) {
                collectedData[dataIndex] = dataLogger.read();
                dataIndex++;
            }

            dataLogger.close();
            collectedData[dataIndex] = 0;
            send(collectedData);
            delete[] collectedData;
        }

        dataLogger = SD.open(DATALOG_FILENAME, FILE_WRITE);
        if (dataLogger) dataLogger.print("");
        dataLogger.close();
    }

    sprintf(messageBuffer, "%s,%d,%f,%f,%f,%f", DEVICE_ID, getUnixTime(),
            tempandhumidity.temperature, tempandhumidity.humidity,
            getLightData().lux, getBatteryVoltage());
    if (isConnected) {
        send(messageBuffer);
    } else {
        Serial.println("Logging to SD-card...");
        dataLogger = SD.open(DATALOG_FILENAME, FILE_APPEND);
        if (dataLogger) dataLogger.println(messageBuffer);
        dataLogger.close();
    }

    sleep();
}

void loop() {}