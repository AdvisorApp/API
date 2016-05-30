package com.advisorapp.api.controller;

import com.advisorapp.api.factory.UvTypeFactory;
import com.advisorapp.api.model.UvType;
import com.advisorapp.api.exception.DataFormatException;
import com.advisorapp.api.model.UvType;
import com.advisorapp.api.service.UvTypeService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/api/uvTypes")
@Api(value = "uvTypes", description = "UvType API")
public class UvTypeController extends AbstractRestHandler {
    @Autowired
    private UvTypeFactory uvTypeFactory;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create an uvType resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createUvType(@RequestBody UvType uvType,
                             HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Location", request.getRequestURL().append("/").append(
                this.uvTypeFactory.getUvTypeService().createUvType(uvType).getId()).toString()
        );
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all uvTypes.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<UvType> getAllUvTypes(@ApiParam(value = "The page number (zero-based)", required = true)
                               @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                               @ApiParam(value = "Tha page size", required = true)
                               @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                               HttpServletRequest request, HttpServletResponse response) {
        return this.uvTypeFactory.getUvTypeService().getAllUvTypes(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single uvType.", notes = "You have to provide a valid uvType ID.")
    public
    @ResponseBody
    UvType getUvType(@ApiParam(value = "The ID of the uvType.", required = true)
                     @PathVariable("id") Long id,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        UvType uvType = this.uvTypeFactory.getUvTypeService().getUvType(id);
        checkResourceFound(uvType);
        //todo: http://goo.gl/6iNAkz
        return uvType;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a uvType resource.", notes = "You have to provide a valid uvType ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateUvType(@ApiParam(value = "The ID of the existing uvType resource.", required = true)
                             @PathVariable("id") Long id, @RequestBody UvType uvType,
                             HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.uvTypeFactory.getUvTypeService().getUvType(id));
        if (id != uvType.getId()) throw new DataFormatException("ID doesn't match!");
        this.uvTypeFactory.getUvTypeService().updateUvType(uvType);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a uvType resource.", notes = "You have to provide a valid uvType ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteUvType(@ApiParam(value = "The ID of the existing uvType resource.", required = true)
                             @PathVariable("id") Long id, HttpServletRequest request,
                             HttpServletResponse response) {
        checkResourceFound(this.uvTypeFactory.getUvTypeService().getUvType(id));
        this.uvTypeFactory.getUvTypeService().deleteUvType(id);
    }
}
