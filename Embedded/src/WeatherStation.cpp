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
#include <WiFi.h>
#include <WiFiMulti.h>
#include <MQTT.h>
#include <SD.h>

#include "PoseidonCore.hpp"
#include "WeatherData.hpp"

// clang-format off

constexpr auto uS_TO_S_FACTOR       = 1000000U;        // μs to s conversion factor
constexpr auto TIME_TO_SLEEP        = 900U;            // Time to sleep in seconds
constexpr auto BAUD_RATE            = 115200U;         // Boards baud rate
constexpr auto MAX_CONNECTION_TRIES = 60U;
constexpr auto DATALOG_FILENAME     = "/datalog.csv";  // Filename
constexpr auto SD_CARD_CHIP_SELECT  = 33;

// clang-format on

// Global objects
WiFiMulti wifiMulti;                // WiFi multi access point
WiFiClient wifiClient;              // WiFi Client for MQTT
MQTTClient mqttClient;              // MQTT connection client
poseidon::WeatherData weatherData;  // Weather data collection and storage

/**
 * @brief Check WiFi connection status and connect to MQTT server.
 *
 * @return `true` successfully connected, `false` failed to connect to WiFi and
 *          MQTT server
 */
bool connect() {
    uint16_t counter = 0;
    POSEIDON_LOG("Checking WiFi...\n");

    while (wifiMulti.run() != WL_CONNECTED) {
        POSEIDON_LOG(".");
        delay(1000);
        if (counter >= MAX_CONNECTION_TRIES) {
            POSEIDON_LOG("\n");
            return false;
        }
        counter++;
    }
    POSEIDON_LOG("\nConnected to WiFi!\n");
    counter = 0;

    POSEIDON_LOG("\nConnecting to MQTT...\n");
    while (!mqttClient.connect("station1", MQTT_USERNAME, MQTT_KEY)) {
        POSEIDON_LOG(".");
        delay(250);
        if (counter >= MAX_CONNECTION_TRIES / 2) {
            POSEIDON_LOG("\n");
            return false;
        }
        counter++;
    }

    POSEIDON_LOG("\nConnected to MQTT!\n");
    return true;
}

/**
 * @brief Publishes data to WEATHER_TOPIC
 */
void send(const char* data) { mqttClient.publish(MQTT_TOPIC, data, false, 2); }

/**
 * @brief Puts the micro controller into deep sleep for TIME_TO_SLEEP seconds.
 *        Is called whenever it is done with sending or writing to SD-Card
 */
void sleep() {
    POSEIDON_LOG("SLEEP");
    esp_sleep_enable_timer_wakeup(TIME_TO_SLEEP * uS_TO_S_FACTOR);
    esp_deep_sleep_start();
}

void setup() {
    POSEIDON_SET_BAUD_RATE(BAUD_RATE);

    weatherData.init();
    mqttClient.begin(MQTT_BROKER_IP, wifiClient);

    wifiMulti.addAP(WIFI_SSID, WIFI_PASSWORD);
    wifiMulti.addAP(WIFI_SSID1, WIFI_PASSWORD1);

    const bool sdStatus = SD.begin(SD_CARD_CHIP_SELECT);
    weatherData.collectData();

    const bool connectionStatus = connect();

    if (connectionStatus) {
        send(weatherData.toCSV());
    } else {
        // TODO: Log to SD-card
    }

    sleep();
}

void loop() {}
