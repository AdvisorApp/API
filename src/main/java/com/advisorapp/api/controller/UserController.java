package com.advisorapp.api.controller;

import com.advisorapp.api.dao.UvRepository;
import com.advisorapp.api.model.*;
import com.advisorapp.api.exception.DataFormatException;
import com.advisorapp.api.model.User;
import com.advisorapp.api.service.StudyPlanService;
import com.advisorapp.api.service.UserService;
import com.advisorapp.api.service.UvService;
import com.advisorapp.api.service.UvUserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/users")
@Api(value = "users", description = "User API")
public class UserController extends AbstractRestHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private StudyPlanService studyPlanService;

    @Autowired
    private UvService uvService;

    @Autowired
    private UvRepository uvRepository;

    @Autowired
    private UvUserService uvUserService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create an user resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createUser(@RequestBody User user,
                            HttpServletRequest request, HttpServletResponse response) {
        User createdUser = this.userService.createUser(user);

        // Create All UvUser for each UVs existing on database.
        Iterator<Uv> uvs = this.uvRepository.findAll().iterator();
        while(uvs.hasNext()){
            Uv uv = uvs.next();
            UvUser uvUser = new UvUser();
            uvUser.setUser(createdUser);
            uvUser.setUv(uv);
            UvUser createdUvUser = this.uvUserService.createUvUser(uvUser);
        }

        response.setHeader("Location", request.getRequestURL().append("/").append(createdUser.getId()).toString());
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all users.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<User> getAllUsers(@ApiParam(value = "The page number (zero-based)", required = true)
                            @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                            @ApiParam(value = "Tha page size", required = true)
                            @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                            HttpServletRequest request, HttpServletResponse response) {
        return this.userService.getAllUsers(page, size);
    }



    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single user.", notes = "You have to provide a valid user ID.")
    public
    @ResponseBody
    User getUser(@ApiParam(value = "The ID of the user.", required = true)
                   @PathVariable("id") Long id,
                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.userService.getUser(id);
        checkResourceFound(user);
        //todo: http://goo.gl/6iNAkz
        return user;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a user resource.", notes = "You have to provide a valid user ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateUser(@ApiParam(value = "The ID of the existing user resource.", required = true)
                            @PathVariable("id") Long id, @RequestBody User user,
                            HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.userService.getUser(id));
        if (id != user.getId()) throw new DataFormatException("ID doesn't match!");
        this.userService.updateUser(user);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a user resource.", notes = "You have to provide a valid user ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteUser(@ApiParam(value = "The ID of the existing user resource.", required = true)
                            @PathVariable("id") Long id, HttpServletRequest request,
                            HttpServletResponse response) {
        checkResourceFound(this.userService.getUser(id));
        this.userService.deleteUser(id);
    }


    // ----- User's study plan requests handler

    @RequestMapping(value = "/{id}/studyPlans",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get user's study plan.", notes = "You have to provide a valid user ID.")
    public
    @ResponseBody
    Set<StudyPlan> getStudyPlanByUser(@ApiParam(value = "The ID of the user.", required = true)
                                      @PathVariable("id") Long id,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.userService.getUser(id);
        checkResourceFound(user);
        return user.getStudyPlans();
    }

    @RequestMapping(value = "/{id}/studyPlans",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a study plan for a user.", notes = "Returns the URL of the new resource in the Location header.")
    public StudyPlan createStudyPlanForUser(@ApiParam(value = "The ID of the user.", required = true)
                                            @PathVariable("id") Long id,
                                            @RequestBody StudyPlan studyPlan,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        User attachedUser = this.userService.getUser(id);
        checkResourceFound(attachedUser);
        studyPlan.setUser(attachedUser);
        StudyPlan createdStudyPlan = this.studyPlanService.createStudyPlan(studyPlan);
        return createdStudyPlan;
    }

    // ----- User's UvUser requests handler

    @RequestMapping(value = "/{id}/uvUsers",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get user's uvUsers.", notes = "You have to provide a valid user ID.")
    public
    @ResponseBody
    Set<UvUser> getUvUserByUser(@ApiParam(value = "The ID of the user.", required = true)
                                      @PathVariable("id") Long id,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.userService.getUser(id);
        checkResourceFound(user);

        return uvUserService.getUvUserByUser(user);
    }

    @RequestMapping(value = "/{user_id}/uvUsers/{uv_id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get user's uvUsers.", notes = "You have to provide a valid user and UV ID.")
    public
    @ResponseBody
    UvUser getUvUserByUserAndUV(@ApiParam(value = "The ID of the user.", required = true)
                                @PathVariable("user_id") Long user_id,
                                @ApiParam(value = "The ID of the UV.", required = true)
                                @PathVariable("uv_id") Long uv_id,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.userService.getUser(user_id);
        Uv uv = this.uvService.getUv(uv_id);
        checkResourceFound(user);
        checkResourceFound(uv);
        if (user_id != user.getId()) throw new DataFormatException("User ID doesn't match!");
        if (uv_id != uv.getId()) throw new DataFormatException("UV ID doesn't match!");

        return uvUserService.getUvUserByUVAndUser(uv, user);
    }

    @RequestMapping(value = "/{user_id}/uvUsers/{uv_id}",
            method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update user's uvUsers.", notes = "You have to provide a valid user and UV ID.")
    public void updateUvUserByUserAndUV(@ApiParam(value = "The ID of the user.", required = true)
                                        @PathVariable("user_id") Long user_id,
                                        @ApiParam(value = "The ID of the UV.", required = true)
                                        @PathVariable("uv_id") Long uv_id,
                                        @RequestBody UvUser uvUser,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.userService.getUser(user_id);
        Uv uv = this.uvService.getUv(uv_id);
        checkResourceFound(user);
        checkResourceFound(uv);
        if (user_id != user.getId()) throw new DataFormatException("User ID doesn't match!");
        if (uv_id != uv.getId()) throw new DataFormatException("UV ID doesn't match!");

        UvUser uvUserOrigin = uvUserService.getUvUserByUVAndUser(uv, user);
        if (uvUserOrigin.getId() != uvUser.getId()) throw new DataFormatException("UvUser ID doesn't match!");

        uvUser.setUser(user);
        uvUser.setUv(uv);
        this.uvUserService.updateUvUser(uvUser);
    }

}
