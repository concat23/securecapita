package dev.concat.vab.securecapita.resource;

import dev.concat.vab.securecapita.domain.HttpResponse;
import dev.concat.vab.securecapita.domain.User;
import dev.concat.vab.securecapita.dto.UserDTO;
import dev.concat.vab.securecapita.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserResource {
    private final IUserService iUserService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user){
      UserDTO userDTO =  iUserService.createUser(user);
        Map<String,UserDTO> map = new HashMap<>();
        map.put("user",userDTO);
      return ResponseEntity.created(getUri()).body(
              HttpResponse.builder()
                            .timeStamp(now().toString())
                            .data(map)
                            .message("User created")
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .build());

    }

    private URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/<userId>").toUriString());
    }
}
