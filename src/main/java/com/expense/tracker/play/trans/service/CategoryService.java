package com.expense.tracker.play.trans.service;

import com.expense.tracker.play.common.exception.ResourceNotFoundException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.trans.domain.Category;
import com.expense.tracker.play.trans.payload.CategoryReq;
import com.expense.tracker.play.trans.payload.CategoryRes;
import com.expense.tracker.play.trans.payload.CategoryRes.FindDto;
import com.expense.tracker.play.trans.repository.CategoryRepository;
import com.expense.tracker.play.user.domain.User;
import com.expense.tracker.play.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

//    public List<Category> findAllCategories(String email) throws UserNotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));
//        List<Category> categories = categoryRepository.findAllByUser(user);
//
//    }


    public FindDto getCategoryById(String email, Long categoryId) throws UserNotFoundException, ResourceNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));
        Category category = categoryRepository.findCategoryByIdAndUser(categoryId, user)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", categoryId)));
        return FindDto.toDto(category);
    }

    public Long addCategory(String email, CategoryReq.CreateDto createDto) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));
        Category category = Category.createCategory(user, createDto);
        return categoryRepository.save(category).getId();
    }

//    public updateCategory() {
//
//    }

//    public void deleteCategoryWithAllTransactions(Integer userId, Integer categoryId) {
//
//    }

//    public void deleteCategoryById() {
//
//    }

}
