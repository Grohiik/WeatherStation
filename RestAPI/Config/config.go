package Config //Konfigurationsfil

import (
	"fmt"
	"os"

	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"github.com/joho/godotenv"
)

var DB *gorm.DB

type DBConfig struct {
	Host     string
	Port     int
	User     string
	DBName   string
	Password string
}

func BuildDBConfig() *DBConfig {
	e := godotenv.Load() //Load .env file
	if e != nil {
		fmt.Print(e)
	}

	username := os.Getenv("db_user")
	password := os.Getenv("db_pass")
	dbName := os.Getenv("db_name")
	dbHost := os.Getenv("db_host")

	dbConfig := DBConfig{
		Host:     dbHost,
		Port:     5432,
		User:     username,
		Password: password,
		DBName:   dbName,
	}
	return &dbConfig
}

func DbURL(dbConfig *DBConfig) string {
	return fmt.Sprintf("host=%s user=%s dbname=%s sslmode=disable password=%s", dbConfig.Host, dbConfig.User, dbConfig.DBName, dbConfig.Password) //Build connection string
}
