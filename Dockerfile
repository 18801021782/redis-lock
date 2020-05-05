FROM java:7
ADD target/api-1.0-SNAPSHOT.war /home/api.war
EXPOSE 80
WORKDIR /home
ENTRYPOINT ["java","-jar", "-XX:MaxPermSize=256m",  "-Xmx2000m", "api.war"]