package com.tyc.mygame.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-14 13:51:43
 */
@Data
public class Account implements UserDetails {
    @TableId(type = IdType.ASSIGN_ID)
    private Long accountId;
    private String channel;
    private String putOnMark;
    private String sdkUid;
    private Integer status;
    private LocalDateTime regTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
    }

    @Override
    public String getPassword() {
        // 12345经过MD5和Bc..加密
        return "$2a$10$Iqm9gN7elnG.3hIlyry1Qel3aKgh1DhMKZu3OPDQDuQfAnjK8jgfm";
    }

    @Override
    public String getUsername() {
        return sdkUid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == 1;
    }
}
