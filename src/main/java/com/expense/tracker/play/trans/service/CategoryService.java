package com.expense.tracker.play.trans.service;

import com.expense.tracker.play.common.exception.ResourceNotFoundException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.trans.domain.Category;
import com.expense.tracker.play.trans.mapper.CategoryMapper;
import com.expense.tracker.play.trans.payload.CategoryReq;
import com.expense.tracker.play.trans.payload.CategoryReq.UpdateDto;
import com.expense.tracker.play.trans.payload.CategoryRes.FindDto;
import com.expense.tracker.play.trans.repository.CategoryQuerydslRepository;
import com.expense.tracker.play.trans.repository.CategoryRepository;
import com.expense.tracker.play.user.domain.User;
import com.expense.tracker.play.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryQuerydslRepository categoryQuerydslRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;

    /**
     * 카테고리 목록 조회
     *
     * @param email
     * @return
     * @throws UserNotFoundException
     */
    public List<FindDto> findAllCategories(String email) throws UserNotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", email)));
        List<Category> categories = categoryRepository.findAllByUserEmail(email);
        return categoryMapper.toFindDtos(categories);
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
    public FindDto getCategory(String email, Long categoryId) throws ResourceNotFoundException {
        Category category = categoryRepository.findByIdAndUserEmail(categoryId, email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", categoryId)));
        return categoryMapper.toFindDto(category);
    }

    /**
     * 카테고리 목록 조회 By QueryDSL
     *
     * @param email
     * @return
     */
    public List<FindDto> findAllCategoriesByDsl(String email) {
        return categoryQuerydslRepository.selectCategories(email);
    }

    /**
     * 카테고리 단건 조회 By QueryDSL
     *
     * @param email
     * @param categoryId
     * @return
     */
    public FindDto getCategoryByDsl(String email, Long categoryId) {
        return categoryQuerydslRepository.selectCategory(email, categoryId);
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
    public Long updateCategory(String email, Long categoryId, UpdateDto updateDto) throws ResourceNotFoundException {
        Category category = categoryRepository.findByIdAndUserEmail(categoryId, email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", categoryId)));

        // update by dirty checking
        category.updateCategory(updateDto);
        return category.getId();
    }


    /**
     * 카테고리 삭제
     *
     * @param email
     * @param categoryId
     * @return
     */
    @Transactional
    public void deleteCategory(String email, Long categoryId) throws ResourceNotFoundException {
        Category category = categoryRepository.findByIdAndUserEmail(categoryId, email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category [%s] not exists", categoryId)));

        categoryRepository.delete(category);
    }
}