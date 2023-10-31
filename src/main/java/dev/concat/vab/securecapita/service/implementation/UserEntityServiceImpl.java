package dev.concat.vab.securecapita.service.implementation;

import dev.concat.vab.securecapita.entity.ConfirmationEntity;
import dev.concat.vab.securecapita.entity.UserEntity;
import dev.concat.vab.securecapita.repository.IConfirmationEntityRepository;
import dev.concat.vab.securecapita.repository.IUserEntityRepository;
import dev.concat.vab.securecapita.service.IEmailService;
import dev.concat.vab.securecapita.service.IUserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
    @author: Bang Vo Anh 
    @since: 10/31/2023 11:13 PM
    @description:
    @update:
            

**/

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements IUserEntityService {

    private final IUserEntityRepository iUserEntityRepository;
    private final IConfirmationEntityRepository iConfirmationEntityRepository;
    private final IEmailService iEmailService;

    @Override
    public UserEntity saveUser(UserEntity user) {
        if (iUserEntityRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        user.setIsEnabled(false);
        iUserEntityRepository.save(user);
        ConfirmationEntity confirmation = new ConfirmationEntity(user);
        iConfirmationEntityRepository.save(confirmation);

        // To do send email to user with token
        iEmailService.sendSimpleMailMessage(user.getName(),user.getEmail(),confirmation.getToken());
        return user;
    }

    @Override
    public Boolean verifyToken(String token) {
        ConfirmationEntity confirmation = iConfirmationEntityRepository.findByToken(token);
        UserEntity user = iUserEntityRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setIsEnabled(true);
        iUserEntityRepository.save(user);
        // iConfirmationEntityRepository.delete(confirmation);
        return Boolean.TRUE;
    }
}
