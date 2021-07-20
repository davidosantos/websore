FROM docker.io/library/webstore:0.0.1
COPY target/webstore-0.0.1.jar /webstore-0.0.1.jar
ENV TIMEZONE="America/Sao_Paulo"
EXPOSE 8080
ENTRYPOINT exec java -Duser.timezone=$TIMEZONE -jar /webstore-0.0.1.jar