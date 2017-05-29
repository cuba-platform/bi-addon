/*
 * Copyright (c) 2008-2016 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.haulmont.addon.bi.core.sys;

public class BiDemoHsqlTestContainer extends BiDemoTestContainer {

    public BiDemoHsqlTestContainer() {
        super();
        setDbDriver("org.hsqldb.jdbc.JDBCDriver");
        setDbUrl("jdbc:hsqldb:hsql://localhost:9001/cubabi");
        setDbUser("sa");
        setDbPassword("");
        addAppPropertiesFile("hsql-test-app.properties");
    }

    public static class Common extends BiDemoHsqlTestContainer {

        public static final BiDemoHsqlTestContainer.Common INSTANCE = new BiDemoHsqlTestContainer.Common();

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
