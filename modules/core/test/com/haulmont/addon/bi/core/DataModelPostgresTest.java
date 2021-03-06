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