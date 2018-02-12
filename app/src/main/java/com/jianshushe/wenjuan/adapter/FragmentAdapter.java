package com.jianshushe.wenjuan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 项目名：WenJuan
 * 包名 ： com.jianshushe.wenjuan.adapter
 * 文件名：FragmentAdapter
 * 创建者：jiushaba
 * 创建时间：2018/1/4 0004上午 11:23
 * 描述： TODO
 */
public class FragmentAdapter extends FragmentPagerAdapter {


    private FragmentManager fragmentManager;
    List<Fragment> fragmentList;

    public FragmentAdapter(FragmentManager fm,  List<Fragment> fragmentList) {

        super(fm);
        this.fragmentManager = fm;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
