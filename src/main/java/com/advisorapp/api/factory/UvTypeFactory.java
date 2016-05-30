package com.advisorapp.api.factory;

import com.advisorapp.api.model.UvType;
import com.advisorapp.api.service.UvTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UvTypeFactory {
    @Autowired
    private UvTypeService uvTypeService;

    public UvType create(
            String type,
            Double hoursByCredit
    )
    {
        UvType uvType = new UvType();

        return this.uvTypeService.createUvType(
            uvType
                .setType(type)
                .setHoursByCredit(hoursByCredit)
        );
    }

    public UvTypeService getUvTypeService()
    {
        return this.uvTypeService;
    }
}
