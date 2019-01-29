/*
 * Copyright (c) 2008-2019 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
