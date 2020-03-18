package com.github.xjs.multidatasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 获得数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getCurrentDateSourceName();
    }

}
