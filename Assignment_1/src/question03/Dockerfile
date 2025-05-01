FROM openjdk:23-jdk

WORKDIR /app

COPY . ./src/question03/

RUN javac src/question03/*.java -d out

CMD ["java", "-cp", "out", "question03.InternshipManager"]





