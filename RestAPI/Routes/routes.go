package Routes

import (
	"RestAPI/Controllers"

	"github.com/gin-gonic/gin"
)

//SetupRouter ... Configure routes
func SetupRouter() *gin.Engine {
	r := gin.Default()
	grp1 := r.Group("/api")
	{
		grp1.GET("/list/devices", Controllers.GetDevices)
		grp1.GET("/list/types/:device_name", Controllers.GetDataTypes)
		grp1.GET("/list/datas/:name", Controllers.GetAllData)
		//grp1.GET("/data/:device/")
		// grp1.GET("device/:id", Controllers.GetDeviceByID)
		// grp1.PUT("device/:id", Controllers.UpdateDevice)
		// grp1.DELETE("device/:id", Controllers.DeleteDevice)
	}
	return r
}