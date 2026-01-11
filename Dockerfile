# 1. 실행용 JDK (가볍고 안정적인 버전)
FROM eclipse-temurin:17-jdk-jammy

# 2. 작업 디렉토리
WORKDIR /app

# 3. JAR 복사
COPY build/libs/*.jar app.jar

# 4. 포트 (Spring server.port)
EXPOSE 8080

# 5. 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]