/*
 * Copyright (c) 2008-2016 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.haulmont.addon.bi.core.sys;

import org.apache.commons.lang3.StringUtils;

public class BiDemoMssqlTestContainer extends BiDemoTestContainer {

    public BiDemoMssqlTestContainer() {
        super();
        setDbDriver("org.hsqldb.jdbc.JDBCDriver");
        setDbUrl("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String mssqlHost = System.getProperty("mssqlHost");
        if (StringUtils.isEmpty(mssqlHost)) {
            mssqlHost = "localhost";
        }
        setDbUrl("jdbc:sqlserver://" + mssqlHost + ";databaseName=cubabi");
        setDbUser("sa");
        setDbPassword("saPass1");
        addAppPropertiesFile("mssql-test-app.properties");
    }

    public static class Common extends BiDemoMssqlTestContainer {

        public static final BiDemoMssqlTestContainer.Common INSTANCE = new BiDemoMssqlTestContainer.Common();

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
