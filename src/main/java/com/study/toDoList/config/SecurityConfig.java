package com.study.toDoList.config;

import com.study.toDoList.config.jwt.JwtAuthenticationFilter;
import com.study.toDoList.config.jwt.JwtExceptionFilter;
import com.study.toDoList.config.jwt.JwtTokenProvider;
import com.study.toDoList.config.oauth.CustomOAuth2UserService;
import com.study.toDoList.config.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.study.toDoList.config.oauth.OAuth2AuthenticationFailureHandler;
import com.study.toDoList.config.oauth.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig { //WebSecurityConfigurerAdapter was deprecated
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final JwtExceptionFilter jwtExceptionFilter;
    //필터 거치면 안될 것들
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().antMatchers("/images/**", "/js/**", "/css/**","/api/**");
        };
    }

    //요청이 들어오면 repository로 가서 authorization request를 저장한다.
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin().disable()
                .httpBasic().disable()

                .authorizeRequests()
                .antMatchers ("/main/**","/login/**", "/oauth2/**","/medi/**").permitAll()
                .and()
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/login/oauth2/code/**")
                .and()
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter,JwtAuthenticationFilter.class);
        return http.build();
    }
}