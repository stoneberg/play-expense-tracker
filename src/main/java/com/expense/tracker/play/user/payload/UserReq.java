package com.expense.tracker.play.user.payload;

import com.expense.tracker.play.user.domain.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserReq {

    @Data
    public static class CreateDto {

        @Size(min = 2, max = 20)
        @NotBlank(message = "FirstName is mandatory field. please provide firstName")
        private String firstName;

        @Size(min = 2, max = 20)
        @NotBlank(message = "LastName is mandatory field. please provide lastName")
        private String lastName;

        @Size(min = 10, max = 30)
        @Email(message = "Email is mandatory field. please provide a valid email")
        private String email;

        @NotBlank(message = "Password is mandatory field. please provide password")
        private String password;

        public User toEntity() {
            return User.builder()
                    .firstName(this.firstName)
                    .lastName(this.lastName)
                    .email(this.email)
                    .password(this.password)
                    .build();
        }

    }

    @Data
    public static class LoginDto {
        @Size(min = 10, max = 30)
        @Email(message = "Email is mandatory field. please provide valid email")
        private String email;

        @NotBlank(message = "Password is mandatory field. please provide password")
        private String password;

    }

}
