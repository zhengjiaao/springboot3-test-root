package com.zja.api.openapi.security.config;

import com.zja.api.openapi.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Author: zhengja
 * @Date: 2025-07-31 11:11
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // 允许访问 swagger-ui 和 API 文档
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**"  // 必须包含 /**，不只是 /swagger-ui/index.html
                        ).permitAll()
                        .requestMatchers("/api/auth/**").permitAll() // 登录接口无需认证
                        // 其他公开接口
                        .requestMatchers("/api/v1/public/**").permitAll()
                        // 受保护的接口需要认证
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**"
                ))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 无状态会话
                // 默认用户名密码为 admin/admin123
                // 启用基本认证
                // .httpBasic(withDefaults()) // 启用基本认证，无状态认证方式，
                // 或者使用表单登录
                // .formLogin(withDefaults()) // 启用表单登录，基于 Session 的有状态认证，适合 Web 应用程序
                *//*.formLogin(form -> form
                        .loginPage("/login")              // 自定义登录页面
                        .loginProcessingUrl("/login")     // 登录处理URL
                        .defaultSuccessUrl("/home")       // 登录成功后跳转
                        .failureUrl("/login?error=true")  // 登录失败跳转
                        .permitAll()                      // 允许所有人访问登录相关URL
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )*//*

        ;

        return http.build();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/api/auth/**"  // 登录接口无需认证
                        ).permitAll()
                        .requestMatchers("/api/v1/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()) // 禁用 CSRF 保护
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 无状态会话
                .httpBasic(withDefaults())
                .formLogin(withDefaults());

        // 添加 JWT 过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}