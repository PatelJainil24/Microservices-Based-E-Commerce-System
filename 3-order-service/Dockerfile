FROM openjdk:17-oracle

ENV SPRING_PROFILES_ACTIVE cloud

COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar 


ENTRYPOINT [ "java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-jar","order-service.jar" ]

#	java -Dspring.profiles.active=dev -jar <jar-Name>

