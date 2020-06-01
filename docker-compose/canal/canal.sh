docker pull canal/canal-server:v1.1.4
docker run --name canal -e canal.instance.master.address=192.168.2.185:3306 -e canal.instance.dbUsername=canal -e canal.instance.dbPassword=canal -p 11111:11111 -d canal/canal-server
