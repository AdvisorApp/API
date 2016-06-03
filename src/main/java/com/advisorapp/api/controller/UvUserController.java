package com.advisorapp.api.controller;

import com.advisorapp.api.factory.UvUserFactory;
import com.advisorapp.api.model.UvUser;
import com.advisorapp.api.exception.DataFormatException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/uvUsers")
@Api(value = "uvUsers", description = "UvUser API")
public class UvUserController extends AbstractRestHandler {
    @Autowired
    private UvUserFactory uvUserFactory;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create an uvUser resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createUvUser(@RequestBody UvUser uvUser,
                           HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Location", request.getRequestURL().append("/").append(
                this.uvUserFactory.getUvUserService().createUvUser(uvUser).getId()).toString()
        );
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all uvUsers.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Set<UvUser> getAllUvUsers(HttpServletRequest request, HttpServletResponse response) {
        return this.uvUserFactory.getUvUserService().getAllUvUsers();
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single uvUser.", notes = "You have to provide a valid uvUser ID.")
    public
    @ResponseBody
    UvUser getUvUser(@ApiParam(value = "The ID of the uvUser.", required = true)
                 @PathVariable("id") Long id,
                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        UvUser uvUser = this.uvUserFactory.getUvUserService().getUvUser(id);
        checkResourceFound(uvUser);
        return uvUser;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a uvUser resource.", notes = "You have to provide a valid uvUser ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateUvUser(@ApiParam(value = "The ID of the existing uvUser resource.", required = true)
                           @PathVariable("id") Long id, @RequestBody UvUser uvUser,
                           HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.uvUserFactory.getUvUserService().getUvUser(id));
        if (id != uvUser.getId()) throw new DataFormatException("ID doesn't match!");
        this.uvUserFactory.getUvUserService().updateUvUser(uvUser);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a uvUser resource.", notes = "You have to provide a valid uvUser ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteUvUser(@ApiParam(value = "The ID of the existing uvUser resource.", required = true)
                           @PathVariable("id") Long id, HttpServletRequest request,
                           HttpServletResponse response) {
        checkResourceFound(this.uvUserFactory.getUvUserService().getUvUser(id));
        this.uvUserFactory.getUvUserService().deleteUvUser(id);
    }
}
