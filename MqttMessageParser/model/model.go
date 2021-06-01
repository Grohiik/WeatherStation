package model

import (
	"time"

	"gorm.io/gorm"
)

//Authour Eric Lundin

type Model struct {
	ID        uint8      `gorm:"autoIncrement;primary_key;"`
	CreatedAt time.Time  `gorm:"autoCreateTime"`
	UpdatedAt time.Time  `gorm:"autoUpdateTime"`
	DeletedAt *time.Time `sql:"index"`
}

type Devices struct {
	gorm.Model
	DataTypes []Data_Types
	Name      string `gorm:"column:device_name;unique"`
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
