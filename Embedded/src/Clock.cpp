#include "Clock.hpp"
#include "RTClib.h"

RTC_PCF8523 rtc;

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