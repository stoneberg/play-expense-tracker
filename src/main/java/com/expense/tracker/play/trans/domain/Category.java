package com.expense.tracker.play.trans.domain;

import com.expense.tracker.play.common.entity.BaseEntity;
import com.expense.tracker.play.trans.payload.CategoryReq;
import com.expense.tracker.play.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "et_categories")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    @Column(length = 20, nullable = false)
    private String title;

    @Column(length = 50, nullable = false)
    private String description;

    @Column(name="total_expense", columnDefinition="Decimal(10, 2) default '0.00'")
    private Double totalExpense = 0.00;


    @Builder
    public Category(User user, String title, String description) {
        this.user = user;
        this.title = title;
        this.description = description;
    }

    // utility method
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        if (transaction.getCategory() != this) {
            transaction.setCategory(this);
        }
    }

    // create category
    public static Category createCategory(User user, CategoryReq.CreateDto dto) {
        return Category.builder()
                .user(user)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }

}
