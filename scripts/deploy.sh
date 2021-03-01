#!/usr/bin/env bash



echo 'Copy files...'

 scp -i ~/.ssh/id_rsa \ 
  	../target/pizza2-0.0.1-SNAPSHOT.jar \
 	andrew@46.254.21.5:/home/andrew/
 
 echo 'Restart server...'
 
 ssh -i ~/.ssh/id_rsa andrew@46.254.21.5 << EOF
 
 
 pgrep java | xargs kill -9
 nohup java -jar pizza2-0.0.1-SNAPSHOT.jar > log.txt &
 nohup java -jar pizza2.jar > log.txt &
 EOF
 
 echo 'Bye'