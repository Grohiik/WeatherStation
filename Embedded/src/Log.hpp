#pragma once

#include <cstdint>

#include "WeatherData.hpp"

namespace poseidon {

    // clang-format off

    // ESP32-Feather connector
    constexpr auto SD_CARD_CHIP_SELECT   = 33U;
    constexpr auto LOG_BUFFER_SIZE       = 256;
    constexpr auto BATTERY_LOG_FILENAME  = "/battery.csv";
    constexpr auto DATALOG_FILENAME      = "/datalog.csv";
    constexpr auto DATALOG_ALL_FILENAME  = "/alldata.csv";

    // clang-format on

    class Log {
       public:
        Log();
        Log(const Log& log) = delete;
        ~Log();

        void init();

        void logBattery();
        void logData(WeatherData& weather);
        void logAllData(WeatherData& weather);

       private:
        bool m_isInit;
        char m_buffer[LOG_BUFFER_SIZE];
    };

}  // namespace poseidon
