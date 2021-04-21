#!/bin/sh

eval "ls -lh"

read -p "Enter the name of the backup file: " fileName

RESTORE_COMMAND="cat $fileName | docker exec -i database_weather-db_1 psql -U miku mikudb "

echo "restoring db"

echo "                     (_)   | |            
     _ __   ___  ___  ___ _  __| | ___  _ __  
    | '_ \ / _ \/ __|/ _ \ |/ _  |/ _ \| '_ \ 
    | |_) | (_) \__ \  __/ | (_| | (_) | | | |
    | .__/ \___/|___/\___|_|\__,_|\___/|_| |_|
    | |                                       
    |_|    
    "

eval $RESTORE_COMMAND
