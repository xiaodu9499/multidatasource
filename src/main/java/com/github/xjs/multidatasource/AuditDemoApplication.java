/** 
 * copyright(c) 2019-2029 mamcharge.com
 */
 
package com.github.xjs.multidatasource;

import com.github.xjs.multidatasource.config.DynamicDataSourceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DynamicDataSourceConfiguration.class)
public class AuditDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditDemoApplication.class, args);
    }
}
