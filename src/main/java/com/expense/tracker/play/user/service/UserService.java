package com.expense.tracker.play.user.service;

import com.expense.tracker.play.common.exception.AuthenticationFailedException;
import com.expense.tracker.play.common.exception.EmailDuplicationException;
import com.expense.tracker.play.common.exception.UserNotFoundException;
import com.expense.tracker.play.common.utils.JwtUtil;
import com.expense.tracker.play.user.domain.ERole;
import com.expense.tracker.play.user.domain.Role;
import com.expense.tracker.play.user.domain.User;
import com.expense.tracker.play.user.payload.UserReq.CreateDto;
import com.expense.tracker.play.user.repository.RoleRepository;
import com.expense.tracker.play.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 사용자 등록
     * 
     * @param createDto
     * @return
     * @throws EmailDuplicationException
     */
    @Transactional
    public Long registerUser(CreateDto createDto) throws EmailDuplicationException {
        if (userRepository.existsByEmail(createDto.getEmail())) {
            throw new EmailDuplicationException(String.format("Email [%s] is already in use", createDto.getEmail()));
        }
        String hashedPassword = passwordEncoder.encode(createDto.getPassword());
        createDto.setPassword(hashedPassword);
        User user = createDto.toEntity();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER);
        Role guestRole = roleRepository.findByName(ERole.ROLE_GUEST);
        //user.addUserRoles(Arrays.asList(userRole, guestRole));
        user.addUserRoles(Collections.singletonList(userRole));

        return userRepository.save(user).getId();
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
//        return jwtUtil.generateJwtToken(user.getUsername);
//    }
}
