package com.advisorapp.api.test;


import com.advisorapp.api.Application;
import com.advisorapp.api.SecurityFilter;
import com.advisorapp.api.dao.UserRepository;
import com.advisorapp.api.dao.UserRepository;
import com.advisorapp.api.model.Credential;
import com.advisorapp.api.model.User;
import com.advisorapp.api.service.AuthenticationService;
import com.advisorapp.api.service.HotelService;
import com.advisorapp.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
public class AuthTest extends TestHelper {

    @InjectMocks
    UserService userService;

    @Autowired
    WebApplicationContext context;

    @InjectMocks
    AuthenticationService authenticationService;

    private MockMvc mvc;

    @Before
    public void initTests() {
        UserRepository userRepository = mock(UserRepository.class);

        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(userService, "userRepository", userRepository);


        SecurityFilter securityFilter = new SecurityFilter();
        ReflectionTestUtils.setField(securityFilter, "authenticationService", authenticationService);


        ReflectionTestUtils.setField(authenticationService, "key", AuthenticationService.defaultKey);
        ReflectionTestUtils.setField(authenticationService, "salt", AuthenticationService.defaultSalt);
        ReflectionTestUtils.setField(authenticationService, "userService", userService);


        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(securityFilter, "/*")
                .build();

    }


    //@Test // TODO fix tests
    public void shouldFaiITheRequestContentIsNotJson() throws Exception {
        mvc.perform(post("/api/auths/token")
                .content("hello world")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    //@Test // TODO fix tests
    public void shouldGiveAJWTIfTheUserIsRegistered() throws Exception {
        User user = new User();

        Credential mySuperPwd = new Credential(user.getEmail(), "mySuperPwd");

        String expectedToken = authenticationService.provideToken(user);

        mvc.perform(post("/api/auths/token")
                .servletPath("/api/auths/token")
                .content(toJson(mySuperPwd))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(expectedToken));

    }

    //@Test // TODO fix tests
    public void shouldSayHello() throws Exception {
        User user = new User();

        when(userService
                .getUser(
                        user.getId()
                )
        ).thenReturn(user);

        Credential mySuperPwd = new Credential(user.getEmail(), "mySuperPwd");

        String token = authenticationService.provideToken(user);

        mvc.perform(get("/api/auths/hello")
                .servletPath("/api/auths/hello")
                .header("X-Authorization", token)
                .content(toJson(mySuperPwd))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello ! " + user.hashCode()));

    }


}
