# WeatherLogger - Backend

The WeatherLogger server will receive and handle data sent from the embedded system. This data will be stored in an SQL database to be used by the front-end. The server is built using spring: https://spring.io/ 

## Requirements

 - OpenJDK 15 (or other variants)
 - Maven (Optional)
 - IntelliJ (Optional)

## Development

Read through this to start developing. The project uses maven as build system and it is IDE agnostic. This means that you can use your favorite IDE or Editor.

Refer to [CodeStyle.md](/Docs/CodeStyle.md) in [Docs](/Docs) on how to get the tools for formatting the code.

### IntelliJ

For intelliJ you'll need to open the `pom.xml` and it'll prompt you if you want to open it as project or not. Choose `Open as Project`.

### Maven

There are different commands you can run to start the spring server. Check this [guide](https://docs.spring.io/spring-boot/docs/2.4.4/maven-plugin/reference/htmlsingle/#goals) for more information.

This will run the server and block the terminal input.

```
mvn spring-boot:run
```

This will run the server in the background and prints output, but you have access to the terminal.

```
mvn spring-boot:start
```

Build jar file

```shell
mvn clean install spring-boot:repackage
```

### Spring configurations

The file [sample.yml](/WeatherLogger/src/main/resources/sample.yml) in `src/main/resources` is the sample configuration for the server. The real file name should be named `application.yml` and spring framework will read it and use the configured data.

### WeatherLogger Project Structure

```
WeatherLogger
├─ src
│  ├─ main
│  │  ├─ java
│  │  │  └─ weather
│  │  │     ├─ controller
│  │  │     ├─ model
│  │  │     └─ WeatherApp.java
│  │  └─ resources
│  │     └─ application.yml
│  └─ test
│     └─ java
├─ .clang-format
├─ pom.xml
└─ README.md
```
