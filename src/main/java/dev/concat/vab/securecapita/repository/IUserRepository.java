package dev.concat.vab.securecapita.repository;

import dev.concat.vab.securecapita.domain.User;

import java.util.Collection;

public interface IUserRepository <T extends User>{
    /* Basic CRUD Operations */
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    boolean delete(Long id);

    /* More Complex Operations */
}
