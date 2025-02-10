package com.albertk.schoolmap.mapper;


import com.albertk.schoolmap.dto.SchoolDTO;
import com.albertk.schoolmap.model.School;

public class SchoolMapper {
    public static SchoolDTO toDTO(School school) {
        return SchoolDTO.builder()
                .id(school.getId())
                .name(school.getName())
                .description(school.getDescription())
                .imageUrl(school.getImageUrl())
                .teachingLanguage(school.getTeachingLanguage())
                .latitude(school.getLatitude())
                .longitude(school.getLongitude())
                .type(school.getType())
                .category(school.getCategory())
                .createdBy(school.getCreatedBy().getUsername()) // Récupère le nom du créateur
                .build();
    }
}
