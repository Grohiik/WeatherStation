#include "Log.hpp"

#include <SD.h>
#include <stdio.h>

#include "sensors/BatteryVoltage.hpp"
#include "sensors/Clock.hpp"

namespace poseidon {

    Log::Log() : m_isInit(false) {}
    Log::~Log() {}

    void Log::init() { m_isInit = SD.begin(SD_CARD_CHIP_SELECT); }

    void Log::logBattery() {
        if (!m_isInit) return;

        auto file = SD.open(BATTERY_LOG_FILENAME, FILE_APPEND);
        // Format to csv (time,battery) with newline at the end
        sprintf(m_buffer, "%u,%.3f\n", getUnixTime(), getBatteryVoltage());
        file.print(m_buffer);
        file.close();
    }

    void Log::logData(WeatherData& weather) {
        if (!m_isInit) return;

        auto file = SD.open(DATALOG_FILENAME, FILE_APPEND);
        sprintf(m_buffer, "%s\n", weather.toCSV(false));
        file.print(m_buffer);
        file.close();
    }

    void Log::logAllData(WeatherData& weather) {
        if (!m_isInit) return;

        auto file = SD.open(DATALOG_ALL_FILENAME, FILE_APPEND);
        sprintf(m_buffer, "%s\n", weather.toCSV(false));
        file.print(m_buffer);
        file.close();
    }

}  // namespace poseidon
