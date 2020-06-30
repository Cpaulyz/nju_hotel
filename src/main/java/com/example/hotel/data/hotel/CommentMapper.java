package com.example.hotel.data.hotel;

import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    int addComment(CommentVO commentVO);

    List<Comment> checkComment(@Param("hotelId") int hotelId);

}
