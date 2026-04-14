package dev.manideeplanka.bookerang;

import dev.manideeplanka.bookerang.controllers.UserController;
import dev.manideeplanka.bookerang.exceptions.InvalidCredentialsException;
import dev.manideeplanka.bookerang.exceptions.UserAlreadyExistsException;
import dev.manideeplanka.bookerang.exceptions.UserNotFoundException;
import dev.manideeplanka.bookerang.models.ErrorRes;
import dev.manideeplanka.bookerang.repositories.UserRepository;
import dev.manideeplanka.bookerang.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class BookerangApp {

    static void main() {

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);

        Javalin.create(config -> {
            config.routes.exception(UserNotFoundException.class, (e, ctx) -> {
                ctx.status(HttpStatus.NOT_FOUND).json(new ErrorRes("Wrong credentials, recheck"));
            });

            config.routes.exception(InvalidCredentialsException.class, (e, ctx) -> {
                ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorRes("Wrong credentials, recheck"));
            });

            config.routes.exception(UserAlreadyExistsException.class, (e, ctx) -> {
                ctx.status(HttpStatus.CONFLICT).json(new ErrorRes("User already exists, recheck"));
            });

            config.routes.post("/user/login", userController::login);
            config.routes.post("/user/signup", userController::signup);
        }).start(8080);


    }
}
