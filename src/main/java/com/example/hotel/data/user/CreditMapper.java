package com.example.hotel.data.user;

import com.example.hotel.vo.CreditVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CreditMapper {
    /**
     * 更新用户信用值
     * @param creditVO
     * @return
     * */
    int insertCredit(CreditVO creditVO);

    List<CreditVO> checkAllCredit(int id);

}
