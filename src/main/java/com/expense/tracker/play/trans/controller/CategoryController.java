package com.expense.tracker.play.trans.controller;

import com.expense.tracker.play.common.exception.ResourceNotFoundException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.trans.payload.CategoryReq.CreateDto;
import com.expense.tracker.play.trans.payload.CategoryReq.UpdateDto;
import com.expense.tracker.play.trans.service.CategoryService;
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
     * @param session
     * @return
     * @throws UserNotFoundException
     */
    @GetMapping
    public ResponseEntity<?> getAllCategories(HttpSession session) throws UserNotFoundException {
        String email = (String) session.getAttribute("email");
        return new ResponseEntity<>(categoryService.findAllCategories(email), HttpStatus.OK);
    }

    /**
     * 카테고리 단건 조회
     * 
     * @param categoryId
     * @param session
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable("categoryId") Long categoryId, HttpSession session) throws UserNotFoundException, ResourceNotFoundException {
        String email = (String) session.getAttribute("email");
        return new ResponseEntity<>(categoryService.getCategory(email, categoryId), HttpStatus.OK);
    }

    /**
     * 카테고리 생성
     * 
     * @param createDto
     * @return
     * @throws UserNotFoundException
     */
    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CreateDto createDto, HttpSession session) throws UserNotFoundException {
        String email = (String) session.getAttribute("email");
        return new ResponseEntity<>(categoryService.addCategory(email, createDto), HttpStatus.CREATED);
    }

    /**
     * 카테고리 수정
     * 
     * @param categoryId
     * @param updateDto
     * @param session
     * @return
     * @throws UserNotFoundException
     * @throws ResourceNotFoundException
     */
    @PutMapping(path = "/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody UpdateDto updateDto, HttpSession session) throws UserNotFoundException, ResourceNotFoundException {
        String email = (String) session.getAttribute("email");
        return new ResponseEntity<>(categoryService.updateCategory(email, categoryId, updateDto), HttpStatus.OK);
    }

    /**
     * 카테고리 삭제
     *
     * @param categoryId
     * @param session
     * @return
     */
    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId, HttpSession session) throws ResourceNotFoundException, UserNotFoundException {
        String email = (String) session.getAttribute("email");
        categoryService.deleteCategory(email, categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
