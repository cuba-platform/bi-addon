/*
 * Copyright (c) 2008-2015 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/license for details.
 */

package com.haulmont.addon.bi.core;

import com.haulmont.addon.bi.core.sys.BiDemoPostgresTestContainer;
import com.haulmont.addon.bi.core.sys.DataModelTestSupport;
import com.haulmont.cuba.testsupport.TestContainer;
import org.junit.ClassRule;
import org.junit.Test;

public class DataModelPostgresTest {
    @ClassRule
    public static TestContainer cont = BiDemoPostgresTestContainer.Common.INSTANCE;

    @Test
    public void test() throws Exception {
        DataModelTestSupport.testLoadList(cont);
    }
}