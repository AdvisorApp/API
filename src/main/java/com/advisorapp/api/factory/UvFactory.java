package com.advisorapp.api.factory;

import com.advisorapp.api.model.Location;
import com.advisorapp.api.model.Option;
import com.advisorapp.api.model.Uv;
import com.advisorapp.api.model.UvType;
import com.advisorapp.api.service.UvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UvFactory {
    @Autowired
    private UvService uvService;

    public Uv createUV(
        String name,
        String description,
        String remoteId,
        Boolean isAvailableForCart,
        Integer minSemester,
        Integer chs,
        Option option,
        Location location,
        UvType uvType,
        Set<Uv> corequisites,
        Set<Uv> prerequisites
    ) {
        Uv uv = new Uv();

        return this.uvService.createUv(
            uv
                .setName(name)
                .setDescription(description)
                .setRemoteId(remoteId)
                .setIsAvailableForCard(isAvailableForCart)
                .setMinSemester(minSemester)
                .setChs(chs)
                .setOption(option)
                .setLocation(location)
                .setUvType(uvType)
                .setCorequisitesUv(corequisites)
                .setPrerequisitesUv(prerequisites)
        );
    }
}
