import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	kotlin("plugin.jpa") version "1.6.10"
	kotlin("plugin.allopen") version "1.4.32"
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

group = "alahyaoui.escooter"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Coroutines Dependencies
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

	// Hibernate Dependencies
	implementation("org.hibernate:hibernate-spatial")

	// Postgis Dependencies
	implementation("net.postgis:postgis-jdbc:2021.1.0")

	// JTS Dependencies
	implementation("org.locationtech.jts:jts-core:1.16.0")

	// Jackson Dependencies
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.graphhopper.external:jackson-datatype-jts:0.10-2.5-1")

	// CSV Dependencies
	implementation("org.apache.commons:commons-csv:1.5")

	// Spring Boot dependencies
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// SpringDoc OpenApi Dependencies
	implementation("org.springdoc:springdoc-openapi-ui:1.6.15")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.15")

	// Test Dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "junit")
		exclude(module = "mockito-core")
	}
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("com.ninja-squad:springmockk:3.0.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}