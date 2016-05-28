package com.advisorapp.api.factory;

import com.advisorapp.api.model.Option;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.model.User;
import com.advisorapp.api.service.OptionService;
import com.advisorapp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class OptionFactory {

    @Autowired
    private OptionService optionService;

    public Option createOption(String name)
    {
        Option option = new Option();
        return this.optionService.createOption(option.setName(name));
    }
}
