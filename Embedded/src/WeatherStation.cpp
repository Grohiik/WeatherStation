/**
 * @file    WeatherStation.cpp
 * @author  Pratchaya Khansomboon (pratchaya.k.git@gmail.com)
 * @author  Linnéa Mörk
 * @brief   WeatherStation is a microcontroller with sensor suite for reading
 *          the environment. It reports back the data to a server for logging
 *          and for later analysis.
 * @version 0.1.0
 * @date    2021-03-31
 *
 * @copyright Copyright (c) 2021
 *
 */

#include <Arduino.h>
#include <Wire.h>

#include <WiFi.h>
#include <MQTT.h>

#ifdef USE_WIFI_CONFIG
#include "PoseidonConfig.hpp"
#else
#define WIFI_SSID      "YOUR SSID"
#define WIFI_PASSWORD  "YOUR WIFI PASSWORD"
#define MQTT_BROKER_IP "YOUR BROKER IP"
#define MQTT_USERNAME  "YOUR USERNAME"
#define MQTT_KEY       "YOUR KEY"

constexpr auto TEMPERATURE_TOPIC = "/temperature";
#endif

// Filenames
constexpr auto TEMP_FILENAME = "temperature.csv";

// Sleep configurations
constexpr auto uS_TO_S_FACTOR = 1000000;   // μs to s conversion factor
constexpr auto TIME_TO_SLEEP = 20;         // Time to sleep in seconds
constexpr auto MAX_CONNECTION_TRIES = 10;  // Connection max tries

// Boards baud rate
constexpr auto BAUD_RATE = 115200;

// WiFi and MQTT
WiFiClient wifi;
MQTTClient client;

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
    }
    counter = 0;
    Serial.println("\nConnected to WiFi.");

    Serial.print("\nConnecting to MQTT...");
    while (!client.connect("station1", MQTT_USERNAME, MQTT_KEY)) {
        Serial.print(".");
        delay(1000);
        if (counter >= MAX_CONNECTION_TRIES) {
            return false;
        }
    }

    Serial.println("\nconnected!");
    client.publish(TEMPERATURE_TOPIC, "0");
    return true;
}

void sleep() {
    Serial.println("Going to sleep...");
    esp_sleep_enable_timer_wakeup(TIME_TO_SLEEP * uS_TO_S_FACTOR);
    esp_deep_sleep_start();
}

void setup() {
    delay(500);

    Serial.begin(BAUD_RATE);
    Serial.println("Waking up");

    // TODO: Read temperature

    // Connect to WiFi
    WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

    client.begin(MQTT_BROKER_IP, wifi);

    if (!connect()) {
        // TODO: log to SD-CARD

        sleep();
    }

    // TODO: Read from SD-card

    // TODO: send all data

    // TODO: delete the logs from SD-card

    Serial.println("Failed to connect");
    sleep();
}

void loop() {}
