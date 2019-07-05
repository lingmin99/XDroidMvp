package cn.droidlover.xdroidmvp.demo.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2016/12/31.
 * Android中有六大布局,分别是: LinearLayout(线性布局)，RelativeLayout(相对布局)，
 * TableLayout(表格布局) FrameLayout(帧布局)，AbsoluteLayout(绝对布局)，GridLayout(网格布局)
 */

public class StateView extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    //重写构造行数
    //Context提供了一个应用的运行环境，在Context的大环境里，应用才可以访问资源，才能完成和其他组件、服务的交互，Context定义了一套基本的功能接口
    public StateView(Context context) {
        super(context);
        setupView(context);
    }

    public StateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        //inflate根据布局id把这个布局加载成一个View并返回的
        inflate(context, R.layout.view_state, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }
}
