/**
 * @file    WeatherData.hpp
 * @author  Linnéa Mörk
 * @version 0.1.0
 * @date 2021-05-25
 *
 * @copyright Copyright (c) 2021
 *
 */

#include "BatteryVoltage.hpp"

#include <Arduino.h>

/**
 * @returns Battery voltage
 */
float getBatteryVoltage() {
    auto batteryValue = (analogRead(A13) / 4095.0) * 2 * 3.3 * 1.100;
    return batteryValue;
}