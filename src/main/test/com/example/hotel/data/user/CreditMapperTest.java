package com.example.hotel.data.user;

import com.example.hotel.enums.ActionType;
import com.example.hotel.enums.SignType;
import com.example.hotel.vo.CreditVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CreditMapperTest {
    @Autowired
    CreditMapper creditMapper;

    @Test
    public void checkAllCredit(){
        List<CreditVO> list = creditMapper.checkAllCredit(4);
        CreditVO init = list.get(0);
        assert init.getResult()==100;
        assert init.getAction()==ActionType.INIT;

    }

    @Test
    public void insertCreditTest(){
        CreditVO creditVO = new CreditVO();
        creditVO.setUserId(0);
        creditVO.setAction(ActionType.EXECUTE);
        creditVO.setChangeNum(50.0);
        creditVO.setResult(150.0);
        creditVO.setSign(SignType.ADD);
        creditVO.setTime("2020-06-19 00:00:00");
        creditMapper.insertCredit(creditVO);
        CreditVO creditVO1 = creditMapper.checkAllCredit(0).get(0);
        assert creditVO.getAction()==creditVO1.getAction();
        assert creditVO.getChangeNum().equals(creditVO1.getChangeNum());
        assert creditVO.getResult().equals(creditVO1.getResult());
        assert creditVO.getSign().equals(creditVO1.getSign());
        assert creditVO.getTime().equals(creditVO1.getTime());
    }
}
