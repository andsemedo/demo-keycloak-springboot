package cv.andersonsemedo.demokeycloakspringboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.auth.converter.principle-attribute}")
    private String principleAttribute;
    @Value("#{'${jwt.auth.converter.resource-ids}'.split(',')}")
    private List<String> resourceIds;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        Set<GrantedAuthority> authorities = new HashSet<>();

        if (resourceAccess != null) {
            for (String resourceId : resourceIds) {
                Map<String, Object> resource = (Map<String, Object>) resourceAccess.get(resourceId);
                if (resource != null && resource.get("roles") instanceof Collection<?> roles) {
                    for (Object role : roles) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                    }
                }
            }
        }

        System.out.println("Authorities: " + authorities);

        return new JwtAuthenticationToken(jwt, authorities, getPrincipleClaimName(jwt));
    }

    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (principleAttribute != null) {
            claimName = principleAttribute;
        }
        return jwt.getClaim(claimName);
    }

}
