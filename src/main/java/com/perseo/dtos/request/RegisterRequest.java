package com.perseo.dtos.request;

import com.perseo.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String username;
    String email;
    String password;
    UserRole role;

    public UserRole getRole() {
        return UserRole.USER;
    }
}
