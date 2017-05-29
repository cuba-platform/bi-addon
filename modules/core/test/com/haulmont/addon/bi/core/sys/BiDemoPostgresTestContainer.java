/*
 * Copyright (c) 2008-2016 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.haulmont.addon.bi.core.sys;

public class BiDemoPostgresTestContainer extends BiDemoTestContainer {

    public BiDemoPostgresTestContainer() {
        super();
        setDbDriver("org.postgresql.Driver");

        setDbUrl("jdbc:postgresql://localhost/cubabi");

        setDbUser("root");
        setDbPassword("root");
        addAppPropertiesFile("postgres-test-app.properties");
    }

    public static class Common extends BiDemoPostgresTestContainer {

        public static final BiDemoPostgresTestContainer.Common INSTANCE = new BiDemoPostgresTestContainer.Common();

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
