package dev.manideeplanka.bookerang.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;

public class JwtMiddleware {
    public static void validate(Context ctx) {
        Dotenv dotenv = Dotenv.load();

        String authHeader = ctx.header("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedResponse();
        }

        String token = authHeader.replace("Bearer ", "");
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(dotenv.get("JWT_SECRET"))).build();
        String username = verifier.verify(token).getSubject();
        ctx.attribute("username", username);
    }
}
