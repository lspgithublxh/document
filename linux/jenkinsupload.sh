#!/bin/bash
echo 'hello'
dd=$1

function killproject(){
  kpid=$(netstat -lnp | grep 7010|awk '{print $7}'|cut -d/ -f1 )
  if [ $kpid > 0 ];then
	  echo "项目已经启动, pid = $kpid"
	  kill -9 $kpid
	  echo "项目已经关闭"
  else 
	  echo "项目尚未启动"
  fi

}

function startproject(){
   source /etc/profile
   echo '正在启动项目'
   cd /usr/local/jenkins-jar/
   cd $dd
  # nohup java -jar *.jar >wrapper.log &2>1 &
  # nohup java -jar *.jar &
  # nohup java -jar *.jar >/dev/null 2>&1 &
   nohup java -jar *.jar >wrapper.log 2>&1 &
   echo '执行启动命令成功'

}

function checkproject(){
  cpid=$(netstat -lnp | grep 7010|awk '{print $7}'|cut -d/ -f1)
  if [ $cpid > 0 ];then
	  echo "项目已经启动,项目pid  = $cpid"
  else
	  echo '项目启动失败'
  fi
}

killproject
startproject
#sleep 20
#checkproject
