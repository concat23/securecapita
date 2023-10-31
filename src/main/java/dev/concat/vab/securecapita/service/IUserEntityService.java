package dev.concat.vab.securecapita.service;

import dev.concat.vab.securecapita.entity.UserEntity;

public interface IUserEntityService {
    UserEntity saveUser(UserEntity user);
    Boolean verifyToken(String token);
}
