# 1단계: Gradle로 Spring Boot 애플리케이션 빌드
FROM --platform=linux/arm64 gradle:8.4-jdk17 AS build
WORKDIR /app

# Gradle 캐시 최적화를 위해 Wrapper 포함한 파일 먼저 복사
COPY gradlew build.gradle settings.gradle ./
COPY gradle gradle
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon || true

# 애플리케이션 전체 복사 및 빌드
COPY . .
RUN ./gradlew bootJar --no-daemon

# 2단계: 런타임 스테이지
FROM --platform=linux/arm64 openjdk:17-jdk
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 4444
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
