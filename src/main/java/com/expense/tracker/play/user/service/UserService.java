package com.expense.tracker.play.user.service;

import com.expense.tracker.play.common.exception.AuthenticationFailedException;
import com.expense.tracker.play.common.exception.EmailDuplicationException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.common.exception.UsernameNotFoundException;
import com.expense.tracker.play.common.utils.JwtTokenUtil;
import com.expense.tracker.play.user.domain.User;
import com.expense.tracker.play.user.payload.UserReq;
import com.expense.tracker.play.user.payload.UserRes.LoginUserDto;
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
    public Long registerUser(UserReq.CreateUserDto reqDto) throws EmailDuplicationException {
        String email = reqDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new EmailDuplicationException(String.format("Email [%s] is already in use", email));
        }
        String hashedPassword = BCrypt.hashpw(reqDto.getPassword(), BCrypt.gensalt(10));
        reqDto.setPassword(hashedPassword);
        return userRepository.save(reqDto.toEntity()).getId();
    }

    public Map<String, Object> loginUser(UserReq.LoginUserDto reqDto) throws UsernameNotFoundException, AuthenticationFailedException {
        // check user exists
        User user = userRepository.findByEmail(reqDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User [%s] not exists", reqDto.getEmail())));
        // check password correct
        boolean passwordMatch = BCrypt.checkpw(reqDto.getPassword(), user.getPassword());
        if (!passwordMatch) {
            throw new AuthenticationFailedException();
        }
        return jwtTokenUtil.generateJwtToken(user);
    }
}
