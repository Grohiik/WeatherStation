/**
 * @file    WeatherData.hpp
 * @author  Pratchaya Khansomboon (pratchaya.k.git@gmail.com)
 * @brief
 * @version 0.1.0
 * @date    2021-04-12
 *
 * @copyright Copyright (c) 2021
 *
 */
#pragma once
#include <cstdint>

#include "PoseidonCore.hpp"
#include "PoseidonData.hpp"

#include "DHTesp.h"
#include "RTClib.h"
#include "Adafruit_Sensor.h"
#include "Adafruit_TSL2591.h"

#include "sensors/LightSensor.hpp"

namespace poseidon {

    constexpr auto MESSAGE_BUFFER = 256;
    constexpr auto WEATHER_DATA_HEADER = "device,time,temp,hum,light,batv";

    class WeatherData : public CSVData {
       public:
        WeatherData();
        ~WeatherData();

        void init();
        void collectData();
        char* toCSV(const bool& headerOn = true) override;

       public:
        uint32_t getTime() const { return m_time; }
        float getBattery() const { return m_batteryVoltage; }
        float getTemperature() const { return m_temperature; }
        float getHumidity() const { return m_humidity; }
        float getLux() const { return m_light.lux; }
        float getFull() const { return m_light.full; }
        float getIR() const { return m_light.ir; }

       private:
        char m_message[MESSAGE_BUFFER];

       private:
        uint32_t m_time;
        float m_batteryVoltage;
        float m_temperature;
        float m_humidity;
        LightSensor m_light;
    };

}  // namespace poseidon
