package com.crud.microservice.controller;

import com.crud.microservice.dto.BookRequest;
import com.crud.microservice.dto.BookResponse;
import com.crud.microservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buku")
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public List<BookResponse> findAll() {
        return bookService.findAllBooks();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findBookById(@PathVariable("id") Long id) {
        return bookService.findBookById(id);
    }

    @PostMapping()
    public ResponseEntity<String> addBook(@RequestBody BookRequest request) {
        return bookService.addBook(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBookById(
            @PathVariable("id") Long id,
            @RequestBody BookRequest request)
    {
        return bookService.editBookById(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBookById(@PathVariable("id") Long id)
    {
        return bookService.deleteBookById(id);
    }

}
