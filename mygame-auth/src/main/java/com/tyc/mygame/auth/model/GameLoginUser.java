package com.tyc.mygame.auth.model;

import com.tyc.mygame.auth.entity.Account;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 进入游戏前的登录服务
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-14 13:48:07
 */
@Data
public class GameLoginUser implements UserDetails {
    private Account account;

    public GameLoginUser(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return account.getAuthorities();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return account.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return account.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return account.isEnabled();
    }
}
