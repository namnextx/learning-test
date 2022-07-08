FROM ghcr.io/namnextx/java-based-image:v1.0.0

VOLUME /tmp

# Add Spring boot app.jar to Container
ADD "target/*.jar" app.jar

ARG JAVA_OPTS

# Fire up our Spring boot app by default
CMD [ "sh", "c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./uramdom -jar /app.jar"]
