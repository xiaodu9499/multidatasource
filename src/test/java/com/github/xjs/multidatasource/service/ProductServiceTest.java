/** 
 * copyright(c) 2019-2029 mamcharge.com
 */
 
package com.github.xjs.multidatasource.service;

import com.github.xjs.multidatasource.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testGetById() {
        Product p = productService.getById(1L);
        System.out.println(p);
    }
}