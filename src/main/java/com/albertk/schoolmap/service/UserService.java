package com.albertk.schoolmap.service;
import com.albertk.schoolmap.dto.UserDTO;
import com.albertk.schoolmap.exception.ResourceNotFoundException;
import com.albertk.schoolmap.mapper.UserMapper;
import com.albertk.schoolmap.model.User;
import com.albertk.schoolmap.repository.UserRepository;
import com.albertk.schoolmap.response.ApiResponse;
import com.albertk.schoolmap.security.SpringSecurityUser;
import com.albertk.schoolmap.types.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return this.getUserByEmail( ((UserDetails) authentication.getPrincipal()).getUsername());
        }
        return null;
    }

    // loadUserByUserName pour spring boot
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new SpringSecurityUser(user); // Retourne un objet compatible avec Spring Security
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found with email: " + email));
    }

    public ApiResponse<UserDTO> getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return ApiResponse.success(UserMapper.toDTO(user), "User found");
    }

    public ApiResponse<?> getAllUsers(Integer page, Integer size) {
        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<User> userPage = userRepository.findAll(pageable);
            Set<UserDTO> userDTOs = userPage.getContent().stream().map(UserMapper::toDTO).collect(Collectors.toSet());
            return ApiResponse.successWithPagination(
                    userDTOs,
                    "Users retrieved successfully",
                    page,
                    userPage.getTotalPages(),
                    size,
                    userPage.getTotalElements()
            );
        } else {
            Set<UserDTO> userDTOs = userRepository.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toSet());
            return ApiResponse.success(userDTOs, "All users retrieved");
        }
    }

    public ApiResponse<UserDTO> createUser(User user) {
        if(user.getRole() == null){
            user.setRole(UserRole.VIEWER); // Par défaut, un nouvel utilisateur est "VIEWER"
        }
        User savedUser = userRepository.save(user);
        return ApiResponse.success(UserMapper.toDTO(savedUser), "User created successfully");
    }

    public ApiResponse<UserDTO> updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());

        User savedUser = userRepository.save(user);
        return ApiResponse.success(UserMapper.toDTO(savedUser), "User updated successfully");
    }

    public ApiResponse<?> deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
        return ApiResponse.success(null, "User deleted successfully");
    }
}


