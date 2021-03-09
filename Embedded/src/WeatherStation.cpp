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

#define BAUD_RATE 115200

void setup() { Serial.begin(BAUD_RATE); }

void loop() {
    Serial.print("Hello, World!");
    delay(250);
}
