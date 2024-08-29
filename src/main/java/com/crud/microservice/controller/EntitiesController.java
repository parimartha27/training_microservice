package com.crud.microservice.controller;

import com.crud.microservice.dto.EntitiesRequest;
import com.crud.microservice.dto.EntitiesResponse;
import com.crud.microservice.service.EntitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entities")
@RequiredArgsConstructor
public class EntitiesController {

    private final EntitiesService entitiesService;

    @GetMapping()
    public List<EntitiesResponse> findAll() {
        return entitiesService.findAllEntity();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findEntityById(@PathVariable("id") Long id) {
        return entitiesService.findEntityById(id);
    }

    @PostMapping()
    public ResponseEntity<String> addEntity(@RequestBody EntitiesRequest request) {
        return entitiesService.addEntity(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEntityById(
            @PathVariable("id") Long id,
            @RequestBody EntitiesRequest request)
    {
        return entitiesService.editEntityById(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEntityById(@PathVariable("id") Long id)
    {
        return entitiesService.deleteEntityById(id);
    }
}
