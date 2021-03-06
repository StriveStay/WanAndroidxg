package com.xiaogang.com.wanandroid_xg.di.component;

import android.app.Activity;
import android.content.Context;

import com.xiaogang.com.wanandroid_xg.di.moudule.ActivityModule;
import com.xiaogang.com.wanandroid_xg.di.scope.ContextLife;
import com.xiaogang.com.wanandroid_xg.di.scope.PerActivity;


import dagger.Component;

/**
 * author: fangxiaogang
 * date: 2018/9/3
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();


}