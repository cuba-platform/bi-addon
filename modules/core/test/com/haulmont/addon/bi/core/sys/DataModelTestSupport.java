/*
 * Copyright (c) 2008-2015 Haulmont. All rights reserved.
 * Use is subject to license terms, see http://www.cuba-platform.com/license for details.
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