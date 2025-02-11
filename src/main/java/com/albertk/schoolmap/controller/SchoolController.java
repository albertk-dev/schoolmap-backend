package com.albertk.schoolmap.controller;


import com.albertk.schoolmap.model.School;
import com.albertk.schoolmap.response.ApiResponse;
import com.albertk.schoolmap.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllSchools(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.OK).body(schoolService.getAllSchools(page,size));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addSchool(@RequestBody School data){
        return ResponseEntity.status(HttpStatus.CREATED).body(schoolService.createSchool(data));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ApiResponse<?>> getSchool(@PathVariable Long id ){
        return ResponseEntity.status(HttpStatus.OK).body(schoolService.getSchoolById(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ApiResponse<?>> updateSchool(@RequestBody School data,@PathVariable Long id ){
        return ResponseEntity.status(HttpStatus.OK).body(schoolService.updateSchool(id, data));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ApiResponse<?>> deleteSchool(@PathVariable Long id ){
        return ResponseEntity.status(HttpStatus.OK).body(schoolService.deleteSchool(id));
    }

    @PostMapping("many")
    public ResponseEntity<ApiResponse<?>> addManySchool(@RequestBody School[] data){
        System.out.println("insert Many data");
        return ResponseEntity.status(HttpStatus.CREATED).body(schoolService.createManySchool(data));
    }


}
