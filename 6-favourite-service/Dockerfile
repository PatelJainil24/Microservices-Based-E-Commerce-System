FROM openjdk:17-oracle

ENV SPRING_PROFILES_ACTIVE cloud

COPY target/favourite-service-0.0.1-SNAPSHOT.jar favourite-service.jar 

ENTRYPOINT [ "java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-jar","favourite-service.jar" ]

#	java -Dspring.profiles.active=dev -jar <jar-Name>

