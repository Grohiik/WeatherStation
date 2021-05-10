package Models

import (
	"RestAPI/Config"
)

//GetAllDevices Fetch all device data
func GetAllDevices(devices *[]Devices) (err error) {
	if err = Config.DB.Find(devices).Error; err != nil {
		return err
	}
	return nil
}
