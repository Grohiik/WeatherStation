/**
 * @file    PoseidonCore.hpp
 * @author  Pratchaya Khansomboon (pratchaya.k.git@gmail.com)
 * @brief   Poseidon core configurations
 * @version 0.1.0
 * @date    2021-04-08
 *
 * @copyright Copyright (c) 2021
 *
 */
#pragma once
// clang-format off

#ifndef POSEIDON_CONFIGURATION
constexpr auto WIFI_SSID      = "YOUR_SSID";
constexpr auto WIFI_PASSWORD  = "YOUR_WIFI_PASSWORD";
constexpr auto MQTT_BROKER_IP = "YOUR_BROKER_IP";
constexpr auto MQTT_USERNAME  = "YOUR_BROKER_USERNAME";
constexpr auto MQTT_KEY       = "YOUR_MQTT_KEY";

constexpr auto WEATHER_TOPIC = "/weather";
#endif

#ifndef POSEIDON_MAJOR_VERSION
#define POSEIDON_MAJOR_VERSION 0
#endif
#ifndef POSEIDON_MINOR_VERSION
#define POSEIDON_MINOR_VERSION 0
#endif
#ifndef POSEIDON_PATCH_VERSION
#define POSEIDON_PATCH_VERSION 0
#endif
