package com.advisorapp.api.controller;

import com.advisorapp.api.model.Option;
import com.advisorapp.api.exception.DataFormatException;
import com.advisorapp.api.model.Option;
import com.advisorapp.api.service.OptionService;
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
@RequestMapping(value = "/api/options")
@Api(value = "options", description = "Option API")
public class OptionController extends AbstractRestHandler {

    @Autowired
    private OptionService optionService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create an option resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createOption(@RequestBody Option option,
                           HttpServletRequest request, HttpServletResponse response) {
        Option createdOption = this.optionService.createOption(option);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdOption.getId()).toString());
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all options.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<Option> getAllOptions(@ApiParam(value = "The page number (zero-based)", required = true)
                           @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                           @ApiParam(value = "Tha page size", required = true)
                           @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                           HttpServletRequest request, HttpServletResponse response) {
        return this.optionService.getAllOptions(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single option.", notes = "You have to provide a valid option ID.")
    public
    @ResponseBody
    Option getOption(@ApiParam(value = "The ID of the option.", required = true)
                 @PathVariable("id") Long id,
                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        Option option = this.optionService.getOption(id);
        checkResourceFound(option);
        //todo: http://goo.gl/6iNAkz
        return option;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a option resource.", notes = "You have to provide a valid option ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateOption(@ApiParam(value = "The ID of the existing option resource.", required = true)
                           @PathVariable("id") Long id, @RequestBody Option option,
                           HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.optionService.getOption(id));
        if (id != option.getId()) throw new DataFormatException("ID doesn't match!");
        this.optionService.updateOption(option);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a option resource.", notes = "You have to provide a valid option ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteOption(@ApiParam(value = "The ID of the existing option resource.", required = true)
                           @PathVariable("id") Long id, HttpServletRequest request,
                           HttpServletResponse response) {
        checkResourceFound(this.optionService.getOption(id));
        this.optionService.deleteOption(id);
    }
}
