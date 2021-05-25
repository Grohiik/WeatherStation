/**
 * @file    WeatherData.hpp
 * @author  Linnéa Mörk
 * @version 0.1.0
 * @date 2021-05-25
 *
 * @copyright Copyright (c) 2021
 *
 */

#include "LightSensor.hpp"
#include "Adafruit_TSL2591.h"

Adafruit_TSL2591 lightsensor = Adafruit_TSL2591(2591);

/**
 * @brief setsup the lightsensor
 */
void setupLightsensor() {
    lightsensor.begin();
    lightsensor.setGain(TSL2591_GAIN_MED);
    lightsensor.setTiming(TSL2591_INTEGRATIONTIME_300MS);
}

/**
 * @returns A struct with ir, full and lux from the light sensor
 */
LightSensor getLightData() {
    // TODO: Auto adjust light sensor GAIN
    LightSensor data;
    uint32_t rawData = lightsensor.getFullLuminosity();
    data.ir = rawData >> 16;
    data.full = rawData & 0xFFFF;
    data.lux = lightsensor.calculateLux(data.full, data.ir);
    return data;
}
