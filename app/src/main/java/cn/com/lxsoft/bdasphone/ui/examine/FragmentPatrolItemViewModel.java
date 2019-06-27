package cn.com.lxsoft.bdasphone.ui.examine;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by goldze on 2018/7/18.
 */

public class FragmentPatrolItemViewModel extends ItemViewModel {
    public String name;
    private String diseaseInfo;
    public SingleLiveEvent<String> clickEvent = new SingleLiveEvent();

    public ObservableBoolean bCheckDisease=new ObservableBoolean(true);
    public ObservableArrayList<String> oALDiseaseList=new ObservableArrayList<>();

    public FragmentPatrolItemViewModel(@NonNull BaseViewModel viewModel, String text) {
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

    public String getDiseaseInfo(){
        String res="";
        if(bCheckDisease.get())
        {
            res= "A";
        }
        else
        {
            for(int i=0;i<oALDiseaseList.size();i++)
            {
                res=res.concat(":"+oALDiseaseList.get(i));
            }
            res=res.substring(1);
        }
        return res;
    }

    public void setDiseaseInfo(String res){
        if(!res.equals("A"))
        {
            String[] tplist=res.split(":");
            for(int i=0;i<tplist.length;i++)
            {
                if(!tplist[i].equals(""))
                    oALDiseaseList.add(tplist[i]);
            }
            bCheckDisease.set(false);
        }
    }



}
