package com.tyc.mygame.ucenter.service;

import com.tyc.mygame.ucenter.entity.Account;

public interface IAccountService {
    Account getByChannelAndUid(String channel, String sdkUid);

    void save(Account account);
}
