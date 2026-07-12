package com.amirsaleh.library.core.config;

import com.amirsaleh.library.domain.User;
import com.amirsaleh.library.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nationalCode) throws UsernameNotFoundException {
        User user = userRepository.findByNationalCode(nationalCode)
                .orElseThrow(() -> new UsernameNotFoundException("کاربری با این کد ملی یافت نشد"));
        return user;
    }
}