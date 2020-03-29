package cn.com.lxsoft.bdasphone.ui.engineer;

import android.app.Application;
import android.support.annotation.NonNull;

import cn.com.lxsoft.bdasphone.app.BridgeBaseViewModel;
import cn.com.lxsoft.bdasphone.entity.Engineer;

public class FragmentEngineerViewModel extends BridgeBaseViewModel {
    public Engineer engineer;

    public FragmentEngineerViewModel(@NonNull Application application) {

        super(application);

    }


    @Override
    public void onCreate()
    {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
