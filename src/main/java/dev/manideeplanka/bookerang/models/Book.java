package dev.manideeplanka.bookerang.models;


import lombok.Builder;
import lombok.Data;
import org.jdbi.v3.core.mapper.reflect.ColumnName;


@Builder
@Data
public class Book {
    String id;
    @ColumnName("author_id")
    String authorId;
    String title;
}
