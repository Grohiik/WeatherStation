package parser

import (
	"fmt"
	"strings"

	"RestAPI/Models"

	"github.com/jinzhu/gorm"
)

/**
 * Parserclass
 *
 * @author Eric Lundin
 * @version 0.1.0
 */

var db *gorm.DB

func SetDatabaseConnection(conn *gorm.DB) {
	db = conn
}

// SplitStore splits the mqtt message into multiple string arrays
// The data is then isolated and stored on the database
func SplitStore(msg string) {
	fmt.Println("Parsing message....")
	var msgString = msg
	//splits the message into two lines
	lines := strings.Split(msgString, "\n")
	//isolates each header element, the whole 1st line is the header
	header := strings.Split(lines[0], ",")
	//isolates the data elements in the second line of the message
	data := strings.Split(lines[1], ",")

	//the timestamp is always located in the second index of data
	time := data[1]
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
}

func storeDevice(name string) Models.Devices {
	//If a device with the same name does not exist create it
	//otherwise get said device
	device := Models.Devices{}
	db.FirstOrCreate(&device, Models.Devices{Name: name})

	return device
}

func storeType(deviceid uint, name string, unit string) Models.Data_Types {
	//If a datatype with the same name does not exist create it
	//otherwise get said datatype
	dataType := Models.Data_Types{}
	db.FirstOrCreate(&dataType, Models.Data_Types{Unit: unit, Name: name, DeviceID: deviceid})

	return dataType
}

func storeData(typeid uint, data string, time string) {
	//Store the data in the data_stored table and increment the count of its datatype
	dataPoint := Models.Data_Stored{Value: data, Time: time, Data_TypesID: typeid}

	db.Create(&dataPoint)
	db.Model(&Models.Data_Types{}).Where("id = ?", typeid).Update("count", gorm.Expr("count + ?", 1))

}
