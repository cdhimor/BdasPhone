package cn.com.lxsoft.bdasphone.ui.search;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.DataSession;
import cn.com.lxsoft.bdasphone.entity.Chart;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;

public class LayoutSearchViewModel extends BaseViewModel {

    public int chartID=1;
    public String chartType;



    public LayoutSearchViewModel(@NonNull Application application) {

        super(application);
    }

    @Override
    public void onCreate()
    {
        //
    }

    //用户名输入框焦点改变的回调事件
    public BindingCommand<String> onSearchTextChangeCommand = new BindingCommand<>(new BindingConsumer<String>() {
        @Override
        public void call(String res) {

        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
