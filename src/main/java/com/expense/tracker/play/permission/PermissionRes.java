package com.expense.tracker.play.permission;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PermissionRes {

    @NoArgsConstructor
    @Data
    public static class AdminDto {
        private Long id;
        private String name;
    }

    @NoArgsConstructor
    @Data
    public static class ClientDto {
        private Long id;
        private String name;
    }

}
