package dev.concat.vab.securecapita.resource;

import dev.concat.vab.securecapita.domain.HttpResponse;
import dev.concat.vab.securecapita.entity.UserEntity;
import dev.concat.vab.securecapita.service.IUserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
    @author: Bang Vo Anh 
    @since: 10/31/2023 11:23 PM
    @description:
    @update:
            

**/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/users")
public class UserEntityResource {

    private final IUserEntityService iUserEntityService;

    @PostMapping
    public ResponseEntity<HttpResponse> createUser(@RequestBody UserEntity user){
        UserEntity newUser = iUserEntityService.saveUser(user);
        Map<String, UserEntity> map = new HashMap<>();
        map.put("user", newUser);
        return ResponseEntity.created(URI.create("")).body(
                HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                .data(map)
                .message("User created")
                .status(CREATED)
                .statusCode(CREATED.value()).build()
        );
    }

    @GetMapping
    public ResponseEntity<HttpResponse> confirmUserAccount(@RequestParam("token") String token){
        Boolean isSuccess = iUserEntityService.verifyToken(token);
        Map<String, Object> map;
        map = Map.of("Success", isSuccess);
        return ResponseEntity.ok().body(
                HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                        .data(map)
                        .message("Account Verified")
                        .status(OK)
                        .statusCode(OK.value()).build()
        );
    }


}
