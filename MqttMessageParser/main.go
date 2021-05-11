package main

import (
	"fmt"
	"os"
	"os/signal"
	"syscall"
	"time"

	"MqttMessageParser/model"
	"MqttMessageParser/parser"

	mqtt "github.com/eclipse/paho.mqtt.golang"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"github.com/joho/godotenv"
)

var db *gorm.DB //database

func init() {
	fmt.Printf(`______  ______  _____________________   __
___  / / /_  / / /_  ____/___  _/__  | / /
__  /_/ /_  / / /_  / __  __  / __   |/ / 
_  __  / / /_/ / / /_/ / __/ /  _  /|  /  
/_/ /_/  \____/  \____/  /___/  /_/ |_/   
`)

	e := godotenv.Load() //Load .env file
	if e != nil {
		fmt.Print(e)
	}

	username := os.Getenv("db_user")
	password := os.Getenv("db_pass")
	dbName := os.Getenv("db_name")
	dbHost := os.Getenv("db_host")

	dbUri := fmt.Sprintf("host=%s user=%s dbname=%s sslmode=disable password=%s", dbHost, username, dbName, password) //Build connection string

	conn, err := gorm.Open("postgres", dbUri)
	if err != nil {
		fmt.Print(err)
	}

	db = conn

	// db.DropTableIfExists(&model.Devices{}, &model.Data_Types{}, model.Data_Stored{})
	db.AutoMigrate(&model.Devices{}, &model.Data_Types{}, &model.Data_Stored{})

	parser.SetDatabaseConnection(db)

	mqttConnect()
}

var messagePubHandler mqtt.MessageHandler = func(client mqtt.Client, msg mqtt.Message) {
	fmt.Printf("---> [%s] received message: \n%s\n---> from topic: %s\n",
		time.Now().Format("2006-01-02 15:04:05"),
		msg.Payload(),
		msg.Topic())

	go parser.SplitStore(msg.Payload())
}

var connectHandler mqtt.OnConnectHandler = func(client mqtt.Client) {
	fmt.Println("Connected")
}

var connectLostHandler mqtt.ConnectionLostHandler = func(client mqtt.Client, err error) {
	fmt.Printf("--->[%s] Connection to mqtt broker lost, shutting down",
		time.Now().Format("2006-01-02 15:04:05"))
	os.Exit(1)
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

	fmt.Printf("---> [%s] Starting client\n", time.Now().Format("2006-01-02 15:04:05"))

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
	fmt.Printf("Subscribed to: %s\n", topic)
}
