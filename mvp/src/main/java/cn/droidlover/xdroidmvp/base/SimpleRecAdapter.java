package cn.droidlover.xdroidmvp.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.droidlover.xrecyclerview.RecyclerAdapter;

/**
 * Created by wanglei on 2016/11/29.
 * SimpleRecAdapter适合单个itemType情况，RecyclerAdapter适合多个ItemType情况
 */

public abstract class SimpleRecAdapter<T, F extends RecyclerView.ViewHolder> extends RecyclerAdapter<T, F> {

    public SimpleRecAdapter(Context context) {
        super(context);
    }

    /*
    使用listView的时候，通过ViewHolder进行缓存可以提升性能
    ViewGroup和View区别：事件分发方面的区别，UI绘制方面的区别
    1.ViewGroup包含 dispatchTouchEvent()、onInterceptTouchEvent()、onTouchEvent()，而View不包含onInterceptTouchEvent()
    当触发事件，依次为ViewGroup->onInterceptTouchEvent()->false->View:->dispatchTouchEvent()->true->onTouchEvent()
              依次为ViewGroup->onInterceptTouchEvent()->true->dispatchTouchEvent()->onTouchEvent()
    绘制方面：onDraw(),onLayout(),onMeasure()，dispatchDraw(),drawChild()
    Viewm没有dispatchDraw(),drawChild()

    */
    @Override
    public F onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return newViewHolder(view);
    }

    //返回newViewHolder，类似iOS中 tableCell类的自定义
    public abstract F newViewHolder(View itemView);

    //获取laout中界面文件，类似ios中获取cell的xib
    public abstract int getLayoutId();

}
