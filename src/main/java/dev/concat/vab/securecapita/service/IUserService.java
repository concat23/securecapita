package dev.concat.vab.securecapita.service;

import dev.concat.vab.securecapita.domain.User;
import dev.concat.vab.securecapita.dto.UserDTO;

public interface IUserService {
    UserDTO createUser(User user);

}
