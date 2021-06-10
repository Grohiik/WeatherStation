package Models

import (
	"RestAPI/Config"
	"fmt"
)

/**
 * Fetchingclass
 *
 * @author Marcus Linné
 * @author Erik Kellgren
 * @author Pratchaya Khansomboon
 * @version 0.1.0
 */

//GetAllDevices Fetches all devices.
func GetAllDevices(devices *[]Devices) (err error) {
	if err = Config.DB.Find(devices).Error; err != nil {
		return err
	}
	return nil
}

// Gets which types of data the choosen device has.
func GetDataTypesByDevice(dataTypes *[]Data_Types, deviceName string) (err error) {
	device := Devices{}
	Config.DB.First(&device, Devices{Name: deviceName})
	Config.DB.Find(&dataTypes, Data_Types{DeviceID: device.ID})

	return nil
}

func GetDataByDeviceAndDataType(data *[]Data_Stored, deviceName string, typeName string) (err error) {
	device := Devices{}
	dataType := Data_Types{}
	Config.DB.First(&device, Devices{Name: deviceName})
	Config.DB.First(&dataType, Data_Types{Name: typeName, DeviceID: device.ID})
	Config.DB.Find(data, Data_Stored{Data_TypesID: dataType.ID})

	fmt.Println(len(*data))

	return nil
}
