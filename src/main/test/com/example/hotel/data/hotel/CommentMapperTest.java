package com.example.hotel.data.hotel;

import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
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
public class CommentMapperTest {
    @Autowired
    CommentMapper commentMapper;

    @Test
    public void addCommentTest(){
        CommentVO commentVO=new CommentVO();
        commentVO.setCreateTime("2020-06-22");
        commentVO.setHotelId(1);
        commentVO.setOrderId(1);
        commentVO.setUserName("王雷");
        commentVO.setGrade(2);
        commentVO.setContent("就这？");
        commentMapper.addComment(commentVO);
        assert commentVO.getId()!=null;
    }

    @Test
    public void checkCommentTest(){
        CommentVO commentVO=new CommentVO();
        commentVO.setCreateTime("2020-06-22 00:00:00");
        commentVO.setHotelId(1);
        commentVO.setOrderId(1);
        commentVO.setUserName("王雷");
        commentVO.setGrade(2);
        commentVO.setContent("就这？");
        commentMapper.addComment(commentVO);
        List<Comment> comments = commentMapper.checkComment(1);
        Comment comment = comments.get(comments.size()-1);
        assert comment.getContent().equals(commentVO.getContent());
        assert comment.getGrade().equals(commentVO.getGrade());
        assert comment.getUserName().equals(commentVO.getUserName());
        assert comment.getCreateTime().equals(commentVO.getCreateTime());
        assert comment.getOrderId().equals(commentVO.getOrderId());
        assert comment.getHotelId().equals(commentVO.getHotelId());
    }
}
