package com.expense.tracker.play.user.service;

import com.expense.tracker.play.common.exception.AuthenticationFailedException;
import com.expense.tracker.play.common.exception.EmailDuplicationException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.common.utils.JwtTokenUtil;
import com.expense.tracker.play.user.domain.User;
import com.expense.tracker.play.user.payload.UserReq;
import com.expense.tracker.play.user.payload.UserReq.CreateDto;
import com.expense.tracker.play.user.payload.UserReq.LoginDto;
import com.expense.tracker.play.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 사용자 등록
     * 
     * @param createDto
     * @return
     * @throws EmailDuplicationException
     */
    @Transactional
    public Long registerUser(CreateDto createDto) throws EmailDuplicationException {
        String email = createDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new EmailDuplicationException(String.format("Email [%s] is already in use", email));
        }
        String hashedPassword = BCrypt.hashpw(createDto.getPassword(), BCrypt.gensalt(10));
        createDto.setPassword(hashedPassword);
        return userRepository.save(createDto.toEntity()).getId();
    }

    /**
     * 사용자 인증 및 토큰 생성
     *
     * @param loginDto
     * @return
     * @throws UserNotFoundException
     * @throws AuthenticationFailedException
     */
//    public Map<String, Object> loginUser(LoginDto loginDto) throws UserNotFoundException, AuthenticationFailedException {
//        // check user exists
//        User user = userRepository.findByEmail(loginDto.getEmail())
//                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", loginDto.getEmail())));
//        // check password correct
//        boolean passwordMatch = BCrypt.checkpw(loginDto.getPassword(), user.getPassword());
//        if (!passwordMatch) {
//            throw new AuthenticationFailedException();
//        }
//        // create jwt token and send to user
//        return jwtTokenUtil.generateJwtToken(user);
//    }
}
