package cv.andersonsemedo.demokeycloakspringboot.dto;

import java.time.LocalDate;

public record UserInfoDto(
        String id, String name, String surname, int age, LocalDate birthdate, String email
) {
}
