package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.databinding.Observable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.databinding.ActivityBrowseBinding;
import cn.com.lxsoft.bdasphone.databinding.ActivityLoginBinding;
import cn.com.lxsoft.bdasphone.ui.login.LoginViewModel;
import cn.com.lxsoft.bdasphone.utils.StatusBarUtils;
import me.goldze.mvvmhabit.base.BaseActivity;

public class BrowseActivity extends BaseActivity<ActivityBrowseBinding, BrowseActivityViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_browse;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public BrowseActivityViewModel initViewModel() {
        //View持有ViewModel的引用，如果没有特殊业务处理，这个方法可以不重写
        return ViewModelProviders.of(this).get(BrowseActivityViewModel.class);
        //binding.ivSwichPasswrod.setB(viewModel.passwordShowSwitchOnClickCommand);
    }

    @Override
    public void initViewObservable() {

        StatusBarUtils.setBar(this,R.color.colorPrimary,false);
    }

/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.
                        PERMISSION_GRANTED) {
                    、、call();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_
                            SHORT).show();
                }b
                    reak;
            default:
        }
    }
    */
}
