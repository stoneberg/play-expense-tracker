package com.expense.tracker.play.trans.controller;

import com.expense.tracker.play.common.annotations.LoginUser;
import com.expense.tracker.play.common.exception.ResourceNotFoundException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.trans.payload.CategoryReq.CreateDto;
import com.expense.tracker.play.trans.payload.CategoryReq.UpdateDto;
import com.expense.tracker.play.trans.service.CategoryService;
import com.expense.tracker.play.user.domain.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 목록 조회
     *
     * @param user
     * @return
     * @throws UserNotFoundException
     */
    @GetMapping
    public ResponseEntity<?> getAllCategories(@LoginUser UserSession user) throws UserNotFoundException {
        return new ResponseEntity<>(categoryService.findAllCategories(user.getEmail()), HttpStatus.OK);
    }

    /**
     * 카테고리 단건 조회
     * 
     * @param categoryId
     * @param user
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable("categoryId") Long categoryId, @LoginUser UserSession user) throws ResourceNotFoundException {
        return new ResponseEntity<>(categoryService.getCategory(user.getEmail(), categoryId), HttpStatus.OK);
    }

    /**
     * 카테고리 목록 조회 by QueryDSL
     *
     * @param user
     * @return
     * @throws UserNotFoundException
     */
//    @GetMapping
//    public ResponseEntity<?> getAllCategories(@LoginUser UserSession user) {
//        return new ResponseEntity<>(categoryService.findAllCategoriesByDsl(user.getEmail()), HttpStatus.OK);
//    }

    /**
     * 카테고리 단건 조회 by QueryDSL
     *
     * @param categoryId
     * @param user
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
//    @GetMapping(path = "/{categoryId}")
//    public ResponseEntity<?> getCategoryByDsl(@PathVariable("categoryId") Long categoryId, @LoginUser UserSession user) {
//        return new ResponseEntity<>(categoryService.getCategoryByDsl(user.getEmail(), categoryId), HttpStatus.OK);
//    }

    /**
     * 카테고리 생성
     * 
     * @param createDto
     * @param user
     * @return
     * @throws UserNotFoundException
     */
    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CreateDto createDto, @LoginUser UserSession user) throws UserNotFoundException {
        log.info("1======================>{}", user);
        log.info("2======================>{}", user.getEmail());
        return new ResponseEntity<>(categoryService.addCategory(user.getEmail(), createDto), HttpStatus.CREATED);
    }

    /**
     * 카테고리 수정
     * 
     * @param categoryId
     * @param updateDto
     * @param user
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @PutMapping(path = "/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody UpdateDto updateDto, @LoginUser UserSession user) throws ResourceNotFoundException {
        return new ResponseEntity<>(categoryService.updateCategory(user.getEmail(), categoryId, updateDto), HttpStatus.OK);
    }

    /**
     * 카테고리 삭제
     *
     * @param categoryId
     * @param user
     * @return
     */
    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId, @LoginUser UserSession user) throws ResourceNotFoundException {
        categoryService.deleteCategory(user.getEmail(), categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
