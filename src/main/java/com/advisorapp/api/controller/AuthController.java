package com.advisorapp.api.controller;

import com.advisorapp.api.model.Credential;
import com.advisorapp.api.model.JsonWebToken;
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

}
