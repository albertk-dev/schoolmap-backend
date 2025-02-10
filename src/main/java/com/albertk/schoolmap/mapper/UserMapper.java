package com.albertk.schoolmap.mapper;


import com.albertk.schoolmap.dto.UserDTO;
import com.albertk.schoolmap.model.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
