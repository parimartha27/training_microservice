package com.crud.microservice.controller;

import com.crud.microservice.dto.UserRequest;
import com.crud.microservice.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/get/{id}")
    public UserResponse getUserById(@PathVariable("id") String id) {
        List<UserResponse> allUsers = getAllUser();

        for(UserResponse user: allUsers){
            if(user.getId().equals(id)){
                return user;
            }
        }

        return new UserResponse(id,"Not Found");
    }

    @PostMapping("/add")
    public String addUser(@RequestBody UserRequest request) {
        List<UserResponse> allUsers = getAllUser();

        UserResponse response = new UserResponse(request.getId(), request.getName());
        allUsers.add(response);

        return "Success adding new user with id " + request.getId() + " and name: " + request.getName();
    }

    private List<UserResponse> getAllUser(){
        List<UserResponse> userList = new ArrayList<>();

        UserResponse user1 = new UserResponse("1", "Pari");
        UserResponse user2 = new UserResponse("2", "William");
        UserResponse user3 = new UserResponse("3", "Harta");

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        return userList;
    }
}