package com.flowershop.service;

import com.flowershop.dao.UserDao;
import com.flowershop.exception.UserNotFoundException;
import com.flowershop.model.User;
import com.flowershop.model.UserProfile;
import com.flowershop.model.dto.UserProfileDto;
import org.springframework.stereotype.Service;

/**
 * Service class for setting up User CRUD operations
 */
@Service
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Update a user's profile by username
     * @param username
     * @param userProfile
     * @return User profile data
     */
    public UserProfile updateProfile(String username, UserProfileDto userProfile){

        // 1.  Call getUserByUsername() from userDao to get a User object
        User user = userDao.getUserByUsername(username);

        if(user == null){
            throw new UserNotFoundException("ERROR: user not found: " + username);
        }

        // 2. Get the userId from the User object
        int userId = user.getId();

        // 3. Call the updateUserProfile() from the userDao with the userId
        UserProfile updatedProfile = userDao.updateUserProfile(userId, userProfile);

        return updatedProfile;
    }
}
