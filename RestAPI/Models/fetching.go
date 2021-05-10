package Models

import (
	"RestAPI/Config"
)

//GetAllDevices Fetches all devices.
func GetAllDevices(devices *[]Devices) (err error) {
	if err = Config.DB.Find(devices).Error; err != nil {
		return err
	}
	return nil
}

// Gets which types of data the choosen device has.
func GetDataTypesByDevice(data_types *[]Data_Types, device string) (err error) {
	if err = Config.DB.Model(&device).Find(&data_types).Error; err != nil {
		return err
	}
	return nil
}

func GetDataByDeviceAndDataType(data *[]Data_Stored, type_name string, device_name string) (err error) {
	device := Devices{}
	data_type := Data_Types{}
	Config.DB.First(&device, Devices{Name: device_name})
	Config.DB.First(&data_type, Data_Types{Name: type_name, DeviceID: device.ID})
	Config.DB.Find(data, Data_Stored{Data_TypesID: data_type.ID})

	return nil
}
