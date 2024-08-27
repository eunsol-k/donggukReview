FROM openjdk:17
VOLUME /tmp
ENTRYPOINT ["java","-jar","/donggukReview.jar","--spring.profiles.active=prod"]