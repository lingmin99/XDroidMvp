package cn.droidlover.xdroidmvp.demo.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.XFragmentAdapter;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by wanglei on 2016/12/22.
 */

public class MainActivity extends XActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;//导航栏
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;//Viewpager，视图翻页工具，提供了多页面切换的效果

    List<Fragment> fragmentList = new ArrayList<>();
    String[] titles = {"首页", "干货", "妹子"};

    XFragmentAdapter adapter;//适配器


    @Override //初始化数据 onCreate中调用了此方法
    public void initData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);

        fragmentList.clear();
        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(GanhuoFragment.newInstance());
        fragmentList.add(GirlFragment.newInstance());

        if (adapter == null) {
            //为了管理Activity中的fragments，需要使用FragmentManager
            adapter = new XFragmentAdapter(getSupportFragmentManager(), fragmentList, titles);
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override //提供加载的view
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override//菜单（选项菜单optionsMenu,上下文菜单：ContextMenu,子菜单subMenu）
    public int getOptionsMenuId() {
        return R.menu.menu_main;
    }

    @Override//菜单选项，选中事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_droid:
                AboutActivity.launch(context);//跳转到关于页面
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Object newP() {
        return null;
    }
}
