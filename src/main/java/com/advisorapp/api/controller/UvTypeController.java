package com.advisorapp.api.controller;

import com.advisorapp.api.factory.UvTypeFactory;
import com.advisorapp.api.model.UvType;
import com.advisorapp.api.exception.DataFormatException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;


@RestController
@RequestMapping(value = "/api/uvTypes")
@Api(value = "uvTypes", description = "UvType API")
public class UvTypeController extends AbstractRestHandler {
    @Autowired
    private UvTypeFactory uvTypeFactory;



    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all uvTypes.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Set<UvType> getAllUvTypes(HttpServletRequest request, HttpServletResponse response) {
        return this.uvTypeFactory.getUvTypeService().getAllUvTypes();
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single uvType.", notes = "You have to provide a valid uvType ID.")
    public
    @ResponseBody
    UvType getUvType(@ApiParam(value = "The ID of the uvType.", required = true)
                     @PathVariable("id") Long id,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        UvType uvType = this.uvTypeFactory.getUvTypeService().getUvType(id);
        checkResourceFound(uvType);
        return uvType;
    }
}
