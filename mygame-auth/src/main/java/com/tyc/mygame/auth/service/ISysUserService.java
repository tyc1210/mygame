package com.tyc.mygame.auth.service;


import com.tyc.mygame.auth.entity.SysUser;

public interface ISysUserService {

    SysUser getByName(String name);
}
