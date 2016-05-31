package com.advisorapp.api.controller;

import com.advisorapp.api.factory.StudyPlanFactory;
import com.advisorapp.api.factory.UserFactory;
import com.advisorapp.api.factory.UvFactory;
import com.advisorapp.api.factory.UvUserFactory;
import com.advisorapp.api.model.*;
import com.advisorapp.api.model.StudyPlan;
import com.advisorapp.api.SecuredRequest;
import com.advisorapp.api.model.User;
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
@RequestMapping(value = "/api/users")
@Api(value = "users", description = "User API")
public class UserController extends AbstractRestHandler {

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private UvFactory uvFactory;

    @Autowired
    private UvUserFactory uvUserFactory;

    @Autowired
    private StudyPlanFactory studyPlanFactory;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create an user resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createUser(@RequestBody User user,
                           HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Location", request.getRequestURL().append("/").append(this.userFactory.createUser(user).getId()).toString());
    }

    @RequestMapping(value = "/me",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get connected user information", notes = "Returns the information of user that corresponds to the JWT")
    public
    @ResponseBody
    User getMe(@ApiParam(value = "The page number (zero-based)", required = true)
                     @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                     @ApiParam(value = "Tha page size", required = true)
                     @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                     SecuredRequest request, HttpServletResponse response) {
        return request.getUser();
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
        User user = this.userFactory.getUserService().getUser(id);
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
        checkResourceFound(this.userFactory.getUserService().getUser(id));
        if (id != user.getId()) throw new DataFormatException("ID doesn't match!");

        this.userFactory.getUserService().updateUser(user);
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
        checkResourceFound(this.userFactory.getUserService().getUser(id));

        this.userFactory.getUserService().deleteUser(id);
    }


    User me(SecuredRequest request, HttpServletResponse response) {
        return this.userFactory.getUserService().findById(request.getUser().getId());
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
        User user = this.userFactory.getUserService().getUser(id);
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
        User attachedUser = this.userFactory.getUserService().getUser(id);
        checkResourceFound(attachedUser);

        return this.studyPlanFactory.createStudyPlanWithUser(studyPlan, attachedUser);
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
        User user = this.userFactory.getUserService().getUser(id);
        checkResourceFound(user);

        return this.userFactory.getUvUserFactory().getUvUserService().getUvUserByUser(user);
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
        User user = this.userFactory.getUserService().getUser(user_id);
        checkResourceFound(user);
        if (user_id != user.getId()) throw new DataFormatException("User ID doesn't match!");

        Uv uv = this.uvFactory.getUvService().getUv(uv_id);
        checkResourceFound(uv);
        if (uv_id != uv.getId()) throw new DataFormatException("UV ID doesn't match!");


        return uvUserFactory.getUvUserService().getUvUserByUVAndUser(uv, user);
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

        User user = this.userFactory.getUserService().getUser(user_id);
        checkResourceFound(user);
        if (user_id != user.getId()) throw new DataFormatException("User ID doesn't match!");

        Uv uv = this.uvFactory.getUvService().getUv(uv_id);
        checkResourceFound(uv);
        if (uv_id != uv.getId()) throw new DataFormatException("UV ID doesn't match!");


        UvUser uvUserOrigin = this.uvUserFactory.getUvUserService().getUvUserByUVAndUser(uv, user);

        if (uvUserOrigin.getId() != uvUser.getId()) throw new DataFormatException("UvUser ID doesn't match!");

        this.uvUserFactory.setup(uvUser, uv, user);
    }

}
