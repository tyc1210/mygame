package com.tyc.mygame.ucenter.service.impl;

import com.tyc.mygame.ucenter.service.IAccountService;
import com.tyc.mygame.ucenter.dao.AccountMapper;
import com.tyc.mygame.ucenter.entity.Account;
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
 * @date 2023-04-14 14:02:39
 */
@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account getByChannelAndUid(String channel, String sdkUid) {
        Map<String,Object> map = new HashMap<>();
        map.put("channel",channel);
        map.put("sdkUid",sdkUid);
        List<Account> accounts = accountMapper.selectByMap(map);
        return accounts.isEmpty()?null:accounts.get(0);
    }

    @Override
    public void save(Account account) {
        accountMapper.insert(account);
    }
}
