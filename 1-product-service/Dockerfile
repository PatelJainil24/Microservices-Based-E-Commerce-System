FROM openjdk:17-oracle

ENV SPRING_PROFILES_ACTIVE cloud

COPY target/product-service-0.0.1-SNAPSHOT.jar product-service.jar 

ENTRYPOINT [ "java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-jar","product-service.jar" ]


