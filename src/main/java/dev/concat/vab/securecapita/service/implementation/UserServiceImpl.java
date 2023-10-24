package dev.concat.vab.securecapita.service.implementation;

import dev.concat.vab.securecapita.domain.User;
import dev.concat.vab.securecapita.dto.UserDTO;
import dev.concat.vab.securecapita.dtomapper.UserDTOMapper;
import dev.concat.vab.securecapita.repository.IUserRepository;
import dev.concat.vab.securecapita.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final IUserRepository<User> iUserRepository;

    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(iUserRepository.create(user));
    }
}
