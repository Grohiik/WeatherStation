package Routes

import (
	"RestAPI/Controllers"

	"github.com/gin-gonic/gin"
)

//SetupRouter ... Configure routes
func SetupRouter() *gin.Engine {
	r := gin.Default()
	grp1 := r.Group("/device-api")
	{
		grp1.GET("device", Controllers.GetDevices)
		// grp1.POST("device", Controllers.CreateDevice)
		// grp1.GET("device/:id", Controllers.GetDeviceByID)
		// grp1.PUT("device/:id", Controllers.UpdateDevice)
		// grp1.DELETE("device/:id", Controllers.DeleteDevice)
	}
	return r
}
