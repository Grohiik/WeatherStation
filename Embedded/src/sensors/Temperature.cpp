#pragma once

#include "Temperature.hpp"

DHTesp dht;

/**
 * @brief setsup the temperatur and humidity sensor
 */
void setupTemperatureAndHumiditySensor() { dht.setup(13, DHTesp::DHT22); }

/**
 * @returns Temperature and humidity
 */
TempAndHumidity getTemp() {
    auto values = dht.getTempAndHumidity();
    return values;
}
