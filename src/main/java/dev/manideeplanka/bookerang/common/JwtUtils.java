package dev.manideeplanka.bookerang.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Date;

public class JwtUtils {

    public static String createToken(String username) {
        Dotenv dotenv = Dotenv.load();

        return JWT.create().withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86_400_000))
                .sign(Algorithm.HMAC256(dotenv.get("JWT_SECRET")));
    }
}
