package com.advisorapp.api.controller;

import com.advisorapp.api.exception.ResourceNotFoundException;
import com.advisorapp.api.exception.UnmakableRequestException;
import com.advisorapp.api.fixtures.FixturingDatabase;
import com.advisorapp.api.model.RestErrorInfo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.UnavailableException;
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
    public void initDatabase(HttpServletRequest request, HttpServletResponse response) throws UnmakableRequestException {
        boolean result = this.fixturingDatabase.run();
        if (!result)
            throw new UnmakableRequestException("You must destroy your database before");

        response.setHeader("Location", request.getRequestURL().append("/").toString());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(UnmakableRequestException.class)
    public
    @ResponseBody
    RestErrorInfo cantMakeRequest(UnmakableRequestException ex, WebRequest request, HttpServletResponse response) {
        log.info("UnmakableRequest handler:" + ex.getMessage());

        return new RestErrorInfo(ex, "Restart the API");
    }
}
