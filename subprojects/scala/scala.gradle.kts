/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.gradle.gradlebuild.test.integrationtests.integrationTestUsesSampleDir

plugins {
    gradlebuild.distribution.`api-java`
}

dependencies {
    implementation(project(":baseServices"))
    implementation(project(":logging"))
    implementation(project(":workerProcesses"))
    implementation(project(":fileCollections"))
    implementation(project(":coreApi"))
    implementation(project(":modelCore"))
    implementation(project(":core"))
    implementation(project(":workers"))
    implementation(project(":platformBase"))
    implementation(project(":platformJvm"))
    implementation(project(":languageJvm"))
    implementation(project(":languageJava"))
    implementation(project(":languageScala"))
    implementation(project(":plugins"))
    implementation(project(":reporting"))
    implementation(project(":dependencyManagement"))
    implementation(project(":processServices"))

    implementation(library("groovy"))
    implementation(library("guava"))
    implementation(library("inject"))

    testImplementation(project(":baseServicesGroovy"))
    testImplementation(project(":files"))
    testImplementation(project(":resources"))
    testImplementation(library("slf4j_api"))
    testImplementation(library("commons_io"))
    testImplementation(testFixtures(project(":core")))
    testImplementation(testFixtures(project(":plugins")))
    testImplementation(testFixtures(project(":languageJvm")))
    testImplementation(testFixtures(project(":languageJava")))

    integTestImplementation(project(":jvmServices"))
    integTestImplementation(testFixtures(project(":languageScala")))

    testRuntimeOnly(project(":distributionsCore")) {
        because("ProjectBuilder tests load services from a Gradle distribution.")
    }
    integTestDistributionRuntimeOnly(project(":distributionsJvm"))
}

classycle {
    excludePatterns.set(listOf("org/gradle/api/internal/tasks/scala/**",
        // Unable to change package of public API
        "org/gradle/api/tasks/ScalaRuntime*"))
}

integrationTestUsesSampleDir("subprojects/scala/src/main")
