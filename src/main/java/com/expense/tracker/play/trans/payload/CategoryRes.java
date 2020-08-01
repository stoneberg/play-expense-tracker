package com.expense.tracker.play.trans.payload;

import com.expense.tracker.play.trans.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CategoryRes {

    @NoArgsConstructor
    @Data
    public static class FindDto {

        private Long id;

        private String email;

        private String title;

        private String description;

        private Double totalExpense;

        @Builder
        public FindDto(Long id, String email, String title, String description, Double totalExpense) {
            this.id = id;
            this.email = email;
            this.title = title;
            this.description = description;
            this.totalExpense = totalExpense;
        }

        public static CategoryRes.FindDto toDto(Category entity) {
            return FindDto.builder()
                    .id(entity.getId())
                    .email(entity.getUser().getEmail())
                    .title(entity.getTitle())
                    .description(entity.getDescription())
                    .totalExpense(entity.getTotalExpense())
                    .build();
        }

    }
}
