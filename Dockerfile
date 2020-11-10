	FROM openjdk:11-jdk-alpine
	EXPOSE 8080
	ARG JAR_FILE=target/mafia-0.0.1-SNAPSHOT.jar
	ADD ${JAR_FILE} mafia-0.0.1-SNAPSHOT.jar
	ENTRYPOINT ["java","-jar","/mafia-0.0.1-SNAPSHOT.jar"]