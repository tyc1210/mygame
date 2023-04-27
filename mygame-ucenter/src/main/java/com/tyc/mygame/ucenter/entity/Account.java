package com.tyc.mygame.ucenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-14 13:51:43
 */
@Data
public class Account {
    @TableId(type = IdType.ASSIGN_ID)
    private Long accountId;
    private String channel;
    private String putOnMark;
    private String sdkUid;
    private String sdkUname;
    private Integer status;
    private LocalDateTime regTime;

}
