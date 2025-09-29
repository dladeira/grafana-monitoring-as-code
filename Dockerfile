FROM maven:3.9.8-eclipse-temurin-21
WORKDIR /app
COPY . .
RUN mvn -q -e -B -DskipTests compile
ENTRYPOINT ["mvn", "-q", "-e", "-B", "-DskipTests", "exec:java", "-Dexec.mainClass=eu.ladeira.grafana.Main"]