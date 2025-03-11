package com.flowershop.controller;

import com.flowershop.dao.UserDao;
import com.flowershop.exception.DaoException;
import com.flowershop.exception.UserNotFoundException;
import com.flowershop.model.UserProfile;
import com.flowershop.model.dto.UserProfileDto;
import com.flowershop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

/**
 * Controller used for all /user or /users endpoints
 */
@PreAuthorize("isAuthenticated()")
@RestController
public class UsersController {

    /*
     * TODO: Create a userDao class/interface
     */
    private UserDao userDao;
    private UserService userService;

    public UsersController(UserDao userDao, UserService userService){
        this.userDao = userDao;
        this.userService = userService;
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public UserProfile getUserProfile(Principal principal){

        /*
         * Call method in DAO to get a user's profile
         *  TODO: How do I know which user's profile to get?
         *   principal.getName() is available because this endpoint
         *   requires authentication, i.e. a token with the username is
         *   passed in with the request.
         */
        String username = principal.getName();
        UserProfile userProfile = userDao.getUserProfileByUsername(username);

        return userProfile;
    }

    @RequestMapping(path = "/user", method = RequestMethod.PUT)
    public UserProfile updateUserProfile(Principal principal,
                                         @RequestBody UserProfileDto profile)
    {
        /*
         * TODO: Call service class because extra business logic checks
         *  or DB calls are needed
         */
        UserProfile updatedProfile = null;
        try {

            updatedProfile = userService.updateProfile(principal.getName(), profile);

        // TODO: Service layer/class exceptions
        } catch (UserNotFoundException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

        // TODO: DAO exceptions
        } catch (DaoException e) {

            throw new ResponseStatusException(HttpStatus.CONFLICT, "ERROR: YOUR DATA IS CONFLICTED :/");

        }

        return updatedProfile;
    }
}
