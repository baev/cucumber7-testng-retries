/*
 *  Copyright 2020 Qameta Software OÜ
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.qameta.allure.examples.cucumber7;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.Pickle;
import io.cucumber.testng.PickleWrapper;
import org.testng.IDataProviderInterceptor;
import org.testng.IDataProviderMethod;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CucumberTestNgFilter implements IDataProviderInterceptor {

    private static final String CUCUMBER_WORKING_DIR = Paths.get("").toUri().toString();
    private static final String CLASSPATH_PREFIX = "classpath:";

    private static final String CUCUMBER_RUN_METHOD
            = "io.cucumber.testng.AbstractTestNGCucumberTests.runScenario";

    @Override
    public Iterator<Object[]> intercept(final Iterator<Object[]> original,
                                        final IDataProviderMethod dataProviderMethod,
                                        final ITestNGMethod method,
                                        final ITestContext iTestContext) {
        if (!CUCUMBER_RUN_METHOD.equals(method.getQualifiedName())) {
            return original;
        }

        final List<Object[]> filtered = new ArrayList<>();
        original.forEachRemaining(objects -> {
            if (objects.length != 2) {
                filtered.add(objects);
            }
            final PickleWrapper first = (PickleWrapper) objects[0];
            final FeatureWrapper second = (FeatureWrapper) objects[1];

            final Pickle pickle = first.getPickle();
            final String fullName = String.format("%s:%d",
                    getUri(pickle),
                    pickle.getLine()
            );

            System.out.println(fullName);

            filtered.add(new Object[]{first, second});
        });

        return filtered.iterator();
    }

    private String getUri(final Pickle pickle) {
        final String testCaseUri = pickle.getUri().toString();
        if (testCaseUri.startsWith(CUCUMBER_WORKING_DIR)) {
            return testCaseUri.substring(CUCUMBER_WORKING_DIR.length());
        }
        if (testCaseUri.startsWith(CLASSPATH_PREFIX)) {
            return testCaseUri.substring(CLASSPATH_PREFIX.length());
        }
        return testCaseUri;
    }
}
