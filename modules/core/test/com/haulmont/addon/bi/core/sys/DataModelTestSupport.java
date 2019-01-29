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

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.testsupport.TestContainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class DataModelTestSupport {

    private static final Log log = LogFactory.getLog(DataModelTestSupport.class);

    public static void testLoadList(TestContainer cont) {
        for (final MetaClass metaClass : cont.metadata().getTools().getAllPersistentMetaClasses()) {
            cont.persistence().createTransaction().execute(em -> {
                log.info(String.format("Checking %s", metaClass.getName()));
                Query q = em.createQuery(String.format("select e from %s e", metaClass.getName()));
                q.setMaxResults(10);
                q.getResultList();
            });
        }
    }
}