package cn.com.lxsoft.bdasphone.ui.main;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.BridgeBaseViewModel;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.entity.BridgeChart;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.UpdateVersion;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.net.BridegeNetObserver;
import cn.com.lxsoft.bdasphone.net.HttpApi;
import cn.com.lxsoft.bdasphone.net.ResponseBase;
import cn.com.lxsoft.bdasphone.net.ResponseInfo;
import cn.com.lxsoft.bdasphone.net.ResponseList;
import cn.com.lxsoft.bdasphone.net.ResponseLogin;
import cn.com.lxsoft.bdasphone.net.SubscribeBase;
import cn.com.lxsoft.bdasphone.net.reponse.LoginResponse;
import cn.com.lxsoft.bdasphone.net.request.LoginRequest;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragment;
import cn.com.lxsoft.bdasphone.ui.browse.GISFragment;
import cn.com.lxsoft.bdasphone.ui.chart.FragmentChart;
import cn.com.lxsoft.bdasphone.ui.chart.LayoutChartViewModel;
import cn.com.lxsoft.bdasphone.ui.examine.ExamineBrowseFragment;
import cn.com.lxsoft.bdasphone.ui.login.LoginActivity;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.http.DownLoadManager;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.http.download.ProgressCallBack;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.ResponseBody;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by goldze on 2017/7/17.
 */

public class MainViewModel extends BridgeBaseViewModel {
    User user;
    DanWei danWei;
    public ObservableField<String> userDanWeiInfo=new ObservableField<>();
    public int nActivityPosition=0;
    private ObservableBoolean obGetDepartment=new ObservableBoolean(false);

    public LayoutChartViewModel layoutChartViewModel;

    public MainViewModel(@NonNull Application application) {
        super(application);
        layoutChartViewModel=new LayoutChartViewModel(application);
        layoutChartViewModel.parentViewModel=this;
    }

    String username="";
    String password="";
    @Override
    public void onCreate() {
        boolean bLogin=false;
        User curUser=DataSession.getCurrentUser();
        if(curUser==null){
            SPUtils sp=SPUtils.getInstance("userinfo");
            if(sp.contains("user_login_name")) {
                username = sp.getString("user_login_name");
                password = sp.getString("user_password");
                String department = sp.getString("user_department");
                String truename = sp.getString("user_true_name");
                int roleid = sp.getInt("role_id");

                User user = new User();
                user.setLoginName(username);
                user.setDanWeiID(department);
                user.setName(truename);
                user.setRoleID(roleid);
                DataSession.setCurrentUser(user);
                setDepartment();
                layoutChartViewModel.dealChartData(false);

                if(SystemConfig.CAN_VPN) {
                    subscribeBase.getVPNLogin("test", "qiaoliang@123!").subscribe(new BridegeNetObserver<ResponseInfo>()
                            .dealData(new BridegeNetObserver.DealData<ResponseInfo>() {
                                @Override
                                public void execute(ResponseInfo res) {
                                    if (res.result.equals("Success")) {
                                        ToastUtils.showShort("VPN成功");
                                        dealLogin();
                                    } else
                                        ToastUtils.showShort("VPN失败");
                                }
                            }));
                }
                else {
                    dealLogin();
                }
            }
            else {
                startActivity(LoginActivity.class);
                finish();
            }
        }
        else {
            setDepartment();
            layoutChartViewModel.dealChartData(false);
        }
        if(DataSession.getbInitLoginData()){
            getInitNetData();
            DataSession.setbInitLoginData(false);
        }
    }

    protected void dealLogin(){
        subscribeBase.checkLogin(username, password).subscribe(new BridegeNetObserver<ResponseLogin>()
                .dealData(new BridegeNetObserver.DealData<ResponseLogin>() {
                    @Override
                    public void execute(ResponseLogin res) {
                        ResponseLogin rlogin = (ResponseLogin) res;
                        if(rlogin.state.equals("success")) {
                            ToastUtils.showShort("登录成功");
                            getInitNetData();
                        } else
                            ToastUtils.showShort("登录错误");
                    }
                }));
    }

    public void dealLoginx(){
        subscribeBase.checkLoginx(username, password).subscribe(new BridegeNetObserver<LoginResponse>()
                .dealData(new BridegeNetObserver.DealData<LoginResponse>() {
                    @Override
                    public void execute(LoginResponse res) {
                        LoginResponse rlogin = (LoginResponse) res;
                        if(rlogin.state==100) {
                            //ToastUtils.showShort("登录成功");
                            //getInitNetData();
                            return;
                        } else
                            //ToastUtils.showShort("登录错误");
                            return;
                    }
                }));
    }

    protected void getInitNetData(){
        layoutChartViewModel.dealChartData(true);
        Observable bridgeOb=subscribeBase.getBridgeData(1, SystemConfig.DATA_MAX_NUM)
            .observeOn(Schedulers.io())
            .map(new Function < ResponseList<QiaoLiang>, Integer> () {
                @Override
                public Integer apply(ResponseList<QiaoLiang> resData) throws Exception {
                    Log.d(TAG, "======================Insert QiaoLiang " + Thread.currentThread().getName());
                    List<QiaoLiang> tplist = resData.DATA;
                    DataBase database = AppApplication.dataBase;
                    database.insertBridgeData(tplist);
                    return 1;
                }
            });

        Observable departmentOb=subscribeBase.getDepartmentData()
                .observeOn(Schedulers.io())
                .map(new Function < ResponseList<DanWei>, Integer> () {
                    @Override
                    public Integer apply(ResponseList<DanWei> resData) throws Exception {
                        Log.d(TAG, "======================Insert Department " + Thread.currentThread().getName());
                        DataBase database = AppApplication.dataBase;
                        database.insertDeptData(resData.DATA);
                        setDepartment();
                        return 2;
                    }
                });

        Observable roadOb=subscribeBase.getRoadData()
                .observeOn(Schedulers.io())
                .map(new Function < ResponseList<LuXian>, Integer> () {
                    @Override
                    public Integer apply(ResponseList<LuXian> resData) throws Exception {
                        Log.d(TAG, "======================Insert LuXian " + Thread.currentThread().getName());
                        AppApplication.dataBase.insertRoadData(resData.DATA);
                        return 3;
                    }
                });

        List<Observable<Integer>> obList=new ArrayList<>();
        if(!AppApplication.dataBase.checkBridgeCount())
            obList.add(bridgeOb);
        if(AppApplication.dataBase.getDeptDataCount()==0)
            obList.add(departmentOb);
        if(AppApplication.dataBase.getLuXianDataCount()==0)
            obList.add(roadOb);

        if(obList.size()>0) {
            Observable.concatArray(obList.toArray(new Observable[obList.size()]))
                    .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer()) //线程调度
                    .compose(RxUtils.exceptionTransformer())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            Log.d(TAG, "==================doOnSubscribe ");
                            showDialog("正在初始化数据");
                        }
                    })
                    .subscribe(new BridegeNetObserver<Integer>() {
                        int obNum = 0;

                        @Override
                        public void onNext(Integer resData) {
                            obNum++;
                            if (obNum == obList.size()) {
                                dismissDialog();
                                ToastUtils.showShort("初始化数据完成");
                            }
                        }
                    });
        }
    }


    protected void getInitNetDataEx(){
        layoutChartViewModel.dealChartData(true);
        if(!AppApplication.dataBase.checkBridgeCount()) {
            //Log.d(TAG, "======================开始获取桥梁数据 " + Thread.currentThread().getName());
            subscribeBase.getBridgeData(1, 30000)
                    .observeOn(Schedulers.io())
                    .subscribe(new BridegeNetObserver<ResponseList<QiaoLiang>>(){
                                @Override
                                public void onNext(ResponseList<QiaoLiang> resData) {
                                    Log.d(TAG, "======================Insert QiaoLiang " + Thread.currentThread().getName());
                                    List<QiaoLiang> tplist = resData.DATA;
                                    DataBase database = AppApplication.dataBase;
                                    database.insertBridgeData(tplist);
                                }
                            });
        }
        if(AppApplication.dataBase.getDeptDataCount()==0) {
            Log.d(TAG, "======================开始获取单位数据 " + Thread.currentThread().getName());
            subscribeBase.getDepartmentData()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        setDepartment();
                        Log.d(TAG, "======================单位数据初始化完毕 ");
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new BridegeNetObserver<ResponseList<DanWei>>(){
                    @Override
                    public void onNext(ResponseList<DanWei> resData) {
                        DataBase database = AppApplication.dataBase;
                        database.insertDeptData(resData.DATA);
                    }});
        }
        else
            setDepartment();

        if(AppApplication.dataBase.getLuXianDataCount()==0) {
            subscribeBase.getRoadData()
                .observeOn(Schedulers.io())
                .subscribe(new BridegeNetObserver<ResponseList<LuXian>>(){
                            @Override
                            public void onNext(ResponseList<LuXian> resData) {
                                AppApplication.dataBase.insertRoadData(resData.DATA);
                                Log.d(TAG, "======================Insert LuXian " + Thread.currentThread().getName());
                            }
                        });
        }

        if(AppApplication.dataBase.getUserDataCount()==0) {
            subscribeBase.getUserData()
                    .observeOn(Schedulers.io())
                    .subscribe(new BridegeNetObserver<ResponseList<User>>(){
                        @Override
                        public void onNext(ResponseList<User> resData) {
                            AppApplication.dataBase.insertUserData(resData.DATA);
                            Log.d(TAG, "======================Insert User " + Thread.currentThread().getName());
                        }
                    });
        }

    }


    protected void setDepartment(){
        DataBase database = AppApplication.dataBase;
        DanWei danWei=database.getDepartment(DataSession.getCurrentUser().getDanWeiID());
        if(danWei!=null) {
            DataSession.setCurrentDanWei(danWei);
            user = DataSession.getCurrentUser();
            danWei = DataSession.getCurrentDanWei();
            userDanWeiInfo.set(danWei.getMingCheng() + "　" + user.getName());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

/*
    boolean bGetDept=false,bGetBridge=false,bGetRoad=false;
    @Override
    public void dealNetData(String tpclass,Object res){
        //bInitDataOK.s
        if(tpclass==null)
            return;
        if(tpclass.equals("check_login")){
            ResponseLogin rlogin=(ResponseLogin)res;
            if(rlogin.state.equals("success")) {
                ToastUtils.showShort("登录成功");
                dealInitNetData();
            }
            else
                ToastUtils.showShort("登录错误");
        }
        if(tpclass.equals("dept_list")) {
            List<DanWei> tplist = (List<DanWei>) res;
            DataBase database = AppApplication.dataBase;
            database.insertDeptData(tplist);
            setDepartment();
            bGetDept=true;
        }
        else if(tpclass.equals("bridge_list")){
            List<QiaoLiang> tplist = (List<QiaoLiang>) res;
            DataBase database = AppApplication.dataBase;
            database.insertBridgeData(tplist);
            bGetBridge=true;
        }
        else if(tpclass.equals("road_list")){
            List<LuXian> tplist = (List<LuXian>) res;
            AppApplication.dataBase.insertRoadData(tplist);
            bGetRoad=true;
        }
        else if(tpclass.equals("Qlfl_Gydw")){
            List<BridgeChart> tplist = (List<BridgeChart>) res;
            AppApplication.dataBase.insertChartData(tplist,tpclass);
            layoutChartViewModel.dealChartData();
        }
        if(bGetDept && bGetBridge && bGetRoad)
            ToastUtils.showShort("获取初始化数据完毕");
    }
*/

    public BindingCommand getInitData = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            testUpdateAPK();
            //getInitNetData();
            //downFile("http://171.217.92.131:18008/Content/pic/rcbh/G110150304L999020190825/8610445f-7682-4eb6-a3b6-d42fcdf29dc5.jpg");
        }
    });

    public BindingCommand dealLogin = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(LoginActivity.class);
        }
    });

    public void onIconSelect(String type) {

        Bundle mBundle;
        switch(type)
        {
            case "browse":
                startContainerActivity(BrowseFragment.class.getCanonicalName());
                //startActivity(BrowseActivity.class);
                break;
            case "patrol":
                mBundle = new Bundle();
                mBundle.putShort("type",SystemConfig.ExamineStyle_Patrol);
                startContainerActivity(ExamineBrowseFragment.class.getCanonicalName(),mBundle);
                break;
            case "chart":
                startContainerActivity(FragmentChart.class.getCanonicalName());
                break;
            case "check":
                mBundle = new Bundle();
                mBundle.putShort("type",SystemConfig.ExamineStyle_Check);
                startContainerActivity(ExamineBrowseFragment.class.getCanonicalName(),mBundle);
                break;
            case "gis":
                startContainerActivity(GISFragment.class.getCanonicalName());
                break;
            case "test":
                mBundle = new Bundle();
                mBundle.putShort("type",SystemConfig.ExamineStyle_Test);
                startContainerActivity(ExamineBrowseFragment.class.getCanonicalName(),mBundle);
                break;
        }
    }

    @Override
    public void onStop() {
        //if(DataSession.getCurrentUser()==null)
        //    AppManager.getAppManager().finishActivity(MainActivity.class);


    }

    protected void testUpdateAPK(){
        subscribeBase.getApkUpdateData().subscribe(new BridegeNetObserver<UpdateVersion>(){
            @Override
            public void onNext(UpdateVersion resData) {
                //Log.d(TAG, "======================Insert QiaoLiang " + Thread.currentThread().getName());
                if(resData.ver>ActivityUtils.packageCode()){
                    MaterialDialogUtils.showBasicDialogNoTitle(AppManager.getAppManager().currentActivity(),"有新版本是否升级")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    downFile(SystemConfig.BASE_URL + "bdas.apk", String.valueOf(resData.ver));
                                }
                            }).show();
                }
            }
        });
    }

    private void downFile(String url,String vercode) {
        String destFileDir = getApplication().getCacheDir().getPath();
        String destFileName = "bdas"+vercode+".apk";
        final ProgressDialog progressDialog = new ProgressDialog(AppManager.getAppManager().currentActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在下载...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        DownLoadManager.getInstance().load(url, new ProgressCallBack<ResponseBody>(destFileDir, destFileName) {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onCompleted() {
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(ResponseBody responseBody) {
                ToastUtils.showShort("文件下载完成！");
                File file=new File(destFileDir+"/"+destFileName);
                installAPK(file);
            }

            @Override
            public void progress(final long progress, final long total) {
                progressDialog.setMax((int) total);
                progressDialog.setProgress((int) progress);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ToastUtils.showShort("文件下载失败！");
                progressDialog.dismiss();
            }
        });

    }

    ////调用系统的安装方法
    private void installAPK(File savedFile) {
        Activity activity=AppManager.getAppManager().currentActivity();
        //调用系统的安装方法
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(activity, "cn.com.lxsoft.bdasphone", savedFile);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //LogUtils.e("AutoUpdate","7.0data="+data);
        } else {
            data = Uri.fromFile(savedFile);
            //LogUtils.e("AutoUpdate","111data="+data);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        activity.startActivity(intent);
        //activity.finish();
    }
}
