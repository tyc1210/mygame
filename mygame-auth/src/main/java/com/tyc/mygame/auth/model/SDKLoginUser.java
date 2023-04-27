package com.tyc.mygame.auth.model;

import com.tyc.mygame.auth.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * SKD登录服务
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-12 11:46:32
 */
@Data
@AllArgsConstructor
public class SDKLoginUser implements UserDetails {
    private SysUser sysUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return sysUser.getAuthorities();
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     */
    @Override
    public boolean isEnabled() {
        return sysUser.getStatus().equals(1);
    }
}
