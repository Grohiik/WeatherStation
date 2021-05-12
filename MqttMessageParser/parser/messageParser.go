package parser

import (
	"MqttMessageParser/model"
	"fmt"
	"strconv"
	"strings"

	"github.com/jinzhu/gorm"
)

var db *gorm.DB

func SetDatabaseConnection(conn *gorm.DB) {
	db = conn
}

// SplitStore splits the mqtt message into multiple string arrays
// The data is then isolated and stored on the database
func SplitStore(msg []byte) error {
	fmt.Println("Parsing message....")
	var msgString = string(msg)
	//splits the message into two lines
	lines := strings.Split(msgString, "\n")
	//isolates each header element, the whole 1st line is the header
	header := strings.Split(lines[0], ",")
	//isolates the data elements in the second line of the message
	data := strings.Split(lines[1], ",")

	// the timestamp is always located in the second index of data
	// checks if the timestamp is valid, jank af but it works(kinda)
	time := data[1]
	i, err := strconv.Atoi(time)
	if err != nil {
		return err
	}
	fmt.Println("time valid", i)
	//the device name is always located at the first index of data
	deviceName := data[0]

	//stores and gets the device
	device := storeDevice(deviceName)
	//go through the datatypes in the header and store the corresponding data
	for i := 2; i < len(header); i++ {
		subHeader := strings.Split(header[i], ":")
		dataType := storeType(device.ID, subHeader[0], subHeader[1])
		storeData(dataType.ID, data[i], time)
	}
	fmt.Println("done")
	return err
}

// storeDevice stores and or returns devices with the name "name"
func storeDevice(name string) model.Devices {
	//If a device with the same name does not exist create it
	//otherwise get said device
	device := model.Devices{}
	db.FirstOrCreate(&device, model.Devices{Name: name})

	return device
}

// storeType stores and or gets a dataType for the specified device
func storeType(deviceid uint, name string, unit string) model.Data_Types {
	//If a datatype with the same name does not exist create it
	//otherwise get said datatype
	dataType := model.Data_Types{}
	db.FirstOrCreate(&dataType, model.Data_Types{Unit: unit, Name: name, DeviceID: deviceid})

	return dataType
}

// storeData stores the datapoints from the mqtt message for the specified datatype
func storeData(typeid uint, data string, time string) {
	//Store the data in the data_stored table and increment the count of its datatype
	dataPoint := model.Data_Stored{Value: data, Time: time, Data_TypesID: typeid}

	db.Create(&dataPoint)
	db.Model(&model.Data_Types{}).Where("id = ?", typeid).Update("count", gorm.Expr("count + ?", 1))

}
