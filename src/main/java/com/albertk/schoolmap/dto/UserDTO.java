package com.albertk.schoolmap.dto;


import com.albertk.schoolmap.types.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private UserRole role;
}

