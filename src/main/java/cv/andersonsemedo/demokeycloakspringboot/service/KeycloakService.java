package cv.andersonsemedo.demokeycloakspringboot.service;

import cv.andersonsemedo.demokeycloakspringboot.dto.UserInfoDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class KeycloakService {

     public UserInfoDto getUserInfo() {
         Map<String, Object> claims = getClaims();
         if (claims != null) {
             String id = (String) claims.get("sub");
             String name = (String) claims.get("given_name");
             String surname = (String) claims.get("family_name");
             String email = (String) claims.get("email");
             int age = 0;
             LocalDate birthdate = null;
             if (claims.get("birthdate") != null) {
                 birthdate = LocalDate.parse(String.valueOf(claims.get("birthdate")));
                 age = LocalDate.now().compareTo(birthdate);
             }
             return new UserInfoDto(id, name, surname, age, birthdate, email);
         }
         return null;
     }

     private Map<String, Object> getClaims() {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         if (authentication != null) {
             Jwt jwt = (Jwt) authentication.getPrincipal();
             return jwt.getClaims();
         }
         return null;
     }
}
