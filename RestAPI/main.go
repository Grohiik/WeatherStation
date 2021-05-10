package main

import (
	"RestAPI/Config"
	"RestAPI/Models"
	"RestAPI/Routes"
	"fmt"

	"github.com/jinzhu/gorm"
)

var err error

func main() {
	Config.DB, err = gorm.Open("postgres", Config.DbURL(Config.BuildDBConfig()))
	if err != nil {
		fmt.Println("Status:", err)
	}
	defer Config.DB.Close()
	Config.DB.AutoMigrate(&Models.Devices{})
	Config.DB.AutoMigrate(&Models.Data_Types{})
	Config.DB.AutoMigrate(&Models.Data_Stored{})
	r := Routes.SetupRouter()
	//running
	r.Run()
}
