package Controllers

import (
	"RestAPI/Models"
	"RestAPI/parser"
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
)

/**
 * Controller
 *
 * @author Marcus Linné
 * @author Erik Kellgren
 * @version 0.1.0
 */

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
	device := c.Params.ByName("deviceName")
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
