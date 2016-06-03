package com.advisorapp.api.controller;

import com.advisorapp.api.model.Credential;
import com.advisorapp.api.model.JsonWebToken;
import com.advisorapp.api.SecuredRequest;
import com.advisorapp.api.exception.ResourceNotFoundException;
import com.advisorapp.api.model.User;
import com.advisorapp.api.exception.ResourceNotFoundException;
import com.advisorapp.api.service.AuthenticationService;
import com.advisorapp.api.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/api/auths")
@Api(value = "users", description = "User API")
public class AuthController extends AbstractRestHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/token",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get an JWT token", notes = "You have to provide valid user credentials to get a token")
    public
    @ResponseBody
    JsonWebToken getToken(@RequestBody Credential credential,
                          HttpServletRequest request, HttpServletResponse response) {
        return userService.fetchByCredentials(credential)
                .map(authenticationService::provideToken)
                .map(JsonWebToken::new)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }


    @RequestMapping(value = "/hello",
            method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Hello route", notes = "200 if token is correct, 401 otherwise")
    public
    @ResponseBody
    String hello(SecuredRequest request, HttpServletResponse response) {
        return "Hello ! " + request.getUser().hashCode();
    }


    @RequestMapping(value = "/signup",
            method = RequestMethod.POST,
            consumes = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "SignUp", notes = "Sign Up a user")
    public void signUp(@RequestBody User user,
                HttpServletRequest request, HttpServletResponse response) {
//        if (this.userService.getUserRepository().findByEmail(user.getEmail())) {
//
//        }
        userService.signUp(user);
    }

}
