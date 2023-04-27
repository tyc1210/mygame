package com.tyc.mygame.ucenter.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyc.mygame.ucenter.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-18 15:55:01
 */
@Mapper
public interface UserMapper extends BaseMapper<SysUser> {
}
