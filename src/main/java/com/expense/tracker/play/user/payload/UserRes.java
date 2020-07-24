package com.expense.tracker.play.user.payload;

import com.expense.tracker.play.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRes {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginUserDto {
        private String firstName;
        private String lastName;
        private String email;

        public LoginUserDto(User user) {
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.email = user.getEmail();
        }
    }
}
