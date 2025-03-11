package com.flowershop.dao;

import java.util.ArrayList;
import java.util.List;

import com.flowershop.exception.DaoException;
import com.flowershop.model.UserProfile;
import com.flowershop.model.dto.UserProfileDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.flowershop.model.User;

/**
 * Class implementation of the {@link UserDao} interface that uses
 * JDBC for database interaction
 *
 * @see UserDao
 */
@Component
public class JdbcUserDao implements UserDao {

    private final String USERS_SQL_SELECT = "SELECT u.user_id, u.username, u.password_hash, u.role FROM users AS u ";
    private final String PROFILE_SQL_SELECT = "SELECT p.profile_id, p.user_id, p.first_name, p.last_name, p.email FROM profile AS p ";
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(int userId) {

        User user = null;
        String sql = USERS_SQL_SELECT + "WHERE user_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                user = mapRowToUser(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public List<User> getUsers() {

        List<User> users = new ArrayList<>();
        String sql = USERS_SQL_SELECT + " ORDER BY username";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                User user = mapRowToUser(results);
                users.add(user);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return users;
    }

    @Override
    public User getUserByUsername(String username) {

        if (username == null) {
            username = "";
        }
        User user = null;
        String sql = USERS_SQL_SELECT + "WHERE username = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            if (results.next()) {
                user = mapRowToUser(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public User createUser(User newUser) {

        User user = null;
        String insertUserSql = "INSERT INTO users " +
                "(username, password_hash, role) " +
                "VALUES (?, ?, ?) " +
                "RETURNING user_id";

        if (newUser.getHashedPassword() == null) {
            throw new DaoException("User cannot be created with null password");
        }
        try {
            String passwordHash = new BCryptPasswordEncoder().encode(newUser.getHashedPassword());

            Integer userId = jdbcTemplate.queryForObject(insertUserSql, int.class,
                    newUser.getUsername(), passwordHash, newUser.getRole());
            user =  getUserById(userId);
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return user;
    }

    @Override
    public UserProfile getUserProfileByUsername(String username) {

        UserProfile profile = null;

        // 1. Sql string
        //      recommend testing in PGAdmin first
        String sql = PROFILE_SQL_SELECT +
                "JOIN users AS u ON u.user_id = p.user_id " +
                "WHERE u.username = ?;";

        try {
            // 2. send the query
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);

            // 3. map to object
            if (result.next()) {
                profile = mapRowToProfile(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return profile;
    }

    public UserProfile getUserProfileByUserId(int userId) {

        UserProfile profile = null;
        String sql = PROFILE_SQL_SELECT + "WHERE p.user_id = ?";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);

            if (result.next()) {
                profile = mapRowToProfile(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return profile;
    }

    @Override
    public UserProfile updateUserProfile(int userId , UserProfileDto userProfile) {

        UserProfile updatedUserProfile = null;
        String sql = "UPDATE profile " +
            "SET first_name = ?, last_name = ?, email = ? " +
            "WHERE user_id = ?;";

        try {
            int rowsAffected = jdbcTemplate.update(sql,
                    userProfile.getFirstName(),
                    userProfile.getLastName(),
                    userProfile.getEmail(),
                    userId);

            if(rowsAffected == 0){
                throw new DaoException("Zero rows affected, expected at least one");
            }

            updatedUserProfile = getUserProfileByUserId(userId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return updatedUserProfile;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setHashedPassword(rs.getString("password_hash"));
        user.setRole(rs.getString("role"));
        return user;
    }

    private UserProfile mapRowToProfile(SqlRowSet rs){
        return new UserProfile(
                rs.getInt("profile_id"),
                rs.getInt("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email")
        );
    }
}
