/*
 * Copyright (c) 2008-2016 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.haulmont.addon.bi.core.sys;

import org.apache.commons.lang3.StringUtils;

public class BiDemoMysqlTestContainer extends BiDemoTestContainer {

    public BiDemoMysqlTestContainer() {
        super();
        setDbDriver("com.mysql.jdbc.Driver");

        String mysqlHost = System.getProperty("mysqlHost");
        if (StringUtils.isEmpty(mysqlHost))
            mysqlHost = "localhost";
        setDbUrl("jdbc:mysql://" + mysqlHost + "/cubabi?useSSL=false");

        String mysqlUser = System.getProperty("mysqlDbUser");
        if (StringUtils.isEmpty(mysqlUser)) {
            mysqlUser = "root";
        }
        String mysqlPassword = System.getProperty("mysqlDbPassword");
        if (StringUtils.isEmpty(mysqlPassword)) {
            mysqlPassword = "root";
        }
        setDbUser(mysqlUser);
        setDbPassword(mysqlPassword);
        addAppPropertiesFile("mysql-test-app.properties");
    }

    public static class Common extends BiDemoMysqlTestContainer {

        public static final BiDemoMysqlTestContainer.Common INSTANCE = new BiDemoMysqlTestContainer.Common();

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
