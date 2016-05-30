package com.advisorapp.api.factory;

import com.advisorapp.api.model.Option;
import com.advisorapp.api.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionFactory {

    @Autowired
    private OptionService optionService;

    public Option createOption(String name)
    {
        Option option = new Option();
        return this.optionService.createOption(option.setName(name));
    }

    public OptionService getOptionService()
    {
        return this.optionService;
    }
}
