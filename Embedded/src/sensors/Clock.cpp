#include "Clock.hpp"

RTC_PCF8523 rtc;

/**
 * @brief setsup the RCT
 */
void setupRTC() {
    rtc.begin();
}

/**
 * @returns Current unix time
 */
uint32_t getUnixTime() {
    if (rtc.initialized()) {
        return rtc.now().unixtime();
    }
    return 0;
}