/*
 * Copyright (c) 2008-2016 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.haulmont.addon.bi.core.sys;

import com.haulmont.cuba.testsupport.TestContainer;

import java.util.ArrayList;
import java.util.Arrays;

public class BiDemoTestContainer extends TestContainer {

    public BiDemoTestContainer() {
        super();
        setAppComponents(new ArrayList<>(Arrays.asList(
                "com.haulmont.cuba"
        )));
        setAppPropertiesFiles(new ArrayList<>(Arrays.asList("cuba-app.properties", "test-app.properties", "app.properties")));
    }

    public static class Common extends BiDemoTestContainer {

        public static final BiDemoTestContainer.Common INSTANCE = new BiDemoTestContainer.Common();

        private static volatile boolean initialized;

        private Common() {
        }

        @Override
        public void before() throws Throwable {
            if (!initialized) {
                super.before();
                initialized = true;
            }
            setupContext();
        }

        @Override
        public void after() {
            cleanupContext();
            // never stops - do not call super
        }
    }
}
