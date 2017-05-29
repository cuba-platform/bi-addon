/*
 * Copyright (c) 2008-2016 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/commercial-software-license for details.
 */

package com.haulmont.addon.bi.core.sys;

import org.apache.commons.lang.StringUtils;

public class BiDemoOracleTestContainer extends BiDemoTestContainer {

    public BiDemoOracleTestContainer() {
        super();
        setDbDriver("oracle.jdbc.OracleDriver");

        String oracleHost = System.getProperty("oracleHost");
        if (StringUtils.isEmpty(oracleHost))
            oracleHost = "localhost";
        setDbUrl("jdbc:oracle:thin:@//" + oracleHost + "/orcl");

        setDbUser("cubabi");
        setDbPassword("cubabi");
        addAppPropertiesFile("oracle-test-app.properties");
    }

    public static class Common extends BiDemoOracleTestContainer {

        public static final BiDemoOracleTestContainer.Common INSTANCE = new BiDemoOracleTestContainer.Common();

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
