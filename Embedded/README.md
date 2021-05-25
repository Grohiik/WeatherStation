# Weather Station - Embedded System

The Weather Station project is an embedded system with sensor suite, such as temperature and humidity. It sends the collected data to a centralized server for later storage and analysis.

## Requirements

**Software**

 - [PlatformIO](https://platformio.org)

**Hardware**

 - ESP32

## Development

### Configurations

Create `env.ini` file in the root of this project and include the values below with your own configs.

```ini
[env]
upload_port = <your-port>
monitor_port = <your-port>

# To use this you need to create PoseidonEnv.hpp
[env:featheresp32]
build_flags = 
    -DPOSEIDON_CONFIGURATION
    -DPOSEIDON_DEBUG
```

The `POSEIDON_CONFIGURATION` macro needs `PoseidonEnv.hpp` file to work. Create the file and copy the sample below and input your own config.

```cpp
#pragma once
// clang-format off

constexpr auto WIFI_SSID      = "YOUR_SSID";
constexpr auto WIFI_PASSWORD  = "YOUR_WIFI_PASSWORD";

constexpr auto MQTT_BROKER_IP = "YOUR_BROKER_IP";
constexpr auto MQTT_USERNAME  = "YOUR_BROKER_USERNAME";
constexpr auto MQTT_KEY       = "YOUR_MQTT_KEY";
constexpr auto DEVICE_ID      = "YOUR_DEVICE_ID";

constexpr auto MQTT_TOPIC     = "YOUR_MQTT_TOPIC";
```

### Visual Studio Code

- [PlatformIO](https://marketplace.visualstudio.com/items?itemName=platformio.platformio-ide)

### Code Formatting

Check [CodeStyle.md](/Docs/CodeStyle.md) for installation of `clang-format`.

`cd` into Embedded.

You can use this command to format the code. **Note**: when using Arduino IDE, it won't reload the file so you'll need to restart for it to see changes.

```
clang-format -i <filepath>
```

### Documentation

This project follows the [Doxygen](https://www.doxygen.nl/index.html) documentation style.

### Project Structure

```
Embedded
├─ include
│  └─ PoseidonCore.hpp
├─ lib
├─ src
│  ├─ PoseidonEnv.hpp      ← ignored
│  └─ WeatherStation.cpp
├─ test
├─ .clang-format
├─ .gitignore
├─ env.ini                 ← ignored
├─ .platformio.ini
└─ README.md
```
