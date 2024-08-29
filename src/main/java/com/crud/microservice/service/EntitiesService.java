package com.crud.microservice.service;

import com.crud.microservice.dto.BookRequest;
import com.crud.microservice.dto.BookResponse;
import com.crud.microservice.dto.EntitiesRequest;
import com.crud.microservice.dto.EntitiesResponse;
import com.crud.microservice.entity.BookEntity;
import com.crud.microservice.entity.EntitiesEntity;
import com.crud.microservice.repository.EntitiesRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntitiesService {
    @Autowired
    private EntitiesRepository entitiesRepository;

    public List<EntitiesResponse> findAllEntity(){
        List<EntitiesEntity> books = entitiesRepository.findAll();

        return books.stream()
                .map(entity -> EntitiesResponse.builder()
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public ResponseEntity<Object> findEntityById(Long id) {
        Optional<EntitiesEntity> entity = entitiesRepository.findById(id);

        if(entity.isPresent()) {
            EntitiesEntity data = entity.get();

            return new ResponseEntity<>(EntitiesResponse.builder()
                    .name(data.getName())
                    .description(data.getDescription())
                    .build(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> addEntity(EntitiesRequest request) {

        if (StringUtils.isBlank(request.getName()) ||
                StringUtils.isBlank(request.getDescription())) {

            return new ResponseEntity<>("Error adding entity: Invalid data provided", HttpStatus.BAD_REQUEST);
        }

        EntitiesEntity entity = new EntitiesEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());

        entitiesRepository.save(entity);

        return new ResponseEntity<>("Success adding Entity", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> editEntityById(Long id, EntitiesRequest request){

        if (StringUtils.isBlank(request.getName()) ||
                StringUtils.isBlank(request.getDescription())) {

            return new ResponseEntity<>("Error edit Entity: Invalid data provided", HttpStatus.BAD_REQUEST);
        }

        Optional<EntitiesEntity> entity = entitiesRepository.findById(id);

        if(entity.isPresent()){
            EntitiesEntity updatedData = entity.get();
            updatedData.setName(request.getName());
            updatedData.setDescription(request.getDescription());

            entitiesRepository.save(updatedData);
            return new ResponseEntity<>("Success updating Entity's data", HttpStatus.OK);
        }

        return new ResponseEntity<>("Entity not found! Can't do update process", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> deleteEntityById(Long id){
        Optional<EntitiesEntity> entity = entitiesRepository.findById(id);

        if(entity.isEmpty()){
            return new ResponseEntity<>("Entity not found. Can't do delete process", HttpStatus.NOT_FOUND);
        }

        entitiesRepository.deleteById(id);

        return new ResponseEntity<>("Success deleting Entity", HttpStatus.OK);
    }
}
