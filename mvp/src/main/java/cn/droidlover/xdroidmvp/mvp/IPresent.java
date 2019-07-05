package cn.droidlover.xdroidmvp.mvp;

/**
 * Created by wanglei on 2016/12/29.
 */

public interface IPresent<V> {
    void attachV(V view);//附上View

    void detachV();//分离view

    boolean hasV();//是否有view
}
