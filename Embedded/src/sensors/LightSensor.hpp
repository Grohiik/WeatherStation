#pragma once

#include <Arduino.h>

void setupLightsensor();

struct LightSensor {
    uint16_t ir;
    uint16_t full;
    float lux;
};

LightSensor getLightData();
