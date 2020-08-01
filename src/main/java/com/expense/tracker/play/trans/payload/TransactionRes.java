package com.expense.tracker.play.trans.payload;

import com.expense.tracker.play.trans.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TransactionRes {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class FindDto {
        private Long id;
        private Double amount;
        private String note;

        public FindDto(Transaction entity) {
            this.id = entity.getId();
            this.amount = entity.getAmount();
            this.note = entity.getNote();
        }

        public static FindDto toDto(Transaction entity) {
            return FindDto.builder()
                    .id(entity.getId())
                    .amount(entity.getAmount())
                    .note(entity.getNote())
                    .build();
        }

    }
}
