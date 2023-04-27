package com.tyc.mygame.ucenter;

import com.tyc.mygame.ucenter.dao.UserMapper;
import com.tyc.mygame.ucenter.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-18 15:51:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestApplication {
    @Autowired
    private UserMapper testUserMapper;

    @Test
    public void testSelect(){
        List<SysUser> sysUsers = testUserMapper.selectList(null);
        sysUsers.forEach(System.out::println);
    }
}
