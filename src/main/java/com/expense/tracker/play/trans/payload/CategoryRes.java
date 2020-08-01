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
        private String title;
        private String description;
        private String email;
        private Double totalExpense;

        public FindDto(Category entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.description = entity.getDescription();
            this.email = entity.getUser().getEmail();
            this.totalExpense = entity.getTotalExpense();
        }

        public static FindDto toDto(Category entity) {
            return FindDto.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .description(entity.getDescription())
                    .email(entity.getUser().getEmail())
                    .totalExpense(entity.getTotalExpense())
                    .build();
        }

    }
}
