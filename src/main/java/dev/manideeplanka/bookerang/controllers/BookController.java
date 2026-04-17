package dev.manideeplanka.bookerang.controllers;

import dev.manideeplanka.bookerang.models.AddBookReq;
import dev.manideeplanka.bookerang.models.IdRes;
import dev.manideeplanka.bookerang.services.BookService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class BookController {

    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void addBook(Context ctx) {
        AddBookReq req = ctx.bodyAsClass(AddBookReq.class);
        String username = ctx.attribute("username");
        String copyId = bookService.addBook(req, username);
        ctx.json(new IdRes("Book added to your collection", copyId)).status(HttpStatus.OK);
    }
}
