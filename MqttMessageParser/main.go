package main

import (
	"fmt"
	"os"
	"os/signal"
	"syscall"

	mqtt "github.com/eclipse/paho.mqtt.golang"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"github.com/joho/godotenv"
)

var db *gorm.DB //database

func init() {

	e := godotenv.Load() //Load .env file
	if e != nil {
		fmt.Print(e)
	}

	username := os.Getenv("db_user")
	password := os.Getenv("db_pass")
	dbName := os.Getenv("db_name")
	dbHost := os.Getenv("db_host")

	dbUri := fmt.Sprintf("host=%s user=%s dbname=%s sslmode=disable password=%s", dbHost, username, dbName, password) //Build connection string
	fmt.Println(dbUri)

	conn, err := gorm.Open("postgres", dbUri)
	if err != nil {
		fmt.Print(err)
	}

	db = conn

	db.DropTable(&Devices{}, &Data_Types{}, Data_Stored{})
	db.AutoMigrate(&Devices{}, &Data_Types{}, &Data_Stored{})

	mqttConnect()
}

var messagePubHandler mqtt.MessageHandler = func(client mqtt.Client, msg mqtt.Message) {
	fmt.Printf("received message : %s from topic: %s\n", msg.Payload(), msg.Topic())
	go splitStore(msg.Payload())
}

var connectHandler mqtt.OnConnectHandler = func(client mqtt.Client) {
	fmt.Println("Connected")
}

var connectLostHandler mqtt.ConnectionLostHandler = func(client mqtt.Client, err error) {
	fmt.Printf("Connection lost: %v", err)
}

func mqttConnect() {

	e := godotenv.Load() //Load .env file
	if e != nil {
		fmt.Print(e)
	}

	user := os.Getenv("mqtt_user")
	url := os.Getenv("mqtt_url")
	pass := os.Getenv("mqtt_pass")
	feed := os.Getenv("mqtt_feed")

	fmt.Println("Starting client")

	opts := mqtt.NewClientOptions()
	opts.AddBroker(url)
	opts.SetUsername(user)
	opts.SetPassword(pass)
	opts.SetDefaultPublishHandler(messagePubHandler)
	opts.OnConnect = connectHandler
	opts.OnConnectionLost = connectLostHandler
	client := mqtt.NewClient(opts)
	if token := client.Connect(); token.Wait() && token.Error() != nil {
		panic(token.Error())
	}
	subscribe(client, feed)
}

func main() {
	c := make(chan os.Signal, 1)
	signal.Notify(c, os.Interrupt, syscall.SIGTERM)

	<-c
}

func subscribe(client mqtt.Client, feed string) {
	topic := feed

	token := client.Subscribe(topic, 1, nil)

	token.Wait()
	fmt.Printf("Subscribed to: %s", topic)
}
