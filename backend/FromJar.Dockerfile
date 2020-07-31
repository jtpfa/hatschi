FROM openjdk:11
ENV APP_HOME=/root/dev/myapp/
RUN mkdir -p $APP_HOME/src/main/java
WORKDIR $APP_HOME
COPY ./build/libs/*.jar myapp.jar
CMD ["java","-jar","myapp.jar"]