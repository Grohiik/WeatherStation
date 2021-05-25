/**
 * @file    WeatherData.hpp
 * @author  Linnéa Mörk
 * @version 0.1.0
 * @date    2021-04-12
 *
 * @copyright Copyright (c) 2021
 *
 */

#pragma once

#include <Arduino.h>
#include "RTClib.h"

void setupRTC();
uint32_t getUnixTime();
