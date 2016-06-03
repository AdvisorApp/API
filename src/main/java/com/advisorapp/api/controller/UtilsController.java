package com.advisorapp.api.controller;

import com.advisorapp.api.fixtures.FixturingDatabase;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/reset")
@Api(value = "options", description = "Reinitialize all the database")
public class UtilsController  extends AbstractRestHandler {

    @Autowired
    private FixturingDatabase fixturingDatabase;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Create an option resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void initDatabase(HttpServletRequest request, HttpServletResponse response) {
        this.fixturingDatabase.run();

        response.setHeader("Location", request.getRequestURL().append("/").toString());
    }
}
