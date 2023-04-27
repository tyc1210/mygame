package com.tyc.mygame.ucenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user")
public class SysUser {
    // 使用雪花算法生成ID
    @TableId(value = "userId",type = IdType.ASSIGN_ID)
    private Long userId;

    private String userName;

    private String password;

    public SysUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}