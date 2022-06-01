package com.kepa.springlibraryapp.book;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select a from Book a where lower(a.name) like lower(concat('%', :search, '%')) " +
            "or lower(a.author) like lower(concat('%', :search, '%'))")
    List<Book> findAllByNameOrAuthor(@Param("search") String search);

}
