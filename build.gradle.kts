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

	// Tuto
	//implementation("com.vividsolutions:jts-core:1.14.0")
	implementation("com.bedatadriven:jackson-datatype-jts:2.4")
	//implementation("org.postgresql:postgresql:42.1.4")
	implementation("net.postgis:postgis-jdbc:2021.1.0")


	// Hibernate Dependencies
	//implementation("org.hibernate:hibernate-core")
	implementation("org.hibernate:hibernate-spatial")

	// Postgres Dependencies
	//runtimeOnly("org.postgresql:postgresql")
	//implementation("net.postgis:postgis-jdbc:2021.1.0")

	// JTS Dependencies
	implementation("org.locationtech.jts:jts-core:1.16.0")

	// Jackson Dependencies
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	//implementation("org.n52.jackson:jackson-datatype-jts:1.2.10")

	// Spring Boot dependencies
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

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

// h2 Dependencies
//runtimeOnly("com.h2database:h2")
//implementation("org.hibernatespatial:hibernate-spatial-h2-geodb")

// MariaDB Dependencies
//implementation("mysql:mysql-connector-java")
//implementation("ch.vorburger.mariaDB4j:mariaDB4j-springboot:2.2.3")

// JTS Dependencies
// implementation("org.locationtech.jts:jts-core:1.16.0")