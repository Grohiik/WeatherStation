#!/bin/sh

SLEEP_TIME=30m
readonly SLEEP_TIME

DATABASE_NAME="miku"
readonly DATABASE_NAME

BACKUP_COMMAND="docker exec -t database_weather-db_1 pg_dumpall -c -U miku > mikudb_`date +%d-%m-%Y"_"%H_%M_%S`.sql"

while :
do
    eval $BACKUP_COMMAND
    echo "                     (_)   | |            
         _ __   ___  ___  ___ _  __| | ___  _ __  
        | '_ \ / _ \/ __|/ _ \ |/ _  |/ _ \| '_ \ 
        | |_) | (_) \__ \  __/ | (_| | (_) | | | |
        | .__/ \___/|___/\___|_|\__,_|\___/|_| |_|
        | |                                       
        |_|    
        "
    echo `date +%d-%m-%Y"_"%H_%M_%S`
    sleep $SLEEP_TIME
done
