package cv.andersonsemedo.demokeycloakspringboot.controller;

import cv.andersonsemedo.demokeycloakspringboot.dto.UserInfoDto;
import cv.andersonsemedo.demokeycloakspringboot.service.KeycloakService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class KeycloakController {

    private final KeycloakService keycloakService;

    public KeycloakController(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @GetMapping("/user-info")
    public ResponseEntity<UserInfoDto> getUserInfo() {
        UserInfoDto userInfo = keycloakService.getUserInfo();
        return ResponseEntity.ok(userInfo);
    }
}
