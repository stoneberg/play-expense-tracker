package com.expense.tracker.play.trans.payload;

import com.expense.tracker.play.trans.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CategoryRes {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class FindDto {
        private Long id;
        private String email;
        private String title;
        private String description;
        private Double totalExpense;

        public FindDto(Category entity) {
            this.id = entity.getId();
            this.email = entity.getUser().getEmail();
            this.title = entity.getTitle();
            this.description = entity.getDescription();
            this.totalExpense = entity.getTotalExpense();
        }

        public static FindDto toDto(Category entity) {
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
