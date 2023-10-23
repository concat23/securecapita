package dev.concat.vab.securecapita.repository;

import dev.concat.vab.securecapita.domain.Role;

import java.util.Collection;

public interface IRoleRepository <T extends Role>{
    /* Basic CRUD Operations */
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    boolean delete(Long id);

    /* More Complex Operations */

    void addRoleToUser(Long id, String roleName);
    Role getRoleByUserById(Long userId);
    Role getRoleByUserEmail(String email);
    void updateUserRole(Long userId, String roleName);
}
