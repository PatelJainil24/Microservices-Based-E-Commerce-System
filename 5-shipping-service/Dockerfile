FROM openjdk:17-oracle

ENV SPRING_PROFILES_ACTIVE cloud

COPY target/shipping-service-0.0.1-SNAPSHOT.jar shipping-service.jar 

ENTRYPOINT [ "java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-jar","shipping-service.jar" ]

#	java -Dspring.profiles.active=dev -jar <jar-Name>

