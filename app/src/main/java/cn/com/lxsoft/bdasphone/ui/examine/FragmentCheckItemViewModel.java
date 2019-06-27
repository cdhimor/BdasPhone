package cn.com.lxsoft.bdasphone.ui.examine;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.net.Uri;
import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by goldze on 2018/7/18.
 */

public class FragmentCheckItemViewModel extends ItemViewModel {
    public String name;
    public SingleLiveEvent<String> clickEvent = new SingleLiveEvent();

    public ObservableBoolean bCheckDisease=new ObservableBoolean(true);
    public ObservableArrayList<Uri> oALDiseasePicList=new ObservableArrayList<>();

    public FragmentCheckItemViewModel(@NonNull BaseViewModel viewModel, String text) {
        super(viewModel);
        this.name = text;
    }

    public BindingCommand onAddDiseaseClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //点击之后将逻辑转到adapter中处理
            clickEvent.setValue(name);
        }
    });


}
