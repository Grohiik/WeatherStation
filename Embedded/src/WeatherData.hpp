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

namespace poseidon {

    constexpr auto MESSAGE_BUFFER = 256;
    constexpr auto WEATHER_DATA_HEADER = "device,time,temp,hum,light,batv";

    class WeatherData : public CSVData {
       public:
        WeatherData();
        ~WeatherData();

        void init();
        void collectData();
        char* toCSV() override;

        inline void setHeader(boolean isHeaderOn = true) {
            m_isHeaderOn = isHeaderOn;
        }

       private:
        char m_message[MESSAGE_BUFFER];
        bool m_isHeaderOn;

       private:
        float m_batteryVoltage;
        float m_temperature;
        float m_humidity;
        float m_lux;
    };

}  // namespace poseidon
