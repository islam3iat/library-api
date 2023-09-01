package com.example.LibraryManagementSystem.member;

import jakarta.validation.constraints.*;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Member}
 */
@Value
public class MemberDto implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    final String firstName;
    @NotNull
    @NotEmpty
    @NotBlank
    final String lastName;
    @NotNull
    @Email
    @NotEmpty
    @NotBlank
    final String email;
    @NotBlank
    final String phone;
}