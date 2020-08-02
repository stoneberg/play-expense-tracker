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
        @NotBlank(message = "Username is mandatory field. please provide username")
        private String username;

        @Size(min = 10, max = 30)
        @Email(message = "Email is mandatory field. please provide a valid email")
        private String email;

        @NotBlank(message = "Password is mandatory field. please provide password")
        private String password;

        public User toEntity() {
            return User.builder()
                    .username(this.username)
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
