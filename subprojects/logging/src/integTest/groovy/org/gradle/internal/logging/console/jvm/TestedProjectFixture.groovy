/*
 * Copyright 2017 the original author or authors.
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

package org.gradle.internal.logging.console.jvm

import org.gradle.integtests.fixtures.RepoScriptBlockUtil
import org.gradle.integtests.fixtures.RichConsoleStyling
import org.gradle.integtests.fixtures.executer.GradleHandle
import org.gradle.test.fixtures.server.http.BlockingHttpServer

class TestedProjectFixture implements RichConsoleStyling {

    static String testClass(String testAnnotationClassName, String testClassName, String serverResource, BlockingHttpServer server) {
        """
            package org.gradle;

            import ${testAnnotationClassName};

            public class $testClassName {
                @Test
                public void longRunningTest() {
                    ${server.callFromBuild(serverResource)}
                }
            }
        """
    }

    static String testableJavaProject(String testDependency, int maxWorkers) {
        """
            apply plugin: 'java'
            
            ${RepoScriptBlockUtil.jcenterRepository()}
            
            dependencies {
                testCompile '${testDependency}'
            }
            
            tasks.withType(Test) {
                maxParallelForks = $maxWorkers
            }
        """
    }

    static String useTestNG() {
        """
            tasks.withType(Test) {
                useTestNG()
            }
        """
    }

    static void containsTestExecutionWorkInProgressLine(GradleHandle gradleHandle, String taskPath, String testName) {
        assert gradleHandle.standardOutput.contains(workInProgressLine("> $taskPath > Executing test $testName"))
    }
}
