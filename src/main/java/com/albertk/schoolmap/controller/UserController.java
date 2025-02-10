package com.albertk.schoolmap.controller;


import com.albertk.schoolmap.model.User;
import com.albertk.schoolmap.response.ApiResponse;
import com.albertk.schoolmap.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllSchools(@RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers(page,size));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addSchool(@RequestBody User data){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(data));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ApiResponse<?>> getSchool(@PathVariable Long id ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ApiResponse<?>> updateSchool(@RequestBody User data, @PathVariable Long id ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, data));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ApiResponse<?>> deleteSchool(@PathVariable Long id ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(id));
    }

}
