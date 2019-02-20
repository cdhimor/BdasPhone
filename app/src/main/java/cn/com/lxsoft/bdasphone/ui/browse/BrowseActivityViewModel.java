package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.Application;
import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class BrowseActivityViewModel extends BaseViewModel {

    public BrowseActivityViewModel(@NonNull Application application){
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        startContainerActivity(BrowseFragment.class.getCanonicalName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
