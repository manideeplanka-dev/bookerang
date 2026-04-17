package dev.manideeplanka.bookerang;

import dev.manideeplanka.bookerang.common.JwtMiddleware;
import dev.manideeplanka.bookerang.controllers.BookController;
import dev.manideeplanka.bookerang.controllers.UserController;
import dev.manideeplanka.bookerang.exceptions.InvalidCredentialsException;
import dev.manideeplanka.bookerang.exceptions.UserAlreadyExistsException;
import dev.manideeplanka.bookerang.exceptions.UserNotFoundException;
import dev.manideeplanka.bookerang.models.ErrorRes;
import dev.manideeplanka.bookerang.repositories.BookRepository;
import dev.manideeplanka.bookerang.repositories.UserRepository;
import dev.manideeplanka.bookerang.services.BookService;
import dev.manideeplanka.bookerang.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class BookerangApp {

    static void main() {

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);

        BookRepository bookRepository = new BookRepository();
        BookService bookService = new BookService(bookRepository);
        BookController bookController = new BookController(bookService);

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

            config.routes.post("/login", userController::login);
            config.routes.post("/signup", userController::signup);

            config.routes.before("/books/*", JwtMiddleware::validate);
            config.routes.post("/books/add", bookController::addBook);
        }).start(8080);


    }
}
