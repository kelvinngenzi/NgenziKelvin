FROM openjdk:23-jdk

WORKDIR /app

COPY . ./src/question01/

RUN javac src/question01/*.java -d out

CMD ["java", "-cp", "out", "question01.Main"]
