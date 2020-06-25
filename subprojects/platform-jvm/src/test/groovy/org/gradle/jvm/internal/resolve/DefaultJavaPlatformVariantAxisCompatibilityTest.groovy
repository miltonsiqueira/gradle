/*
 * Copyright 2016 the original author or authors.
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

package org.gradle.jvm.internal.resolve

import org.gradle.api.JavaVersion
import org.gradle.jvm.platform.JavaPlatform
import org.gradle.jvm.platform.internal.DefaultJavaPlatform
import spock.lang.Specification

class DefaultJavaPlatformVariantAxisCompatibilityTest extends Specification {
    def "Java #requirement is compatible with #value : #expected"() {
        given:
        VariantAxisCompatibility<JavaPlatform> selector = new DefaultJavaPlatformVariantAxisCompatibility()

        when:
        def compatible = selector.isCompatibleWithRequirement(requirement, value)

        then:
        compatible == expected

        where:
        required | found | expected
        '1.5'    | '1.5' | true
        '1.5'    | '1.6' | false
        '1.6'    | '1.5' | true
        '1.7'    | '1.5' | true
        '1.7'    | '1.6' | true

        and:
        requirement = new DefaultJavaPlatform(JavaVersion.toVersion(required))
        value = new DefaultJavaPlatform(JavaVersion.toVersion(found))
    }

    def "should check that Java #newValue platform is a better fit than #oldValue : #expected"() {
        given:
        VariantAxisCompatibility<JavaPlatform> selector = new DefaultJavaPlatformVariantAxisCompatibility()

        when:
        def compatible = selector.betterFit(new DefaultJavaPlatform(JavaVersion.VERSION_1_7), oldValue, newValue)

        then:
        compatible == expected

        where:
        required | found | expected
        '1.5'    | '1.5' | false
        '1.5'    | '1.6' | true
        '1.6'    | '1.5' | false
        '1.7'    | '1.5' | false
        '1.7'    | '1.6' | false

        and:
        oldValue = new DefaultJavaPlatform(JavaVersion.toVersion(required))
        newValue = new DefaultJavaPlatform(JavaVersion.toVersion(found))
    }
}
