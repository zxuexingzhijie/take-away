package com.sky.service.impl;

import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorServiceMapper;
import com.sky.service.DishFlavorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class DishFlavorServiceImp implements DishFlavorService {

    @Autowired
    DishFlavorServiceMapper dishFlavorServiceMapper;

    @Override
    public void insertBatch(List<DishFlavor> flavors) {
        if (flavors != null && flavors.size() > 0) {
            log.info("批量插入口味数据");
            //插入数据到口味表
            dishFlavorServiceMapper.insertBatch(flavors);
        }

    }
}
