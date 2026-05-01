package dev.manideeplanka.bookerang.repositories;

import dev.manideeplanka.bookerang.common.DatabaseConfig;
import org.jdbi.v3.core.Jdbi;

import java.util.UUID;

public class BookRepository {

    Jdbi jdbi;

    public BookRepository() {
        this.jdbi = DatabaseConfig.getJdbi();
    }

    public String addBook(String author, String title, String owner) {
        return jdbi.inTransaction(handle -> {
            UUID authorId = handle.createQuery("""
                            INSERT into authors (name) VALUES (:name)
                            ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name
                            RETURNING author_id
                            """)
                    .bind("name", author)
                    .mapTo(UUID.class)
                    .one();


            UUID bookId = handle.createQuery("""
                            INSERT into books (title, author_id) VALUES (:title, :authorId)
                            ON CONFLICT (title) DO UPDATE SET title = EXCLUDED.title
                            RETURNING book_id
                            """)
                    .bind("title", title)
                    .bind("authorId", authorId)
                    .mapTo(UUID.class)
                    .one();

            UUID copyId = handle.createQuery("""
                            INSERT into copies (book_id, owner_id) VALUES (:bookId, :owner)
                            RETURNING copy_id
                            """)
                    .bind("bookId", bookId)
                    .bind("owner", owner)
                    .mapTo(UUID.class)
                    .one();

            return copyId.toString();
        });
    }
}
