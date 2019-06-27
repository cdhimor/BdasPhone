package cn.com.lxsoft.bdasphone.ui.examine;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.DiseaseInfo;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by goldze on 2018/7/18.
 */

public class FragmentTestItemViewModel extends ItemViewModel {
    protected Construct construct;
    protected List<Disease> diseaseList=new ArrayList<>();
    //public Map<String,List<DiseaseInfo>> diseaseInfoMap;
    public SingleLiveEvent<String> clickEvent = new SingleLiveEvent();

    public ObservableBoolean bCheckDisease=new ObservableBoolean(true);
    public ObservableArrayList<Uri> oALDiseasePicList=new ObservableArrayList<>();

    public FragmentTestItemViewModel(@NonNull BaseViewModel viewModel, Construct construct) {
        super(viewModel);
        this.construct = construct;
    }

    public String[] getGouJianList(){
        return construct.getGouJian().split(",");
    }

    public void setDiseaseList(List<Disease> dList){
        diseaseList=dList;
    }

    public List<Disease> getDiseaseList(){
        return diseaseList;
    }


    public Disease addDisease(){
        Disease disease=new Disease();
        diseaseList.add(disease);
        return disease;
    }

    public Construct getConstruct(){
        return construct;
    }

    public void insertDiseaseItem(String gouJian,DiseaseInfo diseaseInfo){
        if(diseaseList==null)
            diseaseList=new ArrayList<>();
        /*
        else {
            for (int i = 0; i < diseaseList.size(); i++) {
                if(diseaseList.get(i).getGouJian().equals(gouJian))
                    diseaseList.remove(i);
            }
        }
        */

        Disease disease = new Disease();
        disease.setGouJian(gouJian);
        disease.setBingHai(diseaseInfo.getBianMa());
        diseaseList.add(disease);
    }

    public List<Disease> buildDisease()
    {
        for(int i=0;i<diseaseList.size();i++) {
            Disease disease = diseaseList.get(i);
            disease.setBridgeID(construct.getBridgeID());
            disease.setBuJian(construct.getBuJian());
            disease.setCaiZhi(construct.getCaiZhi());
        }
        return diseaseList;
    }

    BindingCommand onAddDiseaseClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //点击之后将逻辑转到adapter中处理
            //clickEvent.setValue(name);
        }
    });


}
