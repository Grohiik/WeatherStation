#include <Arduino.h>
#include <DHTesp.h>
#include "Temperature.hpp"

DHTesp dht;

void setupTemperatureAndHumiditySensor(){
    dht.setup(13, DHTesp::DHT22);
}

/**
 * @returns Temperature and humidity
 */
TempAndHumidity getTemp() {
    auto values = dht.getTempAndHumidity();
    return values;
}