package dev.concat.vab.securecapita.repository;

import dev.concat.vab.securecapita.entity.ConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 
    @author: Bang Vo Anh 
    @since: 10/31/2023 11:10 PM
    @description:
    @update:
            

**/

@Repository
public interface IConfirmationEntityRepository extends JpaRepository<ConfirmationEntity,Long> {
    ConfirmationEntity findByToken(String token);


}
