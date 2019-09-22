package cn.com.lxsoft.bdasphone.net;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.BridgeBaseViewModel;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.entity.BridgeChart;
import cn.com.lxsoft.bdasphone.entity.Chart;
import cn.com.lxsoft.bdasphone.entity.Check;
import cn.com.lxsoft.bdasphone.entity.CheckTemp;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.PatrolTemp;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.UpdateVersion;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.entity.YearTest;
import cn.com.lxsoft.bdasphone.utils.RetrofitClient;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import me.goldze.mvvmhabit.http.ExceptionHandle;
import retrofit2.HttpException;

import static android.support.constraint.Constraints.TAG;


public class SubscribeBase {
    BridgeBaseViewModel viewModel;

    public SubscribeBase(BridgeBaseViewModel vm){
        viewModel=vm;
    }


    public Observable<ResponseList<BridgeChart>> getReportData(String gydw, String type){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).getReportData(gydw,type));
    }

    public Observable<ResponseList<User>> getUserData(){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).getUserData());
    }


    Gson examineGson=null;
    private Gson getExamineGson(){
        if(examineGson==null) {
            examineGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            //过滤掉字段名包含"id","address"的字段
                            return f.getName().equals("worker") | f.getName().equals("owner") | f.getName().equals("bridge") | f.getName().equals("ID");
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            //过滤掉 类名包含 Bean的类
                            return false;
                        }
                    })
                    .create();
        }
        return examineGson;
    }

    public Observable<ResponseInfo> setPatrolData(RequestBase<Patrol> data){
        String tps=getExamineGson().toJson(data);
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).setPatrolData(tps));
    }

    public Observable<ResponseInfo> setPatrolTempData(RequestBase<PatrolTemp> data){
        String tps=getExamineGson().toJson(data);
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).setPatrolData(tps));
    }

    public Observable<ResponseInfo> setCheckTempData(RequestBase<CheckTemp> data){
        String tps=getExamineGson().toJson(data);
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).setCheckData(tps));
    }

    public Observable<ResponseList<Patrol>> getPatrolListData(int pageIndex,int pageSize){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).getPatrolData(pageIndex,pageSize,"日期","asc"));
    }

    public Observable<ResponseList<Check>> getCheckListData(int pageIndex,int pageSize){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).getCheckData(pageIndex,pageSize,"日期","asc"));
    }

    public Observable<ResponseList<YearTest>> getYearTestListData(int pageIndex, int pageSize){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).getYearTestData(pageIndex,pageSize,"日期","asc"));
    }

    public Observable<ResponseList<Construct>> getConstructData(String qldm){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).getConstructData(qldm));
    }

    public Observable<ResponseList<Disease>> getDiseaseData(String exam){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).getDiseaseData(exam));
    }

    public Observable<ResponseInfo> setCheckData(RequestBase<Check> data){
        String tps=getExamineGson().toJson(data);
        Log.d(TAG, tps);
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).setCheckData(tps));
    }

    public Observable<ResponseInfo> uploadCommMedia(String qldm,File file){
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part=MultipartBody.Part.createFormData("filename",file.getName(),imageBody);
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("qldm", qldm);
        stringStringMap.put("ms", "");
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).upLoadCommMedia(stringStringMap,part));
    }

    public Observable<ResponseInfo> uploadPatrolMedia(String examineID,File file){
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part=MultipartBody.Part.createFormData("filename",file.getName(),imageBody);
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("qldm", examineID);
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).upLoadPatrolMedia(stringStringMap,part));
    }

    public Observable<ResponseInfo> uploadCheckMedia(String examineID,File file){
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part=MultipartBody.Part.createFormData("filename",file.getName(),imageBody);
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("qldm", examineID);
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).upLoadCheckMedia(stringStringMap,part));
    }

    public Observable<ResponseLogin> checkLogin(String username,String pswd){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).checkLogin(username,pswd));//"check_login"
    }

    public Observable<ResponseList<QiaoLiang>> getBridgeData(int page,int rows){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).getBridgeData(page,rows));
    }

    public Observable<ResponseList<DanWei>> getDepartmentData(){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).getDepartmentData());
    }

    public Observable<ResponseList<LuXian>> getRoadData(){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).getRoadData());
    }

    public Observable<ResponseInfo> setGisData(String qldm,Double lat,Double lng){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).setBridgeGis(qldm,lat,lng));
    }

    public Observable<ResponseContent> getContentData(String qldm){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).getContentData(qldm));
    }

    public Observable<UpdateVersion> getApkUpdateData(){
        return dealHttpData(RetrofitClient.getInstance().create(HttpApi.class).getApkUpdateData());
    }


    protected <T> Observable<T> dealHttpData(Observable<T> ob) {
        LifecycleProvider lp=viewModel.getLifecycleProvider();
        if(lp!=null)
            ob=ob.compose(RxUtils.bindToLifecycle(lp));
        return ob.compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer());
    }

    protected <T> void getHttpListData(Class<T> T,Observable<ResponseList<T>> ob,String resCode){
        LifecycleProvider lp=viewModel.getLifecycleProvider();
        if(lp!=null)
            ob=ob.compose(RxUtils.bindToLifecycle(lp));
        ob.compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //viewModel.showDialog("正在请求...");
                    }
                })
                .subscribe(new Consumer<ResponseList<T>>() {
                    @Override
                    public void accept(ResponseList<T> res) throws Exception {
                        //
                        // viewModel.dealData((ResponseBase)res);
                        viewModel.dealNetData(resCode,res.DATA);
                        //DataBase database=AppApplication.dataBase;
                        //database.insertDeptData(response);
                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable e) throws Exception {
                        //关闭对话框
                        //viewModel.dismissDialog();
                        //请求刷新完成收回
                        //uc.finishRefreshing.set(!uc.finishRefreshing.get());
                        if(e instanceof Exception){
                            //访问获得对应的Exception
                            //onError(ExceptionHandle.handleException(e));
                        }else {
                            //将Throwable 和 未知错误的status code返回
                            //onError(new ResponseThrowable(e,ExceptionHandle.ERROR.UNKNOWN));
                        }
                        //ToastUtils.showShort(throwable.message);

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //关闭对话框
                        //viewModel.dismissDialog();
                        //请求刷新完成收回
                        //uc.finishRefreshing.set(!uc.finishRefreshing.get());
                    }
                });
    }
}
