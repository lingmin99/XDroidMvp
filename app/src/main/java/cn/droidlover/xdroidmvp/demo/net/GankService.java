package cn.droidlover.xdroidmvp.demo.net;

import cn.droidlover.xdroidmvp.demo.model.GankResults;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by wanglei on 2016/12/31.
 */

public interface GankService {

    //Flowable是一个使用Java编写的轻量级业务流程引擎
    @GET("data/{type}/{number}/{page}")
    Flowable<GankResults> getGankData(@Path("type") String type,
                                      @Path("number") int pageSize,
                                      @Path("page") int pageNum);
}
