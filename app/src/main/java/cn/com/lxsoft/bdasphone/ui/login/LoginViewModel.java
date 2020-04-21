package cn.com.lxsoft.bdasphone.ui.login;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

//import com.goldze.mvvmhabit.ui.main.DemoActivity;

import java.util.concurrent.TimeUnit;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.BridgeBaseViewModel;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.net.BridegeNetObserver;
import cn.com.lxsoft.bdasphone.net.ResponseBase;
import cn.com.lxsoft.bdasphone.net.ResponseInfo;
import cn.com.lxsoft.bdasphone.net.ResponseLogin;
import cn.com.lxsoft.bdasphone.net.SubscribeBase;
import cn.com.lxsoft.bdasphone.net.reponse.LoginResponse;
import cn.com.lxsoft.bdasphone.net.reponse.StateResponse;
import cn.com.lxsoft.bdasphone.ui.main.MainActivity;
import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by goldze on 2017/7/17.
 */

public class LoginViewModel extends BridgeBaseViewModel {
    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();





    public class UIChangeObservable {
        //密码开关观察者
        public ObservableBoolean pSwitchObservable = new ObservableBoolean(false);

        public ObservableBoolean checkAutoLogin=new ObservableBoolean(true);
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //userName.set("admin");
        //password.set("123456");
    }

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userName.set("");
        }
    });

    //密码显示开关  (你可以尝试着狂按这个按钮,会发现它有防多次点击的功能)
    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
            uc.pSwitchObservable.set(!uc.pSwitchObservable.get());
        }
    });
    //用户名输入框焦点改变的回调事件
    public BindingCommand<Boolean> onFocusChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean hasFocus) {
            if (hasFocus) {
                clearBtnVisibility.set(View.VISIBLE);
            } else {
                clearBtnVisibility.set(View.INVISIBLE);
            }
        }
    });
    //登录按钮的点击事件
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(SystemConfig.CAN_VPN) {
                subscribeBase.getVPNLogin("test", "qiaoliang@123!").subscribe(new BridegeNetObserver<ResponseInfo>()
                        .dealData(new BridegeNetObserver.DealData<ResponseInfo>() {
                            @Override
                            public void execute(ResponseInfo res) {
                                if (res.result.equals("Success")) {
                                    ToastUtils.showShort("VPN成功");
                                    login();
                                } else
                                    ToastUtils.showShort("VPN失败");
                            }
                        }));
            }
            else {
                login();
            }

            //login();
        }
    });

    /**
     * 网络模拟一个登陆操作
     **/
    private void login() {
        subscribeBase.checkLoginx(userName.get(), password.get())
                .subscribe(new BridegeNetObserver<LoginResponse>(){
                    @Override
                    public void onNext(LoginResponse rx) {
                        if(rx.state==100) {
                            User user=new User();
                            user.setLoginName(rx.data.getLoginName());
                            user.setDanWeiID(rx.data.getDanWeiID());
                            user.setName(rx.data.getName());
                            //user.setDanWeiID(rx.dept);
                            user.setName(rx.data.getName());
                            //user.setRoleID(rx.user.);
                            DataSession.setCurrentUser(user);
                            DataSession.setbInitLoginData(true);
                            SPUtils sp=SPUtils.getInstance("userinfo");

                            if(sp.contains("prev_login_name") && !sp.getString("prev_login_name").equals(userName.get()))
                                AppApplication.dataBase.initData();

                            sp.put("prev_login_name",userName.get());
                            if(uc.checkAutoLogin.get()){
                                sp.put("user_login_name",user.getLoginName());
                                sp.put("user_password",password.get());
                                sp.put("user_department",user.getDanWeiID());
                                sp.put("user_true_name",user.getName());
                                //sp.put("role_id",Integer.parseInt(rx.role));
                            }
                            else
                                sp.clear();

                            startActivity(MainActivity.class);
                        }
                        else{
                            ToastUtils.showShort(rx.info);
                        }
                    }
                });

        //startActivity(MainActivity.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStop() {
        AppManager.getAppManager().finishActivity(LoginActivity.class);
    }
}
