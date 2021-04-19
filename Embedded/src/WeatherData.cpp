/**
 * @file    WeatherData.cpp
 * @author  Pratchaya Khansomboon (pratchaya.k.git@gmail.com)
 * @brief
 * @version 0.1.0
 * @date 2021-04-12
 *
 * @copyright Copyright (c) 2021
 *
 */

#include "WeatherData.hpp"

#include "PoseidonEnv.hpp"

#include "sensors/BatteryVoltage.hpp"
#include "sensors/Clock.hpp"
#include "sensors/LightSensor.hpp"
#include "sensors/Temperature.hpp"

namespace poseidon {

    WeatherData::WeatherData() {}
    WeatherData::~WeatherData() {}

    void WeatherData::init() {
        setupRTC();
        setupLightsensor();
        setupTemperatureAndHumiditySensor();
    }

    void WeatherData::collectData() {
        m_batteryVoltage = getBatteryVoltage();
        m_lux = getLightData().lux;
        auto tempHum = getTemp();
        m_temperature = tempHum.temperature;
        m_humidity = tempHum.humidity;
    }

    char* WeatherData::toCSV() {
        sprintf(m_message, "%s\n%s,%u,%.3f,%.3f,%.3f,%.3f", WEATHER_DATA_HEADER,
                DEVICE_ID, getUnixTime(), m_temperature, m_humidity, m_lux,
                m_batteryVoltage);
        return m_message;
    }

}  // namespace poseidon
