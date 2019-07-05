package cn.droidlover.xdroidmvp.demo.ui;

import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.demo.model.GankResults;
import cn.droidlover.xdroidmvp.demo.present.PBasePager;
import cn.droidlover.xdroidmvp.demo.widget.StateView;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by wanglei on 2016/12/31.
 */

//extends是继承父类，只要那个类不是声明为final或者那个类定义为abstract的就能继承
public abstract class BasePagerFragment extends XLazyFragment<PBasePager> {

    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;//可用于自定义Loading、Error、Empty、Content

    StateView errorView;

    protected static final int MAX_PAGE = 10;


    @Override
    public void initData(Bundle savedInstanceState) {
        initAdapter();
        getP().loadData(getType(), 1);//获取数据
    }

    /*
    XRecyclerView第三方库实现了下拉刷新，滚动到底部加载更多以及添加header功能的的RecyclerView。使用方式和RecyclerView完全一致，
    不需要额外的layout，不需要写特殊的adater。 加载效果内置了AVLoadingIndicatorView上的所有效果，可以根据需要指定。
     */
    private void initAdapter() {
        setLayoutManager(contentLayout.getRecyclerView());
        contentLayout.getRecyclerView()
                .setAdapter(getAdapter());
        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        contentLayout.showLoading();
                        getP().loadData(getType(), 1);
                    }//下拉刷新

                    @Override
                    public void onLoadMore(int page) {
                        contentLayout.showLoading();
                        getP().loadData(getType(), page);//上拉加载更多
                    }
                });


        if (errorView == null) {
            errorView = new StateView(context);
        }
        contentLayout.errorView(errorView);
        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null));//加载效果页面
        contentLayout.getRecyclerView().useDefLoadMoreView();//使用加载更多默认效果
        contentLayout.showLoading();

    }

    public abstract SimpleRecAdapter getAdapter();

    public abstract void setLayoutManager(XRecyclerView recyclerView);

    public abstract String getType();


    public void showError(NetError error) {
        if (error != null) {
            switch (error.getType()) {
                case NetError.ParseError:
                    errorView.setMsg("数据解析异常");
                    break;

                case NetError.AuthError:
                    errorView.setMsg("身份验证异常");
                    break;

                case NetError.BusinessError:
                    errorView.setMsg("业务异常");
                    break;

                case NetError.NoConnectError:
                    errorView.setMsg("网络无连接");
                    break;

                case NetError.NoDataError:
                    errorView.setMsg("数据为空");
                    break;

                case NetError.OtherError:
                    errorView.setMsg("其他异常");
                    break;
            }
            contentLayout.showError();
        }
    }

    //显示返回的数据
    public void showData(int page, GankResults model) {
        if (page > 1) {
            getAdapter().addData(model.getResults());
        } else {
            getAdapter().setData(model.getResults());
        }

        //更新当前页码
        contentLayout.getRecyclerView().setPage(page, MAX_PAGE);

        if (getAdapter().getItemCount() < 1) {
            contentLayout.showEmpty();
            return;
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_pager;
    }

    @Override
    public PBasePager newP() {
        return new PBasePager();
    }
}
