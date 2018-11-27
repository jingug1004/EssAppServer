# [juniverse] test 서버 배포 스크립트.
# 필요시에는 이 파일을 복사해서 아래 변수들 바꿔서 사용해야 할꺼임.

# target 폴더 안에 생성되는 war파일명이어야 함
ARTIFACT_NAME="noblapp"

# 설치할 서버 주소
REMOTE_URL="172.30.1.15"
# 설치 서버의 tomcat 위치
REMOTE_TOMCAT_PATH="/opt/tomcat"

# 클라이언트 복사 포함 처리 START
if [ "$1" == "-c" ] ; then

# 노블앱 클라이언트 복사.
# 노블앱 클라이언트도 별도의 git로 관리한다면, 그것을 clone하고 build하는 스크립트를 추가
# 그 후 빌드 소스를 복사하는 처리를 넣으면 되겠다.
	CLIENT_SRC_PATH="../noblmap"

	echo "building client..."
	cd $CLIENT_SRC_PATH
# git 가져오기도 할까?
	git pull origin master
	npm install
	npm run build

# 복사 위치는 'src/main/webapp/client/'
	echo "copying client file..."
	cd -
	rm -rf src/main/webapp/client/*
	cp -r $CLIENT_SRC_PATH/www/* src/main/webapp/client/

fi
# 클라이언트 복사 skip... END

# 스프링 소스 빌드
mvn clean install

# .war파일 복사
echo "copying .war file..."
scp target/$ARTIFACT_NAME.war noblapp@$REMOTE_URL:~/
ssh -t noblapp@$REMOTE_URL "sudo cp $ARTIFACT_NAME.war $REMOTE_TOMCAT_PATH/webapps/"

# .war파일 권한 수정
echo "chaning owner of .war file..."
ssh -t noblapp@$REMOTE_URL "sudo chown tomcat:tomcat $REMOTE_TOMCAT_PATH/webapps/$ARTIFACT_NAME.war"

# tomcat 재시작
echo "restarting tomcat"
ssh -t noblapp@$REMOTE_URL "sudo systemctl restart tomcat"

# .war파일 삭제
echo "removing war file"
ssh -t noblapp@$REMOTE_URL "sudo rm $ARTIFACT_NAME.war"
