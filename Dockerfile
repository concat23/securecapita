# ============== VERSION 1 ===============
#FROM maven:3.8.2-jdk-8
#
#WORKDIR /ecommerce
#COPY . .
#RUN mvn clean install
#
#CMD mvn spring-boot:run
# ============= VERSION 2 ================
FROM maven:3.8.2 AS build
WORKDIR /securecapita
COPY pom.xml /securecapita
RUN mvn dependency:resolve
COPY . /securecapita
RUN mvn clean
RUN mvn package -DskipTests

FROM openjdk:8-jdk-oracle
COPY --from=build /securecapita/target/*.jar securecapita.jar
EXPOSE 8080
CMD ["java","-jar","securecapita.jar"]