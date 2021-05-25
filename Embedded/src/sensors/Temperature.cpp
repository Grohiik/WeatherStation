/**
 * @file    WeatherData.hpp
 * @author  Linnéa Mörk
 * @version 0.1.0
 * @date    2021-04-12
 *
 * @copyright Copyright (c) 2021
 *
 */

#include "Temperature.hpp"

DHTesp dht;

/**
 * @brief setsup the temperatur and humidity sensor
 */
void setupTemperatureAndHumiditySensor() { dht.setup(A7, DHTesp::DHT22); }

/**
 * @returns Temperature and humidity
 */
TempAndHumidity getTemp() {
    auto values = dht.getTempAndHumidity();
    return values;
}
