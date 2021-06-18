package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAth2UserService customOAth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests() // 권한 관리
                    .antMatchers("/", "/css/**","/images/**","/js/**","/h2-console/**", "/profile").permitAll() // 모든 사용자 허용
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // role이 USER인 사람만 허용
                    .anyRequest().authenticated() //나머지 경로는인증된 사용자에게만 허용
                .and()
                    .logout()
                        .logoutSuccessUrl("/") //로그아웃 성공시 "/"로 이동
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAth2UserService);

    }
}
