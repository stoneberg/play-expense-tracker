package com.expense.tracker.play.user.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserSession implements Serializable {
    private String email;

    public UserSession(String email) {
        this.email = email;
    }
}
