#pragma once

#include <Arduino.h>
#include <DHTesp.h>

void setupTemperatureAndHumiditySensor();

TempAndHumidity getTemp();
