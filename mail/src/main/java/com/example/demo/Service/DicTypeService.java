package com.example.demo.Service.Mapper;

import com.example.demo.Entity.DicType;
import com.example.demo.datasource.ReadOnlyConnection;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicTypeService {
    @Autowired
    private DicTypeMapper dicTypeMapper;

    @ReadOnlyConnection
    public List<DicType> getAll(DicType dicType){
        if (dicType.getPage() != null && dicType.getRows() != null) {
            PageHelper.startPage(dicType.getPage(), dicType.getRows());
        }
        return dicTypeMapper.selectAll();
    }

}
