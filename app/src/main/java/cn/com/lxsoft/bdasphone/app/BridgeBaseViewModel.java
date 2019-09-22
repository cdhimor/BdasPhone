package cn.com.lxsoft.bdasphone.app;

import android.app.Application;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.net.HttpApi;
import cn.com.lxsoft.bdasphone.net.ResponseBase;
import cn.com.lxsoft.bdasphone.net.SubscribeBase;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragment;
import cn.com.lxsoft.bdasphone.ui.chart.FragmentChart;
import cn.com.lxsoft.bdasphone.ui.main.MainActivity;
import cn.com.lxsoft.bdasphone.utils.RetrofitClient;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;


public class BridgeBaseViewModel extends BaseViewModel {
    public int nActivityPosition=0;
    public SubscribeBase subscribeBase;

    public BridgeBaseViewModel(@NonNull Application application) {
        super(application);
        subscribeBase=new SubscribeBase(this);
    }

    private long mExitTime;

    public void closeAPP(){
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            //大于2000ms则认为是误操作，使用Toast进行提示
            ToastUtils.showShort("再按一次退出程序");
            //并记录下本次点击“返回键”的时刻，以便下次进行判断
            mExitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().finishActivity();
            System.exit(0);
        }
    }

    public BindingCommand<MenuItem> onNavigationItemSelected = new BindingCommand<>(new BindingConsumer<MenuItem>() {
        @Override
        public void call(MenuItem item) {
            int itemId = item.getItemId();
            if(itemId==R.id.navigation_exit) {
                closeAPP();
                //item.setChecked(false);
                return;
            }
            if(item.isChecked())
                return;
            switch (itemId) {
                case R.id.navigation_browse:
                    startContainerActivity(BrowseFragment.class.getCanonicalName());
                    break;
                case R.id.navigation_home:
                    startActivity(MainActivity.class);
                    break;
                case R.id.navigation_chart:
                    startContainerActivity(FragmentChart.class.getCanonicalName());
                    break;
            }
        }
    });


    public void dealNetData(String tpclass,Object res){

    }

}
