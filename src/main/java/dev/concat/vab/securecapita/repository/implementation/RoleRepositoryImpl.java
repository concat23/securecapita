package dev.concat.vab.securecapita.repository.implementation;

import dev.concat.vab.securecapita.domain.Role;
import dev.concat.vab.securecapita.exception.ApiException;
import dev.concat.vab.securecapita.repository.IRoleRepository;
import dev.concat.vab.securecapita.rowmapper.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static dev.concat.vab.securecapita.enumeration.RoleType.ROLE_USER;
import static dev.concat.vab.securecapita.query.RoleQuery.INSERT_ROLE_TO_USER_QUERY;
import static dev.concat.vab.securecapita.query.RoleQuery.SELECT_ROLE_BY_NAME_QUERY;
import static java.util.Objects.requireNonNull;


@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements IRoleRepository<Role> {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection<Role> list(int page, int pageSize) {
        return null;
    }

    @Override
    public Role get(Long id) {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        try {
            log.info("Adding role {} to user id: {}", roleName, userId);

            // Validate input data
            if (userId == null || roleName == null || roleName.isEmpty()) {
                throw new IllegalArgumentException("Invalid userId or roleName.");
            }

            // Retrieve the role by name
            Role role = getRoleByName(roleName);

            // Add the role to the user
            insertRoleToUser(userId,requireNonNull(role).getId());

        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: " + roleName);
        } catch (IllegalArgumentException ex) {
            throw new ApiException(ex.getMessage());
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    private Role getRoleByName(String roleName){
        Map<String, Object> params = new HashMap<>();
        params.put("name",roleName);
        return jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY,params,new RoleRowMapper());
    }

    private void insertRoleToUser(Long userId, Long roleId) {
        Map<String, Long> params = new HashMap<>();
        params.put("userId", userId);
        params.put("roleId", roleId);
        jdbc.update(INSERT_ROLE_TO_USER_QUERY, params);
    }

    @Override
    public Role getRoleByUserById(Long userId) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {

    }
}
