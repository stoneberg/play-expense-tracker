package com.expense.tracker.play.trans.service;

import com.expense.tracker.play.common.exception.ResourceNotFoundException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.trans.domain.Category;
import com.expense.tracker.play.trans.payload.CategoryReq;
import com.expense.tracker.play.trans.payload.CategoryReq.UpdateDto;
import com.expense.tracker.play.trans.payload.CategoryRes.FindDto;
import com.expense.tracker.play.trans.repository.CategoryRepository;
import com.expense.tracker.play.user.domain.User;
import com.expense.tracker.play.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    /**
     * 카테고리 목록 조회
     * 
     * @param email
     * @return
     * @throws UserNotFoundException
     */
    public List<FindDto> findAllCategories(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));
        List<Category> categories = categoryRepository.findAllByUser(user);
        return categories.stream().map(FindDto::new).collect(Collectors.toList());
    }

    /**
     * 카테고리 단건 조회
     * 
     * @param email
     * @param categoryId
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    public FindDto getCategoryById(String email, Long categoryId) throws UserNotFoundException, ResourceNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));
        Category category = categoryRepository.findCategoryByIdAndUser(categoryId, user)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", categoryId)));
        return FindDto.toDto(category);
    }

    /**
     * 카테고리 생성
     * 
     * @param email
     * @param createDto
     * @return
     * @throws UserNotFoundException
     */
    @Transactional
    public Long addCategory(String email, CategoryReq.CreateDto createDto) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));
        Category category = Category.createCategory(user, createDto);
        return categoryRepository.save(category).getId();
    }

    /**
     * 카테고리 수정
     * 
     * @param email
     * @param categoryId
     * @param updateDto
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @Transactional
    public Long updateCategory(String email, Long categoryId, UpdateDto updateDto) throws UserNotFoundException, ResourceNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));

        Category category = categoryRepository.findCategoryByIdAndUser(categoryId, user)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", categoryId)));

        // update by dirty checking
        category.updateCategory(updateDto);
        return category.getId();
    }

//    public void deleteCategoryWithAllTransactions(Integer userId, Integer categoryId) {
//
//    }

//    public void deleteCategoryById() {
//
//    }

}
