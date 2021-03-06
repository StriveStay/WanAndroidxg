package com.xiaogang.com.wanandroid_xg.net;

import com.xiaogang.com.wanandroid_xg.bean.Article;
import com.xiaogang.com.wanandroid_xg.bean.Articleitem;
import com.xiaogang.com.wanandroid_xg.bean.Banner;
import com.xiaogang.com.wanandroid_xg.bean.DataResponse;
import com.xiaogang.com.wanandroid_xg.bean.Knowledge;
import com.xiaogang.com.wanandroid_xg.bean.Project;
import com.xiaogang.com.wanandroid_xg.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * author: fangxiaogang
 * date: 2018/9/1
 */

public interface ApiServer {

    //登录
    @POST("user/login")
    @FormUrlEncoded
    Observable<DataResponse<User>> login(@Field("username") String username, @Field("password") String password);

    //我的收藏
    @GET("/lg/collect/list/{page}/json")
    Observable<DataResponse<Article>> getMyCollect(@Path("page") int page);

    //首页Banner
    @GET("/banner/json")
    Observable<DataResponse<List<Banner>>> getHomeBanners();

    //首页数据
    @GET("/article/list/{page}/json")
    Observable<DataResponse<Article>> getHomeArticles(@Path("page") int page);

    //注册
    @POST("/user/register")
    @FormUrlEncoded
    Observable<DataResponse<User>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);


    //知识体系
    //http://www.wanandroid.com/tree/json
    @GET("/tree/json")
    Observable<DataResponse<List<Knowledge>>> getKnowledge();

    //项目
    //http://www.wanandroid.com/project/tree/json
    @GET("/project/tree/json")
    Observable<DataResponse<List<Project>>> getProject();

    //添加收藏
    @POST("/lg/collect/{id}/json")
    Observable<DataResponse> addCollect(@Path("id") int id);

    //项目内容
    //http://www.wanandroid.com/project/list/1/json?cid=294
    @GET("/project/list/{curpage}/json")
    Observable<DataResponse<Articleitem>> getArticleItem(@Path("curpage") int curpage, @Query("cid")int id);

    //知识体系文章列表
    //http://www.wanandroid.com/article/list/0/json?cid=60
    @GET("/article/list/{page}/json")
    Observable<DataResponse<Article>> getKnowledgelist(@Path("page") int page, @Query("cid") int cid);


    //搜索
    //http://www.wanandroid.com/article/query/0/json
    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    Observable<DataResponse<Article>> getSearchArticles(@Path("page") int page, @Field("k") String k);

    //取消收藏
    @POST("/lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<DataResponse> removeCollect(@Path("id") int id, @Field("originId") int originId);
}
