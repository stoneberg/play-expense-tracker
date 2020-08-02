package com.expense.tracker.play.trans.controller;

import com.expense.tracker.play.common.exception.ResourceNotFoundException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.config.security.service.CustomUserDetails;
import com.expense.tracker.play.trans.payload.CategoryReq.CreateDto;
import com.expense.tracker.play.trans.payload.CategoryReq.UpdateDto;
import com.expense.tracker.play.trans.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 목록 조회
     *
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
    @GetMapping
    public ResponseEntity<?> getAllCategories(@AuthenticationPrincipal CustomUserDetails principal) throws UserNotFoundException {
        return new ResponseEntity<>(categoryService.findAllCategories(principal.getEmail()), HttpStatus.OK);
    }

    /**
     * 카테고리 단건 조회
     * 
     * @param categoryId
     * @param principal
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable("categoryId") Long categoryId, @AuthenticationPrincipal CustomUserDetails principal) throws ResourceNotFoundException {
        return new ResponseEntity<>(categoryService.getCategory(principal.getEmail(), categoryId), HttpStatus.OK);
    }

    /**
     * 카테고리 목록 조회 by QueryDSL
     *
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
//    @GetMapping
//    public ResponseEntity<?> getAllCategories(@AuthenticationPrincipal CustomUserDetails principal) {
//        return new ResponseEntity<>(categoryService.findAllCategoriesByDsl(principal.getEmail()), HttpStatus.OK);
//    }

    /**
     * 카테고리 단건 조회 by QueryDSL
     *
     * @param categoryId
     * @param principal
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
//    @GetMapping(path = "/{categoryId}")
//    public ResponseEntity<?> getCategoryByDsl(@PathVariable("categoryId") Long categoryId, @AuthenticationPrincipal CustomUserDetails principal) {
//        return new ResponseEntity<>(categoryService.getCategoryByDsl(principal.getEmail(), categoryId), HttpStatus.OK);
//    }

    /**
     * 카테고리 생성
     * 
     * @param createDto
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CreateDto createDto, @AuthenticationPrincipal CustomUserDetails principal) throws UserNotFoundException {
        return new ResponseEntity<>(categoryService.addCategory(principal.getEmail(), createDto), HttpStatus.CREATED);
    }

    /**
     * 카테고리 수정
     * 
     * @param categoryId
     * @param updateDto
     * @param principal
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @PutMapping(path = "/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody UpdateDto updateDto, @AuthenticationPrincipal CustomUserDetails principal) throws ResourceNotFoundException {
        return new ResponseEntity<>(categoryService.updateCategory(principal.getEmail(), categoryId, updateDto), HttpStatus.OK);
    }

    /**
     * 카테고리 삭제
     *
     * @param categoryId
     * @param principal
     * @return
     */
    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId, @AuthenticationPrincipal CustomUserDetails principal) throws ResourceNotFoundException {
        categoryService.deleteCategory(principal.getEmail(), categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
