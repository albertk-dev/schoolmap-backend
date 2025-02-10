package com.albertk.schoolmap.service;

import com.albertk.schoolmap.dto.SchoolDTO;
import com.albertk.schoolmap.exception.ResourceNotFoundException;
import com.albertk.schoolmap.mapper.SchoolMapper;
import com.albertk.schoolmap.model.School;
import com.albertk.schoolmap.repository.SchoolRepository;
import com.albertk.schoolmap.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public ApiResponse<SchoolDTO> getSchoolById(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));
        return ApiResponse.success(SchoolMapper.toDTO(school), "School found");
    }

    public ApiResponse<?> getAllSchools(Integer page, Integer size) {
        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<School> schoolPage = schoolRepository.findAll(pageable);
            Set<SchoolDTO> schoolDTOs = schoolPage.getContent().stream().map(SchoolMapper::toDTO).collect(Collectors.toSet());
            return ApiResponse.successWithPagination(
                    schoolDTOs,
                    "Schools retrieved successfully",
                    page,
                    schoolPage.getTotalPages(),
                    size,
                    schoolPage.getTotalElements()
            );
        } else {
            Set<SchoolDTO> schoolDTOs = schoolRepository.findAll().stream().map(SchoolMapper::toDTO).collect(Collectors.toSet());
            return ApiResponse.success(schoolDTOs, "All schools retrieved");
        }
    }

    public ApiResponse<SchoolDTO> createSchool(School school) {
        School savedSchool = schoolRepository.save(school);
        return ApiResponse.success(SchoolMapper.toDTO(savedSchool), "School created successfully");
    }

    public ApiResponse<SchoolDTO> updateSchool(Long id, School updatedSchool) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));

        school.setName(updatedSchool.getName());
        school.setDescription(updatedSchool.getDescription());
        school.setImageUrl(updatedSchool.getImageUrl());
        school.setTeachingLanguage(updatedSchool.getTeachingLanguage());
        school.setLatitude(updatedSchool.getLatitude());
        school.setLongitude(updatedSchool.getLongitude());
        school.setType(updatedSchool.getType());
        school.setCategory(updatedSchool.getCategory());

        School savedSchool = schoolRepository.save(school);
        return ApiResponse.success(SchoolMapper.toDTO(savedSchool), "School updated successfully");
    }

    public ApiResponse<?> deleteSchool(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));

        schoolRepository.delete(school);
        return ApiResponse.success(null, "School deleted successfully");
    }
}


