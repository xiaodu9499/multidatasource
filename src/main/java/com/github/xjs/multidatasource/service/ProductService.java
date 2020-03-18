/** 
 * copyright(c) 2019-2029 mamcharge.com
 */
 
package com.github.xjs.multidatasource.service;

import com.github.xjs.multidatasource.config.DataSourceAnnotation;
import com.github.xjs.multidatasource.dao.ProductMapper;
import com.github.xjs.multidatasource.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DataSourceAnnotation(name="db1")
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public Product getById(Long id){
        return productMapper.selectById(id);
    }

}
