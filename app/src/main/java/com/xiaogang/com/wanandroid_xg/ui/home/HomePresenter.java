package com.xiaogang.com.wanandroid_xg.ui.home;

import com.blankj.utilcode.util.SPUtils;
import com.xiaogang.com.wanandroid_xg.base.BasePresenter;
import com.xiaogang.com.wanandroid_xg.bean.Article;
import com.xiaogang.com.wanandroid_xg.bean.Banner;
import com.xiaogang.com.wanandroid_xg.bean.DataResponse;
import com.xiaogang.com.wanandroid_xg.bean.User;
import com.xiaogang.com.wanandroid_xg.net.ApiServer;
import com.xiaogang.com.wanandroid_xg.net.RetrofitManager;
import com.xiaogang.com.wanandroid_xg.utils.Constant;
import com.xiaogang.com.wanandroid_xg.utils.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * author: fangxiaogang
 * date: 2018/9/17
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private int mPage = 0;

    private boolean isRefresh = true;

    @Inject
    public HomePresenter() {

    }

    @Override
    public void getBannerdate() {
        RetrofitManager.create(ApiServer.class)
                .getHomeBanners()
                .compose(RxSchedulers.<DataResponse<List<Banner>>>applySchedulers())
                .compose(mView.<DataResponse<List<Banner>>>bindToLife())
                .subscribe(new Consumer<DataResponse<List<Banner>>>() {
                    @Override
                    public void accept(DataResponse<List<Banner>> listDataResponse) throws Exception {
                        mView.setBannerdate(listDataResponse.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }

    @Override
    public void gethomedate() {
        RetrofitManager.create(ApiServer.class)
                .getHomeArticles(mPage)
                .compose(RxSchedulers.<DataResponse<Article>>applySchedulers())
                .compose(mView.<DataResponse<Article>>bindToLife())
                .subscribe(new Consumer<DataResponse<Article>>() {
                    @Override
                    public void accept(DataResponse<Article> articleDataResponse) throws Exception {
                        if (isRefresh) {
                            mView.sethomedate(articleDataResponse.getData(),0);
                        }else {
                            mView.sethomedate(articleDataResponse.getData(),1);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        if (SPUtils.getInstance(Constant.SPname).getBoolean(Constant.LOGIN)){
            String username = SPUtils.getInstance(Constant.SPname).getString(Constant.USERNAME);
            String password = SPUtils.getInstance(Constant.SPname).getString(Constant.PASSWORD);
            RetrofitManager.create(ApiServer.class)
                    .login(username, password)
                    .compose(RxSchedulers.<DataResponse<User>>applySchedulers())
                    .compose(mView.<DataResponse<User>>bindToLife())
                    .subscribe(new Consumer<DataResponse<User>>() {
                        @Override
                        public void accept(DataResponse<User> userDataResponse) throws Exception {
                            if (userDataResponse.getErrorCode() == 0){

                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    });
        }

    }



    @Override
    public void refresh() {
        mPage = 0;
        isRefresh = true;
        getBannerdate();
        gethomedate();
    }

    @Override
    public void loadMore() {
        mPage ++;
        isRefresh = false;
        gethomedate();
    }


}
