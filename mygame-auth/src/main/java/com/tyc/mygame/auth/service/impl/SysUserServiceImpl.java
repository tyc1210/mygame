package com.tyc.mygame.auth.service.impl;


import com.tyc.mygame.auth.dao.SysUserMapper;
import com.tyc.mygame.auth.entity.SysUser;
import com.tyc.mygame.auth.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-11 16:44:18
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getByName(String name) {
        Map<String,Object> map = new HashMap<>();
        map.put("userName",name);
        List<SysUser> sysUsers = sysUserMapper.selectByMap(map);
        return sysUsers.isEmpty()?null:sysUsers.get(0);
    }
}
