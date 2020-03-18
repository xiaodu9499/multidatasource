package com.github.xjs.multidatasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.LinkedHashMap;

@Slf4j
@Configuration
@EnableTransactionManagement
@ConditionalOnProperty("spring.datasource.name")
public class DynamicDataSourceConfiguration implements EnvironmentAware {

    private Environment environment;

    /**
     * @param environment the enviroment to set
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "dataSource")
    @Primary
    public DynamicDataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        LinkedHashMap dataSources = initCustomDataSources();
        dynamicDataSource.setDefaultTargetDataSource(dataSources.get(DynamicDataSourceHolder.DATA_SOURCE_NAMES.get(0)));
        dynamicDataSource.setTargetDataSources(dataSources);
        dynamicDataSource.afterPropertiesSet();
        return dynamicDataSource;
    }

    private LinkedHashMap<String, DataSource> initCustomDataSources() {
        String dataSourceNames = environment.getProperty("spring.datasource.name");
        if(StringUtils.isEmpty(dataSourceNames)) {
            throw new RuntimeException("please configure property `spring.datasource.name`");
        }
        LinkedHashMap<String, DataSource> dataSources = new LinkedHashMap<String, DataSource>();
        for (String dataSourceName : dataSourceNames.split(",")) {
            try {
                DataSource ds = Binder.get(environment).bind("spring.datasource.druid", Bindable.of(DruidDataSource.class)).get();
                if(null != ds){
                    if(ds instanceof DruidDataSource) {
                        DruidDataSource druidDataSource = (DruidDataSource)ds;
                        druidDataSource.setName(dataSourceName);
                        druidDataSource.setDriverClassName(environment.getProperty("spring.datasource."+dataSourceName+".driver-class-name"));
                        druidDataSource.setUrl(environment.getProperty("spring.datasource."+dataSourceName+".url"));
                        druidDataSource.setUsername(environment.getProperty("spring.datasource."+dataSourceName+".username"));
                        druidDataSource.setPassword(environment.getProperty("spring.datasource."+dataSourceName+".password"));
                    }
                    DynamicDataSourceHolder.DATA_SOURCE_NAMES.add(dataSourceName);
                    dataSources.put(dataSourceName,ds);
                }
                log.info("Data source initialization 【"+dataSourceName+"】 successfully ...");
            } catch (Exception e) {
                log.error("Data source initialization【" + dataSourceName + "】 failed ...", e);
            }
        }
        if(dataSources.size() <= 0){
            throw new RuntimeException("please configure property `spring.datasource.druid`");
        }
        return dataSources;
    }
}
