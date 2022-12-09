plugins { kotlin("jvm") version "1.7.22" }

repositories { mavenCentral() }

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("com.oneeyedmen:okeydoke:1.3.3")
}

tasks {
    sourceSets {
        main { java.srcDirs("src/main") }
        test { java.srcDirs("src/test") }
    }

    wrapper { gradleVersion = "7.6" }

    test { useJUnitPlatform() }
}
