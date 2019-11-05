package com.ningmeng.vueblog.vo;

import com.ningmeng.vueblog.entity.Comment;
import com.ningmeng.vueblog.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ToString
@Setter
@Getter
public class CommentVO {
    private Integer id;
    private String content;
    private Long createTime;
    private UserVO user;
    private Integer originalCommentId;
    private Integer floorNumber;
    private Integer articleId;
    private List<CommentVO> children;

    public CommentVO(){}

    public static CommentVO newInstance(Comment comment, List<Comment> comments) {
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comment, commentVO);
        commentVO.setUser(UserVO.newInstance(comment.getUser()));
        ArrayList<CommentVO> commentVOS = new ArrayList<>();
        comments.sort((o1, o2) -> {
            if (o1.getCreateTime() > o2.getCreateTime())
                return 1;
            else if (o1.getCreateTime() == o2.getCreateTime())
                return 0;
            else
                return -1;
        });
        for (Comment c : comments) {
            CommentVO vo = new CommentVO();
            BeanUtils.copyProperties(c, vo);
            vo.setUser(UserVO.newInstance(c.getUser()));
            commentVOS.add(vo);
        }
        commentVO.setChildren(commentVOS);
        return commentVO;
    }
}
