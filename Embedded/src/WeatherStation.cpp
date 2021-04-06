/**
 * @file    WeatherStation.cpp
 * @author  Pratchaya Khansomboon (pratchaya.k.git@gmail.com)
 * @author  Linnéa Mörk
 * @brief   WeatherStation is a microcontroller with sensor suite for reading
 *          the environment. It reports back the data to a server for logging
 *          and for later analysis.
 * @version 0.0.0
 * @date    2021-03-31
 *
 * @copyright Copyright (c) 2021
 *
 */

#include <Arduino.h>
#include <Wire.h>

#include <WiFi.h>
#include <WiFiMulti.h>

#ifdef USE_WIFI_CONFIG
#include "PoseidonConfig.hpp"
#else
#define WIFI_SSID     "YOUR SSD ID HERE"
#define WIFI_PASSWORD "YOUR WIFI PASSWORD HERE"
#endif

#define BAUD_RATE 115200

// WiFi configurations
constexpr auto SSID = WIFI_SSID;
constexpr auto PASSWORD = WIFI_PASSWORD;

// Filenames
constexpr auto TEMP_FILENAME = "temperature.csv";

// MQTT topics
constexpr auto TEMPERATURE_TOPIC = "/temperature";

// WiFi object
WiFiMulti wifi;

void setup() {
    Serial.begin(BAUD_RATE);

    // Read temperature

    // Connect to WiFi
    wifi.addAP(SSID, PASSWORD);
}

void loop() {
    // Read from SD-card

    // Try to send
    if ((wifi.run()) == WL_CONNECTED) {
        Serial.println("We're connected!");
        delay(3000);
    }

    // Log to SD-card if failed

    // Sleep
}
