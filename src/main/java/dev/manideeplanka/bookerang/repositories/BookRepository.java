package dev.manideeplanka.bookerang.repositories;

import dev.manideeplanka.bookerang.common.DatabaseConfig;
import dev.manideeplanka.bookerang.models.Book;
import org.jdbi.v3.core.Jdbi;

import java.util.UUID;

public class BookRepository {

    Jdbi jdbi;

    public BookRepository() {
        this.jdbi = DatabaseConfig.getJdbi();
    }

    public String addBook(String author, String title, String owner) {
        return jdbi.inTransaction(handle -> {
            UUID authorId = handle.createUpdate("INSERT into authors (name) VALUES (:name)")
                    .bind("name", author)
                    .executeAndReturnGeneratedKeys("author_id")
                    .mapTo(UUID.class)
                    .one();


            UUID id = handle.createUpdate("INSERT into books (title, author_id) VALUES (:title, :authorId)")
                    .bind("title", title)
                    .bind("authorId", authorId)
                    .executeAndReturnGeneratedKeys("book_id")
                    .mapTo(UUID.class)
                    .one();

            UUID copyId = handle.createUpdate("INSERT into copies (book_id, owner_id) VALUES (:bookId, :owner)")
                    .bind("bookId", id)
                    .bind("owner", owner)
                    .executeAndReturnGeneratedKeys("copy_id")
                    .mapTo(UUID.class)
                    .one();

            return copyId.toString();
        });
    }
}
