package com.advisorapp.api.controller;

import com.advisorapp.api.factory.UvFactory;
import com.advisorapp.api.model.Uv;
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
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/uvs")
@Api(value = "uvs", description = "Uv API")
public class UvController extends AbstractRestHandler {
    @Autowired
    private UvFactory uvFactory;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create an uv resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createUv(@RequestBody Uv uv,
                           HttpServletRequest request, HttpServletResponse response) {
        Uv createdUv = this.uvFactory.getUvService().createUv(uv);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdUv.getId()).toString());
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all uvs.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<Uv> getAllUvs(@ApiParam(value = "The page number (zero-based)", required = true)
                           @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                           @ApiParam(value = "Tha page size", required = true)
                           @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                           HttpServletRequest request, HttpServletResponse response) {
        return this.uvFactory.getUvService().getAllUvs(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single uv.", notes = "You have to provide a valid uv ID.")
    public
    @ResponseBody
    Uv getUv(@ApiParam(value = "The ID of the uv.", required = true)
                 @PathVariable("id") Long id,
                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        Uv uv = this.uvFactory.getUvService().getUv(id);
        checkResourceFound(uv);
        //todo: http://goo.gl/6iNAkz
        return uv;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a uv resource.", notes = "You have to provide a valid uv ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateUv(@ApiParam(value = "The ID of the existing uv resource.", required = true)
                           @PathVariable("id") Long id, @RequestBody Uv uv,
                           HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.uvFactory.getUvService().getUv(id));
        if (id != uv.getId()) throw new DataFormatException("ID doesn't match!");
        this.uvFactory.getUvService().updateUv(uv);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a uv resource.", notes = "You have to provide a valid uv ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteUv(@ApiParam(value = "The ID of the existing uv resource.", required = true)
                           @PathVariable("id") Long id, HttpServletRequest request,
                           HttpServletResponse response) {
        checkResourceFound(this.uvFactory.getUvService().getUv(id));
        this.uvFactory.getUvService().deleteUv(id);
    }

    // ----- UV's co-requisites requests handler

    @RequestMapping(value = "/{id}/corequisites",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get UV's corequisites")
    public
    @ResponseBody
    Set<Uv> getUVCorequisites(@ApiParam(value = "The ID of the existing uv resource.", required = true)
                               @PathVariable("id") Long id,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        Uv concernedUV = this.uvFactory.getUvService().getUv(id);
        checkResourceFound(concernedUV);
        return concernedUV.getRealCorequisites();
    }

}
