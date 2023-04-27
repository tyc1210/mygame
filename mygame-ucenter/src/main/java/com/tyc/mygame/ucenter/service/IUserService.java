package com.tyc.mygame.ucenter.service;

import com.tyc.mygame.ucenter.entity.SysUser;
import com.tyc.mygame.ucenter.model.request.LoginUser;
import com.tyc.mygame.ucenter.model.request.RegisterUser;
import com.tyc.mygame.ucenter.model.response.LoginResponse;

public interface IUserService {
    void register(RegisterUser registerUser);

    SysUser getByName(String name);

    LoginResponse login(LoginUser user);
}
