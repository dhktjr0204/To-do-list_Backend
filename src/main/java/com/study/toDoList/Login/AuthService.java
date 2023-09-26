package com.study.toDoList.Login;

import com.study.toDoList.config.jwt.JwtHeaderUtil;
import com.study.toDoList.config.jwt.JwtTokenProvider;
import com.study.toDoList.domain.User;
import com.study.toDoList.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    public User getMemberInfo(HttpServletRequest request){
        String token= JwtHeaderUtil.getAccessToken(request);
        if (token==null){
            return null;
        }
        String tokenInfo=jwtTokenProvider.parseClaims(token).getSubject();
        User user=userRepository.findByEmail(tokenInfo)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일이 없습니다:"+tokenInfo));
        return user;
    }
}
