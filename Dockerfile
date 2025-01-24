### Build
FROM amazoncorretto:21.0.4-alpine AS builder
COPY . /
RUN ./gradlew clean build

#### Run
FROM amazoncorretto:21.0.4-alpine
RUN apk update && \
    apk --no-cache add tzdata; \
    cp /usr/share/zoneinfo/America/Argentina/Buenos_Aires /etc/localtime; \
	echo "America/Argentina/Buenos_Aires" > /etc/timezone; \
	apk del tzdata; \
    apk upgrade zlib libssl3 libcrypto3 && \
	mkdir /app
WORKDIR /app
COPY --from=builder /build/libs/app.jar app.jar
EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar"]
