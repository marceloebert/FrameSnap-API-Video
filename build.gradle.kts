plugins {
	java
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
	id("org.sonarqube") version "4.4.1.3373"
}

sonarqube {
    properties {
        property("sonar.projectKey", "marceloebert_fiap-software-architecture-fastfood")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.organization", "marceloebert")
        property("sonar.login", System.getenv("SONAR_TOKEN"))
    }
}

group = "com.fiap"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("mysql:mysql-connector-java:8.0.30")
	implementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	compileOnly("org.projectlombok:lombok")
	//developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("software.amazon.awssdk:dynamodb:2.30.3")
	implementation("software.amazon.awssdk:dynamodb-enhanced:2.30.3")
	implementation("software.amazon.awssdk:aws-core:2.30.3")
	implementation("software.amazon.awssdk:sdk-core:2.30.3")
	implementation("software.amazon.awssdk:auth:2.30.3")
	implementation("software.amazon.awssdk:cognitoidentityprovider:2.20.116")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("software.amazon.awssdk:s3:2.20.112")
	implementation("software.amazon.awssdk:sqs:2.20.112")
	//implementation("org.springframework.boot:spring-boot-starter-security")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
