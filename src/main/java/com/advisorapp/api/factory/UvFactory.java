package com.advisorapp.api.factory;

import com.advisorapp.api.model.*;
import com.advisorapp.api.service.SemesterService;
import com.advisorapp.api.service.UvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UvFactory {
    @Autowired
    private UvService uvService;

    @Autowired
    private SemesterService semesterService;

    public Uv createUV(
        String name,
        String description,
        String remoteId,
        Boolean isAvailableForCart,
        Integer chs,
        Location location,
        UvType uvType,
        Set<Uv> corequisites,
        Set<Uv> prerequisites
    ) {
        Uv uv = new Uv();

        uv = this.uvService.createUv(
            uv
                .setName(name)
                .setDescription(description)
                .setRemoteId(remoteId)
                .setIsAvailableForCard(isAvailableForCart)
                .setChs(chs)
                .setLocation(location)
                .setUvType(uvType)
                .setCorequisitesUv(corequisites == null ? new HashSet<Uv>() : corequisites)
                .setPrerequisitesUv(prerequisites == null ? new HashSet<Uv>() : prerequisites)
        );

        return uv;
    }

    public UvService getUvService()
    {
        return this.uvService;
    }
}
