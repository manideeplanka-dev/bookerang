package dev.manideeplanka.bookerang.models;


import lombok.Data;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@Data
public class Copy {
    String id;

    @ColumnName("owner_id")
    String ownerId;

    @ColumnName("book_id")
    String bookId;
}
