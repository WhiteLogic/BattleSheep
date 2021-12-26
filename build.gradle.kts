import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.5.10"
}

group = "ru.white_logic.battlesheep"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	gradlePluginPortal()
	mavenCentral() // https://repo.maven.apache.org/maven2
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation(group="org.slf4j", name="slf4j-api",            version="1.7.29")

	implementation(group="ch.qos.logback", name="logback-core",    version="1.2.3")
	implementation(group="ch.qos.logback", name="logback-classic", version="1.2.3")

	val lombokVersion = "1.18.20"
	implementation(         group="org.projectlombok", name="lombok", version=lombokVersion)
	testImplementation(     group="org.projectlombok", name="lombok", version=lombokVersion)
	annotationProcessor(    group="org.projectlombok", name="lombok", version=lombokVersion)
	testAnnotationProcessor(group="org.projectlombok", name="lombok", version=lombokVersion)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		jvmTarget = "1.8"
	}
}

tasks.withType<Jar> {
	manifest {
		attributes["Built-By"] = "WhiteLogic"
		attributes["Implementation-Version"] = archiveVersion
		attributes["Main-Class"] = "ru.white_logic.battlesheep.controller.MainGame"
	}
}

tasks.register<Jar>("fatJar") {
	archiveClassifier.set("fat")
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	from(sourceSets.main.get().output)
	dependsOn(configurations.runtimeClasspath)
	from(configurations.runtimeClasspath.get().map({ if (it.isDirectory) it else zipTree(it) }))
//	from(configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) })
	with(tasks["jar"] as CopySpec)
}

tasks.withType<Test> {
	useJUnitPlatform()
}