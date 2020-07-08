package com.Dao;


import com.Bean.Community;
import com.Bean.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {
    @Autowired
    private JdbcTemplate template;
    private PostRowMapper postRowMapper = new PostRowMapper();

    //增
    public boolean insertPost(String postTitle, String postContent, int postOwnerId, int postComId) {
        try {
            template.update("insert into post(postTitle," +
                    "postContent," +
                    "postOwnerId," +
                    "postComId) values (?,?,?,?)", postTitle, postContent, postOwnerId, postComId);
            template.update("update community set communitySize = communitySize+1 where communityId=?;", postComId);
            return true;

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //删
    public boolean deletePost(int postId, int postComId) {
        try {
            template.update("delete from post where postId=?", postId);
            template.update("update community set communitySize=communitySize-1 where communityId=?", postComId);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    //查
    public Post selectPostById(int postId) {
        try {
            List<Post> posts = template.query("select * from post,user,community where postOwnerId=userId and postComId=communityId and postId=? ", postRowMapper, postId);
            return posts.get(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //多查询
    //param：用于搜索，表示搜索哪个字段，默认搜索postTitle
    //ownerId：用于搜索，表示搜索某位用户或自己发的帖子，传入的为目标用户的id，传入0表示查询自己的帖子，默认在所有的帖子里面查询。
    //qw：用于搜索，搜索param的字段中包括value的结果。
    //若param和value都为“all”，表示不定向搜索。默认为这种情况。
    //order_by：表示根据哪个字段排序。默认根据 postHeat排序。
    //order：用于排序，为0表示正序，为1表示倒序。默认为 正序
    //pageSize：表示分页页面大小。 默认为 10
    //page：表示查询第几页的数据。 默认为 1
    //若pageSize和page都为0，则不分页，返回所有数据。
    public List<Post> selectPosts(String param,int ownerId,String value,String order_by,int order,int pageSize,int page){
        try {
            String sql = "select * from post,user,community where communityId=postComId and userId=postOwnerId ";
            if(ownerId!=-1)
                sql+=" and userId= "+ownerId;
            if (param != "all" || value != "all")
                sql += " and " + param + " like '%" + value + "%'";
            sql += " order by " + order_by;
            if (order == 1)
                sql += " desc";
            if (page != 0 || pageSize != 0)
                sql += " limit " + (page - 1) * pageSize + "," + pageSize;
            List<Post> posts = template.query(sql, postRowMapper);
            return posts;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
