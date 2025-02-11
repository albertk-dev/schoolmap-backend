package com.albertk.schoolmap.model;

import com.albertk.schoolmap.types.SchoolCategory;
import com.albertk.schoolmap.types.SchoolType;
import com.albertk.schoolmap.types.TeachingLanguage;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Table(name = "schools")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeachingLanguage teachingLanguage;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchoolType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchoolCategory category;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    private LocalDateTime createdAt;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point geom;

    @PrePersist
    @PreUpdate
    private void generateGeom() {
        if (latitude != null && longitude != null) {
            GeometryFactory geometryFactory = new GeometryFactory();
            this.geom = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        }

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

}
