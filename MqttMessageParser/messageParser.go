package main

import (
	"fmt"
	"strings"

	"github.com/jinzhu/gorm"
)

func splitStore(msg []byte) {
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

func storeDevice(name string) Devices {
	device := Devices{}
	db.FirstOrCreate(&device, Devices{Name: name})

	return device
}

func storeType(deviceid uint, name string, unit string) Data_Types {
	dataType := Data_Types{}
	db.FirstOrCreate(&dataType, Data_Types{Unit: unit, Name: name, DeviceID: deviceid})

	return dataType
}

func storeData(typeid uint, data string, time string) {
	dataPoint := Data_Stored{Value: data, Time: time, Data_TypesID: typeid}

	db.Create(&dataPoint)
	db.Model(&Data_Types{}).Where("id = ?", typeid).Update("count", gorm.Expr("count + ?", 1))

}