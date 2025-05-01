FROM openjdk:23-jdk

WORKDIR /app

COPY . ./src/question02/

RUN javac src/question02/*.java -d out

CMD ["java", "-cp", "out", "question02.RRATaxSystem"]
