# Weather Station - Embedded System

The Weather Station project is an embedded system with sensor suite, such as temperature and humidity. It sends the collected data to a centralized server for later storage and analysis.

## Requirements

**Software**

 - [PlatformIO](https://platformio.org)

**Hardware**

 - ESP32

## Development

### Configurations

Create `PoseidonConfig.hpp` file in `src` directory. Use the sample config below.

```cpp
#pragma once

#define WIFI_SSID      "YOUR SSID"
#define WIFI_PASSWORD  "YOUR WIFI PASSWORD"
#define MQTT_BROKER_IP "YOUR BROKER IP"
#define MQTT_USERNAME  "YOUR USERNAME"
#define MQTT_KEY       "YOUR KEY"

constexpr auto TEMPERATURE_TOPIC = "/temperature";
```

### PlatformIO

To upload the program you'll need to add these to the `platformio.ini` file.

```
upload_port = <your-port>
monitor_port = <your-port>
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
├─ lib
├─ src
│  └─ WeatherStation.cpp
├─ test
├─ .clang-format
├─ .gitignore
├─ .platformio.ini
└─ README.md
```
