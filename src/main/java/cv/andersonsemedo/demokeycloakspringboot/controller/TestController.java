package cv.andersonsemedo.demokeycloakspringboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("home");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('role_user')")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("user");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('role_admin')")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("admin");
    }



}
