package com.expense.tracker.play.trans.controller;

import com.expense.tracker.play.common.exception.ResourceNotFoundException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.trans.payload.CategoryReq.CreateDto;
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

//    @GetMapping
//    public String getAllCategories() {
//        HttpSession httpSession = request.getSession(false);
//        String email = (String) httpSession.getAttribute("email");
////        String username = (String) httpSession.getAttribute("username");
////        return username + StringUtils.SPACE + email;
//        categoryService.findAllCategories(email);
//    }

    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable("categoryId") Long categoryId, HttpSession session) throws UserNotFoundException, ResourceNotFoundException {
        String email = (String) session.getAttribute("email");
        log.info("@email===========>{}", email);
        return new ResponseEntity<>(categoryService.getCategoryById(email, categoryId), HttpStatus.OK);
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
        log.info("@email===========>{}", email);
        return new ResponseEntity<>(categoryService.addCategory(email, createDto), HttpStatus.CREATED);
    }
}
