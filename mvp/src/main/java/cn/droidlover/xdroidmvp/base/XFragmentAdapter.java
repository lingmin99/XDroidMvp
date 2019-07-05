package cn.droidlover.xdroidmvp.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2016/12/10.
 */

/*
相同点：
FragmentPagerAdapter和FragmentStatePagerAdapter都继承自PagerAdapter
不同点：
卸载不再需fragment时，各自采用的处理方法有所不同

也就是：在destroyItem()方法中，FragmentStatePagerAdapter调用的是remove()方法，适用于页面较多的情况；
FragmentPagerAdapter调用的是detach()方法，适用于页面较少的情况。但是有页面数据需要刷新的情况，不管是页面少还是多，
还是要用FragmentStatePagerAdapter，否则页面会因为没有重建得不到刷新

*/
public class XFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] titles;

    public XFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
        super(fm);
        this.fragmentList.clear();
        this.fragmentList.addAll(fragmentList);
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > position) {
            return titles[position];
        }
        return "";
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
