package dev.concat.vab.securecapita.repository;

import dev.concat.vab.securecapita.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
    @author: Bang Vo Anh 
    @since: 10/31/2023 11:06 PM
    @description:
    @update:
            

**/

@Repository
public interface IUserEntityRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmailIgnoreCase(String email);
    Boolean existsByEmail(String email);
}
