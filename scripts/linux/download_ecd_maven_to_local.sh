#!/bin/bash

HOST="192.168.220.77"
USER="adp"
PASSWORD="1"

REMOTE_DIR="/bap-server/assembly/repository/maven/com/supcon/greendill/"
LOCAL_DIR="/home/user/repository77/com/supcon/greendill/"

function lftp_download() {
    # $1 REMOTE_DIR
    # $2 LOCAL_DIR
    echo $1 ">>>" $2
    lftp -u "$USER","$PASSWORD" $HOST <<EOF
    mirror --use-pget-n=100 "$1" "$2";
    exit
EOF
}


my_array=(SESWssER SESWssEP SESECD SESEAB SESGISConfig alarm VideoPlayWeb SESED)
for item in "${my_array[@]}"
do
  lftp_download $REMOTE_DIR$item $LOCAL_DIR$item
done
