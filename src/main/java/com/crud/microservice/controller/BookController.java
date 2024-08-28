package com.crud.microservice.controller;

import com.crud.microservice.dto.BookRequest;
import com.crud.microservice.dto.BookResponse;
import com.crud.microservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    @GetMapping("/buku")
    public List<BookResponse> findAll() {
        return bookService.findAllBooks();
    }
    @GetMapping("/buku/{id}")
    public BookResponse getBookById(@PathVariable("id") Long id) {
        return bookService.findBookById(id);
    }

    @PostMapping("/buku")
    public String getBookById(@RequestBody BookRequest request) {
        return bookService.addBook(request);
    }

    @PutMapping("/buku/{id}")
    public String getBookById(@PathVariable("id") Long id) {
        return bookService.addBook(request);
    }
}
