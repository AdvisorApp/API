package com.advisorapp.api.factory;

import com.advisorapp.api.model.UvType;
import com.advisorapp.api.service.UvTypeService;
import org.springframework.beans.factory.annotation.Autowired;

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
}
