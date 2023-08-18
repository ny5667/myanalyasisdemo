#!/bin/bash
HOST="192.168.220.77" # FTP服务器IP地址
USER="ADP"            # FTP账号
PASS="1"              # FTP密码
LOCAL_DIR="/home/user/gitlab/sesecd/service/src/main/resources/META-INF/init/custom/"  # 本地文件夹路径
REMOTE_DIR="/bap-server/bap-workspace/generate/SESECD_1.0.0/service/src/main/resources/META-INF/init/custom/"  # 远程文件夹路径

# 连接FTP服务器并切换到目标文件夹
lftp -u $USER,$PASS $HOST <<EOF
cd $REMOTE_DIR

# 开始上传
mirror -R $LOCAL_DIR .

# 退出lftp
exit
EOF
