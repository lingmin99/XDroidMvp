package cn.droidlover.xdroidmvp.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.demo.kit.AppKit;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xstatecontroller.XStateController;

/**
 * Created by wanglei on 2016/12/31.
 */

public class WebActivity extends XActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.contentLayout)
    XStateController contentLayout;

    String url;
    String desc;

    public static final String PARAM_URL = "url";
    public static final String PARAM_DESC = "desc";


    @Override
    public void initData(Bundle savedInstanceState) {
        url = getIntent().getStringExtra(PARAM_URL);
        desc = getIntent().getStringExtra(PARAM_DESC);


        initToolbar();
        initContentLayout();
        initRefreshLayout();
        initWebView();
    }

    private void initContentLayout() {
        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null));
    }

    private void initRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(url);
            }
        });

    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (contentLayout != null)
                        contentLayout.showContent();
                    if (webView != null)
                        url = webView.getUrl();
                } else {
                    if (contentLayout != null)
                        contentLayout.showLoading();//显示加载页面
                }
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setBuiltInZoomControls(true);//是否能伸缩
        webView.getSettings().setJavaScriptEnabled(true);//
        webView.getSettings().setDomStorageEnabled(true);//开启DOM storage API 功能
        webView.getSettings().setDatabaseEnabled(true);//开启database storage API功能
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//设置缓存模式
        webView.getSettings().setAppCacheEnabled(true);//数据缓存

        webView.loadUrl(url);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle(desc);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//菜单栏目事件
        switch (item.getItemId()) {
            case android.R.id.home://返回
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            case R.id.action_share://分享
                AppKit.shareText(this, webView.getTitle() + " " + webView.getUrl() + " 来自「XDroid」");
                break;
            case R.id.action_refresh://刷新
                webView.reload();
                break;
            case R.id.action_copy://复制链接
                AppKit.copyToClipBoard(this, webView.getUrl());
                break;
            case R.id.action_open_in_browser://在浏览器打开
                AppKit.openInBrowser(this, webView.getUrl());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
        }
    }

    public static void launch(Activity activity, String url, String desc) {
        //Intent是一种运行时绑定（runtime binding)机制，它能在程序运行的过程中连接两个不同的组件。
        Router.newIntent(activity).anim(R.anim.slide_in_right,R.anim.slide_out_left)//动画效果子视图从右->左划进来,父视图向左边出，左出 右进
                .to(WebActivity.class)
                .putString(PARAM_URL, url)//传递的参数值
                .putString(PARAM_DESC, desc)
                .launch();
    }

    @Override
    public int getOptionsMenuId() {
        return R.menu.menu_web;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public Object newP() {
        return null;
    }
}
