package com.expense.tracker.play.trans.repository;

import com.expense.tracker.play.common.querydsl.Querydsl4RepositorySupport;
import com.expense.tracker.play.trans.domain.Category;
import com.expense.tracker.play.trans.payload.CategoryRes;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.expense.tracker.play.trans.domain.QCategory.category;
import static com.expense.tracker.play.trans.domain.QTransaction.transaction;
import static com.expense.tracker.play.user.domain.QUser.user;

@Repository
public class CategoryQuerydslRepository extends Querydsl4RepositorySupport {

    public CategoryQuerydslRepository() {
        super(Category.class);
    }

    /**
     * 카테고리 조회
     *
     * @param email
     * @param categoryId
     * @return
     */
    public CategoryRes.FindDto selectCategory(String email, Long categoryId) {
        return select(Projections.fields(CategoryRes.FindDto.class,
                category.id
                ,category.title
                ,category.description
                ,category.user.email.as("email")
                ,transaction.amount.sum().as("totalExpense")
        ))
                .from(category)
                .leftJoin(category.transactions, transaction)
                .leftJoin(category.user, user)
                .where(
                        this.eqCategoryId(categoryId),
                        this.eqEmail(email)
                )
                .groupBy(
                        category.id,
                        category.title,
                        category.description,
                        category.user.email
                )
                .distinct()
                .fetchOne();
    }

    /**
     * 카테고리 목록 조회
     *
     * @param email
     * @return
     */
    public List<CategoryRes.FindDto> selectCategories(String email) {
        return select(Projections.fields(CategoryRes.FindDto.class,
                category.id,
                category.title,
                category.description,
                category.user.email.as("email"),
                transaction.amount.sum().as("totalExpense")
        ))
                .from(transaction)
                .leftJoin(transaction.category, category)
                .leftJoin(category.user, user)
                .where(
                        this.eqEmail(email)
                )
                .groupBy(
                        category.id,
                        category.title,
                        category.description,
                        category.user.email
                )
                .distinct()
                .fetch();
    }

    /**
     * Where Clauses
     */

    // 카테고리 아이디
    private BooleanExpression eqCategoryId(Long categoryId) {
        return categoryId != null ? category.id.eq(categoryId) : null;
    }

    // 이메일
    private BooleanExpression eqEmail(String email) {
        return StringUtils.hasText(email) ? user.email.eq(email) : null;
    }

}
