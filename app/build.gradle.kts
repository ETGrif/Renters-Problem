/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("renters.problem.java-application-conventions")
}

dependencies {
    implementation("org.apache.commons:commons-text")
    implementation(project(":simplex"))
    // implementation(project(":JMat:jmat.core"))
}

application {
    // Define the main class for the application.
    mainClass.set("renters.problem.app.App")
}
