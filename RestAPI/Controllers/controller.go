package Controllers

import (
	"RestAPI/Models"
	"RestAPI/parser"
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
)

//GetDevices ... Gets all devices
func GetDevices(c *gin.Context) {
	var device []Models.Devices
	err := Models.GetAllDevices(&device)
	if err != nil {
		c.AbortWithStatus(http.StatusNotFound)
	} else {
		c.JSON(http.StatusOK, device)
	}
}

func GetDataTypes(c *gin.Context) {
	device := c.Params.ByName("device_name")
	var data_types []Models.Data_Types
	err := Models.GetDataTypesByDevice(&data_types, device)
	if err != nil {
		c.AbortWithStatus(http.StatusNotFound)
	} else {
		c.JSON(http.StatusOK, data_types)
	}
}

func GetAllData(c *gin.Context) {
	deviceName := c.Params.ByName("name")
	dataType := c.Params.ByName("type")

	var data_stored []Models.Data_Stored
	err := Models.GetDataByDeviceAndDataType(&data_stored, deviceName, dataType)
	if err != nil {
		c.AbortWithStatus(http.StatusNotFound)
	} else {
		c.JSON(http.StatusOK, data_stored)
	}
}

func StoreData(c *gin.Context) {
	buf := make([]byte, 1024)
	num, _ := c.Request.Body.Read(buf)
	reqBody := string(buf[0:num])

	fmt.Println(reqBody)
	go parser.SplitStore(reqBody)
}

/*

//CreateUser ... Create User
func CreateUser(c *gin.Context) {
	var user Models.User
	c.BindJSON(&user)
	err := Models.CreateUser(&user)
	if err != nil {
		fmt.Println(err.Error())
		c.AbortWithStatus(http.StatusNotFound)
	} else {
		c.JSON(http.StatusOK, user)
	}
}

//GetUserByID ... Get the user by id
func GetUserByID(c *gin.Context) {
	id := c.Params.ByName("id")
	var user Models.User
	err := Models.GetUserByID(&user, id)
	if err != nil {
		c.AbortWithStatus(http.StatusNotFound)
	} else {
		c.JSON(http.StatusOK, user)
	}
}

//UpdateUser ... Update the user information
func UpdateUser(c *gin.Context) {
	var user Models.User
	id := c.Params.ByName("id")
	err := Models.GetUserByID(&user, id)
	if err != nil {
		c.JSON(http.StatusNotFound, user)
	}
	c.BindJSON(&user)
	err = Models.UpdateUser(&user, id)
	if err != nil {
		c.AbortWithStatus(http.StatusNotFound)
	} else {
		c.JSON(http.StatusOK, user)
	}
}

//DeleteUser ... Delete the user
func DeleteUser(c *gin.Context) {
	var user Models.User
	id := c.Params.ByName("id")
	err := Models.DeleteUser(&user, id)
	if err != nil {
		c.AbortWithStatus(http.StatusNotFound)
	} else {
		c.JSON(http.StatusOK, gin.H{"id" + id: "is deleted"})
	}
}
*/
