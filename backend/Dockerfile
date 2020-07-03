FROM openjdk:11 AS BUILD_IMAGE
ENV APP_HOME=/root/dev/myapp/
RUN mkdir -p $APP_HOME/src/main/java
WORKDIR $APP_HOME
COPY build.gradle gradlew gradlew.bat $APP_HOME
COPY gradle $APP_HOME/gradle
COPY . .
RUN ./gradlew build

FROM openjdk:11
WORKDIR /root/
COPY --from=BUILD_IMAGE /root/dev/myapp/build/libs/*.jar myapp.jar
EXPOSE 8080
CMD ["java","-jar","myapp.jar"]