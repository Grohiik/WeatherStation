package main

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
	Time         string
}
