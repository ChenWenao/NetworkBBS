package com.Controller;

import com.Bean.Post;
import com.Bean.User;
import com.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    //增
    //新建贴
    //要求传入一个表单：newPost
    //表单要求包括字段：postTitle，postContent，postComId
    //处理逻辑，当前登录的用户在postComId吧发布一条帖子
    @PostMapping("Post/newPost")
    public boolean newPost(HttpSession session, @ModelAttribute(value = "newPost") Post newPost) {

        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------

        return postService.addNewPost(newPost.getPostTitle(), newPost.getPostContent(), ((User) session.getAttribute("loginUser")).getUserId(), newPost.getPostComId());
    }

    //删帖
    //贴主删帖，或者管理员封禁贴时调用。
    //调用该接口时，后台会做身份核验，若登录的用户不是贴主，或者用户不是管理员，则会返回false
    //需要传入的值：postId
    @GetMapping("Post/dropPost/{postId}")
    public boolean dropPost(HttpSession session, @PathVariable("postId") int postId) {

        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        loginUser.setUserLevel(0);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------


        Post post_del = postService.getPostById(postId);
        if (post_del == null)
            return false;
        if (post_del.getPostOwnerId() == ((User) session.getAttribute("loginUser")).getUserId() || ((User) session.getAttribute("loginUser")).getUserLevel() == 0) {
            return postService.removePost(post_del.getPostId(), post_del.getPostComId());
        } else {
            return false;
        }
    }

    //多查询
    //param：用于搜索，表示搜索哪个字段，默认搜索postTitle
    //ownerId：用于搜索，表示搜索某位用户或自己发的帖子，传入的为目标用户的id，传入0表示查询自己发布的帖子，不传入则默认在所有的帖子里面查询。
    //qw：用于搜索，搜索param的字段中包括value的结果。
    //若param和value都为“all”，表示不定向搜索。默认为这种情况。
    //order_by：表示根据哪个字段排序。默认根据 postHeat排序。
    //order：用于排序，为0表示正序，为1表示倒序。默认为 正序
    //pageSize：表示分页页面大小。 默认为 10
    //page：表示查询第几页的数据。 默认为 1
    //若pageSize和page都为0，则不分页，返回所有数据。
    @GetMapping("Post/search")
    public List<Post> searchPosts(HttpSession session, @RequestParam(name = "param", defaultValue = "communityName") String param, @RequestParam(value = "ownerId", defaultValue = "-1") int ownerId, @RequestParam("qw") String value, @RequestParam(name = "order_by", defaultValue = "communityHeat") String order_by, @RequestParam(name = "order", defaultValue = "0") int order, @RequestParam(name = "pageSize", defaultValue = "5") int pageSize, @RequestParam(name = "page", defaultValue = "1") int page) {
        //-----------------------------暂时新添的Session-------------------------------------
        User loginUser = new User();
        loginUser.setUserId(1);
        loginUser.setUserLevel(0);
        session.setAttribute("loginUser", loginUser);
        //---------------------------------------------------------------------------------


        if (ownerId == 0)
            return postService.getPosts(param, ((User) session.getAttribute("loginUser")).getUserId(), value, order_by, order, pageSize, page);
        else
            return postService.getPosts(param, ownerId, value, order_by, order, pageSize, page);
    }

}
