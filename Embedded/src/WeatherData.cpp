/**
 * @file    WeatherData.cpp
 * @author  Pratchaya Khansomboon (pratchaya.k.git@gmail.com)
 * @brief
 * @version 0.1.0
 * @date 2021-05-25
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

    WeatherData::WeatherData()
        : m_time(0), m_batteryVoltage(0), m_temperature(0), m_humidity(0) {}
    WeatherData::~WeatherData() {}

    void WeatherData::init() {
        setupRTC();
        setupLightsensor();
        setupTemperatureAndHumiditySensor();
    }

    void WeatherData::collectData() {
        m_batteryVoltage = getBatteryVoltage();
        m_light = getLightData();
        m_time = getUnixTime();
        auto tempHum = getTemp();
        m_temperature = tempHum.temperature;
        m_humidity = tempHum.humidity;
    }

    char* WeatherData::toCSV(const boolean& headerOn) {
        if (headerOn)
            sprintf(m_message, "%s\n%s,%u,%.3f,%.3f,%.3f,%.3f", DATA_HEADER,
                    DEVICE_ID, m_time, m_temperature, m_humidity, m_light.lux,
                    m_batteryVoltage);
        else
            sprintf(m_message, "%s,%u,%.3f,%.3f,%.3f,%.3f", DEVICE_ID, m_time,
                    m_temperature, m_humidity, m_light.lux, m_batteryVoltage);
        return m_message;
    }

}  // namespace poseidon
