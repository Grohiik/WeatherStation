package Models

import (
	"time"

	"gorm.io/gorm"
)

type Devices struct {
	Name      string         `gorm:"column:device_name" json:"name"`
	CreatedAt time.Time      `json:"created_at"`
	DeletedAt gorm.DeletedAt `gorm:"index" json:"-"`
	ID        uint           `gorm:"primarykey" json:"-"`
	UpdatedAt time.Time      `json:"-"`
	DataTypes []Data_Types   `json:"-"`
}

type Data_Types struct {
	Name      string    `json:"name"`
	Count     uint      `json:"count"`
	Unit      string    `json:"unit"`
	CreatedAt time.Time `json:"created_at"`

	DeviceID  uint           `json:"-"`
	DeletedAt gorm.DeletedAt `gorm:"index" json:"-"`
	ID        uint           `gorm:"primarykey" json:"-"`
	UpdatedAt time.Time      `json:"-"`
	Datas     []Data_Stored  `json:"-"`
}

type Data_Stored struct {
	Value string `json:"value"`
	Time  string `json:"time"`

	Data_TypesID uint           `gorm:"column:type_id" json:"-"`
	DeletedAt    gorm.DeletedAt `gorm:"index" json:"-"`
	ID           uint           `gorm:"primarykey" json:"-"`
	UpdatedAt    time.Time      `json:"-"`
	CreatedAt    time.Time      `json:"-"`
	Datas        []Data_Stored  `json:"-"`
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
