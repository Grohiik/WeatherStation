package Models

import (
	"gorm.io/gorm"
)

type Model struct {
	ID uint
}

type Devices struct {
	gorm.Model
	DataTypes []Data_Types
	Name      string `gorm:"column:device_name"`
}

type Data_Types struct {
	gorm.Model
	Datas    []Data_Stored
	DeviceID uint
	Name     string
	Count    uint
	Unit     string
}

type Data_Stored struct {
	gorm.Model
	Data_TypesID uint `gorm:"column:type_id"`
	Value        string
	Created      string
}

func (b *Devices) Devices() string {
	return "devices"
}

func (b *Data_Types) Data_types() string {
	return "data_types"
}

func (b *Data_Stored) Data_stored() string {
	return "data_stored"
}
