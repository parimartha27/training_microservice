package com.crud.microservice.service;

import com.crud.microservice.repository.BookRepository;
import com.crud.microservice.dto.BookRequest;
import com.crud.microservice.dto.BookResponse;
import com.crud.microservice.entity.BookEntity;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookResponse> findAllBooks(){
        List<BookEntity> books = bookRepository.findAll();

        return books.stream()
                .map(bookEntity -> BookResponse.builder()
                        .title(bookEntity.getTitle())
                        .author(bookEntity.getAuthor())
                        .releaseDate(bookEntity.getReleaseDate())
                        .build())
                .collect(Collectors.toList());
    }

    public ResponseEntity<Object> findBookById(Long id) {
        Optional<BookEntity> bookEntity = bookRepository.findById(id);

        if(bookEntity.isPresent()) {
            BookEntity data = bookEntity.get();

            return new ResponseEntity<>(BookResponse.builder()
                    .title(data.getTitle())
                    .author(data.getAuthor())
                    .releaseDate(data.getReleaseDate())
                    .build(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> addBook(BookRequest request) {

        if (StringUtils.isBlank(request.getTitle()) ||
                StringUtils.isBlank(request.getAuthor()) ||
                request.getReleaseDate() <= 0) {

            return new ResponseEntity<>("Error adding book: Invalid data provided", HttpStatus.BAD_REQUEST);
        }

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(request.getTitle());
        bookEntity.setAuthor(request.getAuthor());
        bookEntity.setReleaseDate(request.getReleaseDate());

        bookRepository.save(bookEntity);

        return new ResponseEntity<>("Success adding book", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> editBookById(Long id, BookRequest request){

        if (StringUtils.isBlank(request.getTitle()) ||
                StringUtils.isBlank(request.getAuthor()) ||
                request.getReleaseDate() <= 0) {

            return new ResponseEntity<>("Error edit book: Invalid data provided", HttpStatus.BAD_REQUEST);
        }

        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        if(bookEntity.isPresent()){
            BookEntity updatedData = bookEntity.get();
            updatedData.setTitle(request.getTitle());
            updatedData.setAuthor(request.getAuthor());
            updatedData.setReleaseDate(request.getReleaseDate());

            bookRepository.save(updatedData);

            return new ResponseEntity<>("Success updating book's data", HttpStatus.OK);
        }

        return new ResponseEntity<>("Book not found! Can't do update process", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> deleteBookById(Long id){
        Optional<BookEntity> bookEntity = bookRepository.findById(id);

        if(bookEntity.isEmpty()){
            return new ResponseEntity<>("Book not found. Can't do delete process", HttpStatus.NOT_FOUND);
        }

        bookRepository.deleteById(id);

        return new ResponseEntity<>("Success deleting book", HttpStatus.OK);
    }
}
