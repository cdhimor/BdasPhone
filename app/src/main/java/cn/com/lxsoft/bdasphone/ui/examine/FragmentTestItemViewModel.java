package cn.com.lxsoft.bdasphone.ui.examine;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.DiseaseInfo;
import cn.com.lxsoft.bdasphone.entity.ImageData;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.entity.YearTest;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by goldze on 2018/7/18.
 */

public class FragmentTestItemViewModel extends ItemViewModel {
    public int viewModelType=3; //1:commpage 2:danyuanpage 3:diseasepage
    protected Construct construct;
    protected int position;
    protected List<Disease> diseaseList=new ArrayList<>();
    //public Map<String,List<DiseaseInfo>> diseaseInfoMap;
    //public SingleLiveEvent<String> clickEvent = new SingleLiveEvent();
    //public YearTest yearTest;
    public String testDateInfo="";
    public String ownerName;
    public String workerName;
    public String bjName;

    public String pingJi;
    public String pingFen;
    public String upFen;
    public String dwFen;
    public String sfFen;

    public ObservableField<String> bjFen=new ObservableField<>();

    public String dYPingFen="";
    public String dYUpFen="";
    public String dYDwFen="";
    public String dYSfFen="";


    //public ObservableArrayList<Uri> oALDiseasePicList=new ObservableArrayList<>();

    public interface OnDiseaseChange {
        // 回调方法
        void onChange(int pos,List<Disease> list);
    }
    OnDiseaseChange mOnDiseaseChange;

    public FragmentTestItemViewModel(@NonNull BaseViewModel viewModel, Construct construct,int pos,int vmType) {
        super(viewModel);
        viewModelType=vmType;
        if(vmType==3) {
            this.construct = construct;
            bjFen.set("未评分");
            bjName = DataDict.getDict("6.2", construct.getBuJian());
        }
        position=pos;
    }

    String[] gouJianList;
    public String[] getGouJianList(){
        if(gouJianList==null)
            gouJianList=construct.getGouJian().split(",");
        return gouJianList;
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
        if(mOnDiseaseChange!=null)
            mOnDiseaseChange.onChange(position,diseaseList);
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
                if(disease.checkEmpty()) {
                    diseaseList.remove(i);
                    i -= 1;
                }
            //disease.setBridgeID(construct.getBridgeID());
            disease.setBuJian(construct.getBuJian());
            disease.setCaiZhi(construct.getCaiZhi());
            disease.setXuHao(construct.getXuHao());
            disease.setImages(ImageData.createImageData(disease.getImageList()));
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

    public void setYearTest(YearTest yt)
    {
        //yearTest=yt;
        yt.__setDaoSession(AppApplication.dataBase.getDaoSession());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        testDateInfo=sdf.format(yt.getDate());

        User worker=yt.getWorker();
        workerName=worker==null?"":((User) worker).getName();
        User owner=yt.getOwner();
        ownerName=owner==null?"":((User) owner).getName();

        pingFen=(yt.getPingFen()<0)?"未评价":String.valueOf(yt.getPingFen());
        pingJi=(yt.getPingJia()<0)?"未评价":String.valueOf(yt.getPingJia()+"类");
        upFen=(yt.getShangBuJieGouPingFen()<0)?"未评价":String.valueOf(yt.getShangBuJieGouPingFen());
        dwFen=(yt.getXiaBuJieGouPingFen()<0)?"未评价":String.valueOf(yt.getXiaBuJieGouPingFen());
        sfFen=(yt.getQiaoMianXiPingFen()<0)?"未评价":String.valueOf(yt.getQiaoMianXiPingFen());
    }

    public void deleteDisease(Disease disease){
        diseaseList.remove(disease);
        mOnDiseaseChange.onChange(position,diseaseList);
    }

    public void getSRDisease(){

    }

    /*
    public Disease curAddImageDisease;

    public ObservableBoolean obAddImage=new ObservableBoolean(false);
    public void setCurAddImageDisease(Disease disease){
        curAddImageDisease=disease;
    }
    public void addDiseaseImages(List<Uri> uris){
        curAddImageDisease.addIamges(uris);
        obAddImage.set(true);
    }
    */

    public void setDiseaseChangeListener(OnDiseaseChange onListener ){
        mOnDiseaseChange=onListener;
    }


}
