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

package org.gradle.api.internal.tasks.compile

import org.gradle.internal.jvm.JavaInfo
import org.gradle.internal.jvm.Jvm
import spock.lang.IgnoreIf
import spock.lang.Specification

import javax.tools.JavaCompiler

class JdkToolsTest extends Specification {

    @IgnoreIf({ Jvm.current().toolsJar == null })
    def "can get java compiler"() {
        def compiler = JdkTools.current().systemJavaCompiler

        expect:
        compiler instanceof JavaCompiler
        compiler.class == JdkTools.current().systemJavaCompiler.class
    }

    def "throws when no tools"() {
        when:
        new JdkTools(Mock(JavaInfo) {
            getToolsJar() >> null
            getJavaHome() >> new File('.')
        })

        then:
        thrown IllegalStateException
    }

    def "throws when tools doesn't contain compiler"() {
        when:
        new JdkTools(Mock(JavaInfo) {
            getToolsJar() >> new File("/nothing")
        }).systemJavaCompiler

        then:
        thrown IllegalStateException
    }
}
