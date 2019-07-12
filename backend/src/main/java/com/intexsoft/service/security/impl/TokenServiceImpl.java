package com.intexsoft.service.security.impl;

import com.intexsoft.security.model.UserDetailsImpl;
import com.intexsoft.service.security.TokenService;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger LOGGER = LogManager.getLogger(TokenServiceImpl.class);

    private static final String INVALID_JWT_SIGNATURE_MESSAGE = "Invalid JWT signature";

    private static final String INVALID_JWT_TOKEN_MESSAGE = "Invalid JWT token";

    private static final String EXPIRED_JWT_TOKEN_MESSAGE = "Expired JWT token";

    private static final String UNSUPPORTED_JWT_TOKEN_MESSAGE = "Unsupported JWT token";

    private static final String JWT_CLAIMS_STRING_IS_EMPTY_MESSAGE = "JWT claims string is empty";

    private static final String JWT_SECRET = "secret";

    private static final Integer JWT_EXPIRATION_MILLIS = 600000;

    /**
     * generate new token
     *
     * @param authentication token for an authentication request
     * @return token
     */
    @Override
    public String generate(Authentication authentication) {
        return generate(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
    }

    /**
     * refresh old token
     *
     * @param token JWT
     * @return new token
     */
    @Override
    public String refresh(String token) {
        return generate(extractUsername(token));
    }


    /**
     * extract username from token
     *
     * @param token JWT
     * @return username
     */
    @Override
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    /**
     * validate token
     *
     * @param authToken JWT
     * @return true or trows Exception
     */
    @Override
    public boolean validate(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOGGER.error(INVALID_JWT_SIGNATURE_MESSAGE, e);
            throw e;
        } catch (MalformedJwtException e) {
            LOGGER.error(INVALID_JWT_TOKEN_MESSAGE, e);
            throw e;
        } catch (ExpiredJwtException e) {
            LOGGER.error(EXPIRED_JWT_TOKEN_MESSAGE, e);
            throw e;
        } catch (UnsupportedJwtException e) {
            LOGGER.error(UNSUPPORTED_JWT_TOKEN_MESSAGE, e);
            throw e;
        } catch (IllegalArgumentException e) {
            LOGGER.error(JWT_CLAIMS_STRING_IS_EMPTY_MESSAGE, e);
            throw e;
        }
    }

    private String generate(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MILLIS))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
}
