package com.kepa.springlibraryapp.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
class BookService {
    private final BookRepository bookRepository;

    List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }

    BookDto findById(Long id) {
        Optional<BookDto> bookDto = bookRepository.findById(id).map(BookMapper::toDto);
        return bookDto.orElseThrow(BookNotFoundException::new);
    }

    List<BookDto> findAllByNameOrAuthor(String text) {
        return bookRepository.findAllByNameOrAuthor(text)
                .stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());

    }
}
