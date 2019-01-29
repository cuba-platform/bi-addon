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

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UuidProvider;
import com.haulmont.cuba.core.sys.persistence.DbmsType;
import com.haulmont.cuba.security.entity.User;

import static org.junit.Assert.assertTrue;

/**
 * @author krivopustov
 */
public final class QueryTestSupport {

    public static void test(Metadata metadata, Persistence persistence) {
        persistence.createTransaction().execute(new Transaction.Runnable() {
            @Override
            public void run(EntityManager em) {
                if (!"oracle".equals(DbmsType.getType()) && !"mysql".equals(DbmsType.getType())) {
                    Query query = em.createQuery("select u from sec$User u where u.id = '60885987-1b61-4247-94c7-dff348347f93'");
                    Object user = query.getFirstResult();
                    assertTrue(user instanceof User);
                }

                Query query = em.createQuery("select u from sec$User u where u.id = ?1");
                query.setParameter(1, UuidProvider.fromString("60885987-1b61-4247-94c7-dff348347f93"));
                Object user1 = query.getFirstResult();
                assertTrue(user1 instanceof User);
            }
        });
    }
}