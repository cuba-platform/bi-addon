/*
 * TODO Copyright
 */

package com.haulmont.addon.bi;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.DefaultString;

@Source(type = SourceType.DATABASE)
public interface BIConfig extends Config {

    @Property("cubabi.pentahoServerUrl")
    @DefaultString("http://localhost:8081/pentaho")
    String getPentahoServerUrl();

    void setPentahoServerUrl(String serverUrl);
}
