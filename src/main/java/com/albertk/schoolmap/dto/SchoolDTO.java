package com.albertk.schoolmap.dto;


import com.albertk.schoolmap.types.SchoolCategory;
import com.albertk.schoolmap.types.SchoolType;
import com.albertk.schoolmap.types.TeachingLanguage;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private TeachingLanguage teachingLanguage;
    private Double latitude;
    private Double longitude;
    private SchoolType type;
    private SchoolCategory category;
    private String createdBy; // On retourne juste le username du créateur
}
