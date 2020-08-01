package com.expense.tracker.play.user.service;

import com.expense.tracker.play.common.exception.AuthenticationFailedException;
import com.expense.tracker.play.common.exception.EmailDuplicationException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.common.utils.JwtTokenUtil;
import com.expense.tracker.play.user.domain.User;
import com.expense.tracker.play.user.payload.UserReq.CreateUserDto;
import com.expense.tracker.play.user.payload.UserReq.LoginUserDto;
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

    @Transactional
    public Long registerUser(CreateUserDto reqDto) throws EmailDuplicationException {
        String email = reqDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new EmailDuplicationException(String.format("Email [%s] is already in use", email));
        }
        String hashedPassword = BCrypt.hashpw(reqDto.getPassword(), BCrypt.gensalt(10));
        reqDto.setPassword(hashedPassword);
        return userRepository.save(reqDto.toEntity()).getId();
    }

    /**
     * 사용자 인증 및 토큰 생성
     *
     * @param reqDto
     * @return
     * @throws UserNotFoundException
     * @throws AuthenticationFailedException
     */
    public Map<String, Object> loginUser(LoginUserDto reqDto) throws UserNotFoundException, AuthenticationFailedException {
        // check user exists
        User user = userRepository.findByEmail(reqDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format("User [%s] not exists", reqDto.getEmail())));
        // check password correct
        boolean passwordMatch = BCrypt.checkpw(reqDto.getPassword(), user.getPassword());
        if (!passwordMatch) {
            throw new AuthenticationFailedException();
        }
        // create jwt token and send to user
        return jwtTokenUtil.generateJwtToken(user);
    }
}
