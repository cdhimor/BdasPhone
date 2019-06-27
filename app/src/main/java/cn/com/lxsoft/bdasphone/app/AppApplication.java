package cn.com.lxsoft.bdasphone.app;

import org.greenrobot.greendao.query.QueryBuilder;

import cn.com.lxsoft.bdasphone.R;
//import cn.com.lxsoft.bdasphone.ui.login.LoginActivity;

import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.DataBaseGreenImpl;
import cn.com.lxsoft.bdasphone.database.DataSession;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.User;
import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.crash.CaocConfig;

/**
 * Created by goldze on 2017/7/16.
 */

public class AppApplication extends BaseApplication {
    public static DataBase dataBase=null;

    @Override
    public void onCreate() {
        super.onCreate();
        //是否开启打印日志
        //KLog.init(BuildConfig.DEBUG);
        //初始化全局异常崩溃
        initCrash();
        //内存泄漏检测
//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }

        //数据库初始化
        dataBase=DataBaseGreenImpl.getInstance();
        dataBase.init(this);

        DataDict.init(getApplicationContext());

        DataSession.init(this);

        SystemConfig.init(this);

        //测试数据
        //dataBase.initTestData();
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        DataSession.setCurrentDanWei(new DanWei("1111000000000","北京市交委会路政局"));
        DataSession.setCurrentUser(new User("测试用户","1111000000000"));
  }

    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                //.restartActivity(LoginActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }
}
