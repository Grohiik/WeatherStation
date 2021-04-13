#include "BatteryVoltage.hpp"

#include <Arduino.h>

/**
 * @returns Battery voltage
 */
float getBatteryVoltage() {
    auto batteryValue = (analogRead(A13) / 4095.0) * 2 * 3.3 * 1.100;
    return batteryValue;
}