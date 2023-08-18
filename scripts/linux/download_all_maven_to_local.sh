#!/bin/bash

HOST="192.168.220.77"
USER="adp"
PASSWORD="1"

REMOTE_DIR="/bap-server/assembly/repository/maven/"
LOCAL_DIR="/home/user/repository77/"

lftp -u "$USER","$PASSWORD" $HOST <<EOF
mirror --use-pget-n=10 $REMOTE_DIR $LOCAL_DIR;
exit
EOF