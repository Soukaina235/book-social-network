# ----- Build stage ------
# we need maven to install the dependencies
FROM maven:3.8.7-openjdk-18 AS build

# defining our working directory as .build (cd to it)
WORKDIR /build

# we copy pom.xml into the current location (which is /build)
COPY pom.xml .

# downloading the dependencies
# go-offline allow to download the dependencies even if we are offline
RUN mvn dependency:go-offline

# copy the src folder to the current location
COPY src ./src

# -DskipTests => we assume that everything is working: tests, compiling, ...
RUN mvn clean package -DskipTests

# ----- Runtime stage ------
# we need the jdk to run the java app
FROM amazoncorretto:17

# define few vars, that will be passed as arguments when we build our app
# dev is the default value in this case
ARG PROFILE=dev
# default is 1.0.O
ARG APP_VERSION=1.0.0

# moving to another directory
WORKDIR /app

# copying the jar file
# --from=build => referencing to the build stage
COPY --from=build /build/target/book-network-*.jar /app/

# our app runs in the port 8088, so we need to expose it
EXPOSE 8088

# this is a default value
# jdbc:postgresql://localhost:1234/book_social_network
# we can't call them directly, so we create new vars
ENV DB_URL=jdbc:postgresql://postgres-sql-bsn:5432/book_social_network
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}
ENV EMAIL_HOSTNAME=missing_hostname
ENV EMAIL_USER_NAME=missing_user_name
ENV EMAIL_PASSWORD=missing_password

# we need to run the jar
CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} book-network-${JAR_VERSION}.jar
