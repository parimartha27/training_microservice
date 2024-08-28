package com.crud.microservice.service;

import com.crud.microservice.controller.BookRepository;
import com.crud.microservice.dto.BookRequest;
import com.crud.microservice.dto.BookResponse;
import com.crud.microservice.entity.BookEntity;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookResponse> findAllBooks(){
        List<BookResponse> responses = new ArrayList<>();
        List<BookEntity> books = bookRepository.findAll();

        books.forEach(bookEntity -> {
            BookResponse bookDto = BookResponse.builder()
                    .title(bookEntity.getTitle())
                    .author(bookEntity.getAuthor())
                    .releaseDate(bookEntity.getReleaseDate())
                    .build();
            responses.add(bookDto);
        });

        return responses;
    }
    public BookResponse findBookById(Long id) {
        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        if(bookEntity.isPresent()) {
            BookEntity data = bookEntity.get();

            return BookResponse.builder()
                    .title(data.getTitle())
                    .author(data.getAuthor())
                    .releaseDate(data.getReleaseDate())
                    .build();
        }
        return null;
    }

    public String addBook(BookRequest request) {
        if (StringUtils.isNotEmpty(request.getTitle()) &&
                StringUtils.isNotEmpty(request.getAuthor()) &&
                request.getReleaseDate() > 0
        ) {
            BookEntity bookEntity = new BookEntity();
            bookEntity.setTitle(request.getTitle());
            bookEntity.setAuthor(request.getAuthor());
            bookEntity.setReleaseDate(request.getReleaseDate());

            bookRepository.save(bookEntity);
            return "Success adding book";
        }

        return "Error adding book";
    }

    public String editBook(Long id){
        Optional<BookEntity> bookEntity = bookRepository.findById(id);

        if(bookEntity.isPresent()) {
            BookEntity data = bookEntity.get();


        }

        return "Failed to edit book";
    }
}
