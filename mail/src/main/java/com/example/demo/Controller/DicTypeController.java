package com.example.demo.Controller;


import com.example.demo.Entity.DicType;
import com.example.demo.Service.Mapper.DicTypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dictype")
public class DicTypeController {
    @Autowired
    private DicTypeService dicTypeService;

    @RequestMapping(value = "/all")
    public PageInfo<DicType> getALL(DicType dicType){
        List<DicType> dicTypeList = dicTypeService.getAll(dicType);
        return new PageInfo<>(dicTypeList);
    }
}
