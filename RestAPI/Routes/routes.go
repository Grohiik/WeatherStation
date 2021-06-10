package Routes

import (
	"RestAPI/Controllers"

	"github.com/gin-gonic/gin"
)

/**
 * Routeclass
 *
 * @author Marcus Linné
 * @author Erik Kellgren
 * @version 0.1.0
 */

//SetupRouter ... Configure routes
func SetupRouter() *gin.Engine {
	r := gin.Default()
	grp1 := r.Group("api")
	{
		grp1.GET("/list/devices", Controllers.GetDevices)
		grp1.GET("/list/types/:deviceName", Controllers.GetDataTypes)
		grp1.GET("/data/:name/:type", Controllers.GetAllData)
		grp1.POST("/storedata", Controllers.StoreData)
	}
	return r
}
