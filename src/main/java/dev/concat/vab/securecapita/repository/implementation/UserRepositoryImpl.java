package dev.concat.vab.securecapita.repository.implementation;

import dev.concat.vab.securecapita.domain.Role;
import dev.concat.vab.securecapita.domain.User;
import dev.concat.vab.securecapita.exception.ApiException;
import dev.concat.vab.securecapita.repository.IRoleRepository;
import dev.concat.vab.securecapita.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static dev.concat.vab.securecapita.enumeration.RoleType.ROLE_USER;
import static dev.concat.vab.securecapita.enumeration.VerificationType.ACCOUNT;
import static dev.concat.vab.securecapita.query.UserQuery.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements IUserRepository<User> {

//    private static final  String COUNT_USER_EMAIL_QUERY = "";
//    private static final String INSERT_USER_QUERY = "";
//    private static final String INSERT_VERIFICATION_QUERY = "";
//    private static final String INSERT_VERIFICATION_URL_QUERY = "";
    private final NamedParameterJdbcTemplate jdbc;
    private final IRoleRepository<Role> iRoleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User user) {
        // Check the email is unique
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already in use. Please use a different email and try again.");
        // Save new user
        try{
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters= getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY,parameters,holder);
            user.setId(requireNonNull(holder.getKey().longValue()));
            iRoleRepository.addRoleToUser(user.getId(),ROLE_USER.name());
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(),ACCOUNT.getType());
            MapSqlParameterSource paramMap = new MapSqlParameterSource();
            paramMap.addValue("userId", user.getId());
            paramMap.addValue("url", verificationUrl);
            jdbc.update(INSERT_VERIFICATION_URL_QUERY, paramMap);
            //????? 3:40:09 / 5:09:32
//            emailService.sendVerificationUrl(user.getFirstName(),user.getEmail(),verificationUrl,ACCOUNT.getType());
            user.setEnabled(false);
            user.setNotLocked(true);
            return user;
        }catch (EmptyResultDataAccessException exception){
            throw new ApiException("No role found by name: " + ROLE_USER.name());
        }catch (Exception exception){
            throw new ApiException("An error occurred. Please try again.");
        }
        // Add role to the user

        // Send verification URL

        // Save URL in verification table

        // Send email to user with verification URL

        // Return the nearly created user


        // If any errors, throw exception with proper message

        return null;
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName",user.getFirstName())
                .addValue("lastName",user.getLastName())
                .addValue("email",user.getEmail())
                .addValue("password",encoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type){
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/" + type + "/"+key).toUriString();
    }

    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    private Integer getEmailCount(String email) {
//        String COUNT_USER_EMAIL_QUERY = "SELECT COUNT(*) FROM your_table WHERE email = :email";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("email", email);

        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, paramMap, Integer.class);
    }
}
