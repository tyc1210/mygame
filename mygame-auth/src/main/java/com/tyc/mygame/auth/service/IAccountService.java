package com.tyc.mygame.auth.service;

import com.tyc.mygame.auth.entity.Account;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-14 14:59:01
 */
public interface IAccountService {
    Account getById(Long id);

    Account getByChannelAndUid(String channel, String sdkUid);
}
