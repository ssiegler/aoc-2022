plugins {
    kotlin("jvm") version "1.7.22"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src/main")
        }
        test {
            java.srcDirs("src/test")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }

    test {
        useJUnitPlatform()
    }
}
