FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


FROM openjdk:8-alpine
ENV PW_HOME /opt/pinewise

WORKDIR ${PW_HOME}
# Download $GS_URL and unzip to $PW_HOME
RUN apk --no-cache add openssl wget unzip bash libc6-compat &&\
    mkdir -p ${PW_HOME}
RUN apk add --no-cache nss
RUN apk update && apk add --no-cache libc6-compat
# Install kubernetes java client uber jar
COPY --from=build /workspace/app/target/nbdemo-0.0.1-SNAPSHOT.jar ${PW_HOME}/


RUN addgroup --gid 2000 -S pw_group &&\
    adduser -S pw_user -G pw_group --uid 1000 &&\
    chown -R pw_user:pw_group ${PW_HOME}

USER pw_user
RUN mkdir ${PW_HOME}/work

CMD ["./nbdemo-0.0.1-SNAPSHOT.jar"]
