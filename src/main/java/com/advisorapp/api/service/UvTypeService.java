package com.advisorapp.api.service;

import com.advisorapp.api.dao.UvTypeRepository;
import com.advisorapp.api.model.UvType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UvTypeService {
    @Autowired
    private UvTypeRepository uvTypeRepository;


    public UvType createUvType(UvType uvType) {
        return uvTypeRepository.save(uvType);
    }

    public UvType getUvType(long id) {
        return uvTypeRepository.findOne(id);
    }

    public Set<UvType> getAllUvTypes()
    {
        return this.uvTypeRepository.findAll();
    }

    public void delete(Long id) {
        uvTypeRepository.delete(id);
    }
}
