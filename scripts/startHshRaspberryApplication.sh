
if [[ $# -eq 0 ]] ; then
  echo 'You must specify the name your home and HshServer url!'
  exit 1
fi

if [ ! -d "logs" ]; then
  mkdir logs
fi

now=$(date +"%Y-%m-%d")
logfile="logs/hshapplication.$now.log"

sudo java -classpath .:classes:/opt/pi4j/lib/*:HshRaspberryApplication.jar se.beatit.hsh.raspberry.HshRaspberryApplication $1 $2 >> $logfile &
