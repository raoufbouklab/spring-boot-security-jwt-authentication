FROM openjdk:11

VOLUME /tmp

EXPOSE 8082

ADD target/springsecurity-jwt-bookstore.jar springsecurity-jwt-bookstore.jar

ENTRYPOINT ["java", "-jar", "springsecurity-jwt-bookstore.jar"]