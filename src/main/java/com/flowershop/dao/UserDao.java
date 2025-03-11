package com.flowershop.dao;

import com.flowershop.model.User;
import com.flowershop.model.UserProfile;
import com.flowershop.model.dto.UserProfileDto;

import java.util.List;

/**
 * Interface for accessing User information from the persistence layer
 */
public interface UserDao {

    /**
     * Get a list of all registered users.
     * @return list of all users or an empty list
     */
    List<User> getUsers();

    /**
     * Get details on specific user by user id.
     * @param userId user id to check
     * @return User details or null if not found
     */
    User getUserById(int userId);

    /**
     * Get user details on specific user by username.
     * @param username username to check
     * @return User details or null if not found
     */
    User getUserByUsername(String username);

    /**
     * Create a new user
     * @param newUser
     * @return User details after creating
     */
    User createUser(User newUser);

    /**
     * Get a user's profile by username
     * @param username username to check
     * @return User profile after creating or null if not found
     */
    UserProfile getUserProfileByUsername(String username);

    /**
     * Get a user's profile by user id
     * @param userId user id to check
     * @return User profile after creating or null if not found
     */
    UserProfile getUserProfileByUserId(int userId);

    /**
     * Update user's profile by user id
     * @param userId user id to update
     * @param userProfile new profile data
     * @return User profile after updating or null if not found
     */
    UserProfile updateUserProfile(int userId, UserProfileDto userProfile);
}
