package parser

import (
	"fmt"
	"strings"

	"MqttMessageParser/model"

	"github.com/jinzhu/gorm"
)

var db *gorm.DB

func SetDatabaseConnection(conn *gorm.DB) {
	db = conn
}

func SplitStore(msg []byte) {
	fmt.Println("Parsing message....")
	var msgString = string(msg)
	lines := strings.Split(msgString, "\n")
	header := strings.Split(lines[0], ",")
	data := strings.Split(lines[1], ",")

	time := data[1]
	deviceName := data[0]

	device := storeDevice(deviceName)
	for i := 2; i < len(header); i++ {
		subHeader := strings.Split(header[i], ":")
		dataType := storeType(device.ID, subHeader[0], subHeader[1])
		storeData(dataType.ID, data[i], time)
	}
	fmt.Println("done")
}

func storeDevice(name string) model.Devices {
	device := model.Devices{}
	db.FirstOrCreate(&device, model.Devices{Name: name})

	return device
}

func storeType(deviceid uint, name string, unit string) model.Data_Types {
	dataType := model.Data_Types{}
	db.FirstOrCreate(&dataType, model.Data_Types{Unit: unit, Name: name, DeviceID: deviceid})

	return dataType
}

func storeData(typeid uint, data string, time string) {
	dataPoint := model.Data_Stored{Value: data, Time: time, Data_TypesID: typeid}

	db.Create(&dataPoint)
	db.Model(&model.Data_Types{}).Where("id = ?", typeid).Update("count", gorm.Expr("count + ?", 1))

}
