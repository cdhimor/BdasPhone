package cn.com.lxsoft.bdasphone.ui.main;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;

import java.util.concurrent.TimeUnit;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataSession;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseActivity;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragment;
import cn.com.lxsoft.bdasphone.ui.browse.GISFragment;
import cn.com.lxsoft.bdasphone.ui.chart.FragmentChart;
import cn.com.lxsoft.bdasphone.ui.chart.LayoutChartViewModel;
import cn.com.lxsoft.bdasphone.ui.examine.ExamineBrowseFragment;
import cn.com.lxsoft.bdasphone.ui.examine.FragmentCheck;
import cn.com.lxsoft.bdasphone.ui.examine.FragmentPatrol;
import cn.com.lxsoft.bdasphone.ui.examine.FragmentTest;
import cn.com.lxsoft.bdasphone.ui.examine.FragmentTestViewModel;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Created by goldze on 2017/7/17.
 */

public class MainViewModel extends BaseViewModel {
    User user;
    DanWei danWei;
    public String userDanWeiInfo="";
    public int nActivityPosition=0;

    public LayoutChartViewModel layoutChartViewModel;

    public MainViewModel(@NonNull Application application) {
        super(application);
        layoutChartViewModel=new LayoutChartViewModel(application);
    }

    @Override
    public void onCreate() {
        user= DataSession.getCurrentUser();
        danWei=DataSession.getCurrentDanWei();
        userDanWeiInfo=danWei.getMingCheng()+"　　"+user.getName();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

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

    public BindingCommand<MenuItem> onNavigationItemSelected = new BindingCommand<>(new BindingConsumer<MenuItem>() {
        @Override
        public void call(MenuItem item) {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.navigation_browse:
                    startActivity(BrowseActivity.class);
                    break;
            }
        }
    });
}
