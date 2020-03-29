package cn.com.lxsoft.bdasphone.ui.examine;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.BridgeBaseViewModel;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.DataBaseGreenImpl;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.entity.Check;
import cn.com.lxsoft.bdasphone.entity.CheckTemp;
import cn.com.lxsoft.bdasphone.entity.ImageData;
import cn.com.lxsoft.bdasphone.entity.PatrolTemp;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.net.BridegeNetObserver;
import cn.com.lxsoft.bdasphone.net.RequestBase;
import cn.com.lxsoft.bdasphone.net.ResponseInfo;
import cn.com.lxsoft.bdasphone.net.ResponseList;
import cn.com.lxsoft.bdasphone.ui.browse.ContentFragment;
import cn.com.lxsoft.bdasphone.ui.browse.QiaoLiangListItemViewModel;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import cn.com.lxsoft.bdasphone.utils.ConvertUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class FragmentCheckViewModel extends BridgeBaseViewModel {
    QiaoLiang bridge;
    Check oldCheck;
    public String sBridgeName="";

    public QiaoLiangListItemViewModel qiaoLiangViewModel;

    public HashMap<String,ObservableField<String>> checkDisease=new HashMap();
    public HashMap<String,List<ImageData>> checkImage=new HashMap();
    public HashMap<String,ObservableField<String>> checkComm=new HashMap();
    public ObservableBoolean obNewData=new ObservableBoolean(false);
    public ObservableBoolean obHistory=new ObservableBoolean(false);

    public Check check;

    public FragmentCheckViewModel(@NonNull Application application) {

        super(application);
    }

    @Override
    public void onCreate() {
        DataBaseGreenImpl database = AppApplication.dataBase;
        bridge = DataSession.getCurrentQiaoLiang();
        sBridgeName=bridge.getMingCheng();


        Bundle mBundle=bundleArguments;
        if (mBundle != null) {
            if(mBundle.getString(SystemConfig.Bundle_Examine_Type).equals(SystemConfig.Bundle_Examine_Type_History)){
                CheckTemp pt=database.getCheckTempData(mBundle.getString(SystemConfig.Bundle_Examine_Type_History_Data));
                check=new Check(pt);
                obHistory.set(true);
            }
            else {
                check = database.getCheckDataFromBridge(bridge.getDaiMa());
                obNewData.set(true);
                oldCheck = check;
                if (mBundle.getString(SystemConfig.Bundle_Examine_Type).equals(SystemConfig.Bundle_Examine_Type_CopyNew)) {
                    check = database.getCheckDataFromBridge(bridge.getDaiMa());
                } else {
                    check = new Check();
                    check.setWorker(DataSession.getCurrentUser());
                    List<User> users = database.getLeaderUser();

                    if (users.size() == 1)
                        check.setOwner(users.get(0));
                }
                Date date = new Date();
                check.setExamineID("C"+bridge.getDaiMa().concat("-"+ConvertUtils.getDateSimpleName(date)));
                check.setBridgeID(bridge.getDaiMa());
                check.setBridgeName(bridge.getMingCheng());
                check.setDate(date);

                int nextDay = 1;
                if (bridge.getExamineLevel() == 3)
                    nextDay = 7;
                Date nextDate = ConvertUtils.getNewDate(nextDay);
                check.setNextDate(nextDate);
            }
        }
        else
            check = database.getCheckDataFromBridge(bridge.getDaiMa());

        if(check==null)
            return;

        qiaoLiangViewModel = new QiaoLiangListItemViewModel(this, bridge);
        //qiaoLiangViewModel.bCanClick = false;
        qiaoLiangViewModel.setItemClickListener(new QiaoLiangListItemViewModel.OnItemClickListener() {
            @Override
            public void onClick(QiaoLiang tpQiaoLiang) {
                //oStringChooseQiaoLiang.set(tpQiaoLiang.getDaiMa());
                //DataSession.setCurrentQiaoLiang(tpQiaoLiang);
                startContainerActivity(ContentFragment.class.getCanonicalName());
            }
        });

        adapter.fragmentCheckViewModel=this;
    }

    public void initCheckData(){
        String wk="",ow="";
        check.__setDaoSession(AppApplication.dataBase.getDaoSession());
        if(check.getWorker()!=null)
            wk=check.getWorker().getName();
        if(check.getOwner()!=null)
            ow=check.getOwner().getName();

        addCommPagerData(wk,ow,check.getDate(),check.getNextDate());
        addDiseasePagerData("桥梁结构",check.getQiaoLiangJieGou(),check.getQiaoLiangJieGouImg(),"5.21");
        addDiseasePagerData("桥梁外观",check.getQiaoLiangWaiGuan(),check.getQiaoLiangWaiGuanImg(),"5.22");
        addDiseasePagerData("主梁",check.getZhuLiang(),check.getZhuLiangImg(),"5.23");
        addDiseasePagerData("斜拉索/杆",check.getXieLaSuo(),check.getXieLaSuoImg(),"5.24");
        addDiseasePagerData("桥面铺装",check.getQiaoMianPuZhuang(),check.getQiaoMianPuZhuangImg(),"5.25");
        addDiseasePagerData("伸缩缝",check.getShenSuoFeng(),check.getShenSuoFengImg(),"5.26");
        addDiseasePagerData("人行道",check.getRenXingDao(),check.getRenXingDaoImg(),"5.27");
        addDiseasePagerData("栏杆护栏",check.getLanGanHuLan(),check.getLanGanHuLanImg(),"5.28");
        addDiseasePagerData("排水设施",check.getPaiShuiSheShi(),check.getPaiShuiSheShiImg(),"5.29");
        addDiseasePagerData("墩台",check.getDunTai(),check.getDunTaiImg(),"5.210");
        addDiseasePagerData("翼墙护坡",check.getYiQiangHuPo(),check.getYiQiangHuPoImg(),"5.211");
        addDiseasePagerData("交通设施",check.getJiaoTongSheShi(),check.getJiaoTongSheShiImg(),"5.212");
        addDiseasePagerData("观测点",check.getGuanCeDian(),check.getGuanCeDianImg(),"5.213");
    }

    private void addDiseasePagerData(String name,String data,String img,String dict){
        if(data==null)
            data="1";
        String di=data;
        List<ImageData> iml=ImageData.parseImageData(img);
        ObservableField<String> tpdi=new ObservableField<String>(di);
        checkDisease.put(name,tpdi);

        iml=(iml==null)?new ArrayList<ImageData>():iml;
        checkImage.put(name,iml);
        FragmentCheckItemViewModel itemViewModel = new FragmentCheckItemViewModel(this,name,tpdi,iml,dict);
        viewModelsItems.add(itemViewModel);
        itemViewModel.checkViewModel=this;
    }

    private void addCommPagerData(String wk,String ow,Date dt,Date dtn){
        FragmentCheckItemViewModel itemViewModel = new FragmentCheckItemViewModel(this,wk,ow,ConvertUtils.getDateName(dt),ConvertUtils.getDateName(dtn));
        viewModelsItems.add(itemViewModel);
    }

    protected void dealCheckData(){
        check.setQiaoLiangJieGou(checkDisease.get("桥梁结构").get());
        check.setQiaoLiangJieGouImg(ImageData.createImageData(checkImage.get("桥梁结构")));
        check.setQiaoLiangWaiGuan(checkDisease.get("桥梁外观").get());
        check.setQiaoLiangWaiGuanImg(ImageData.createImageData(checkImage.get("桥梁外观")));
        check.setZhuLiang(checkDisease.get("主梁").get());
        check.setZhuLiangImg(ImageData.createImageData(checkImage.get("主梁")));
        check.setXieLaSuo(checkDisease.get("斜拉索/杆").get());
        check.setXieLaSuoImg(ImageData.createImageData(checkImage.get("斜拉索/杆")));
        check.setQiaoMianPuZhuang(checkDisease.get("桥面铺装").get());
        check.setQiaoMianPuZhuangImg(ImageData.createImageData(checkImage.get("桥面铺装")));
        check.setShenSuoFeng(checkDisease.get("伸缩缝").get());
        check.setShenSuoFengImg(ImageData.createImageData(checkImage.get("伸缩缝")));
        check.setRenXingDao(checkDisease.get("人行道").get());
        check.setRenXingDaoImg(ImageData.createImageData(checkImage.get("人行道")));
        check.setLanGanHuLan(checkDisease.get("栏杆护栏").get());
        check.setLanGanHuLanImg(ImageData.createImageData(checkImage.get("栏杆护栏")));
        check.setPaiShuiSheShi(checkDisease.get("排水设施").get());
        check.setPaiShuiSheShiImg(ImageData.createImageData(checkImage.get("排水设施")));
        check.setDunTai(checkDisease.get("墩台").get());
        check.setDunTaiImg(ImageData.createImageData(checkImage.get("墩台")));
        check.setYiQiangHuPo(checkDisease.get("翼墙护坡").get());
        check.setYiQiangHuPoImg(ImageData.createImageData(checkImage.get("翼墙护坡")));
        check.setJiaoTongSheShi(checkDisease.get("交通设施").get());
        check.setJiaoTongSheShiImg(ImageData.createImageData(checkImage.get("交通设施")));
        check.setGuanCeDian(checkDisease.get("观测点").get());
        check.setGuanCeDianImg(ImageData.createImageData(checkImage.get("观测点")));

        check.setBHistory(false);

    }

    public void saveData(){
        if(check.beNewCheck() || obHistory.get()){
            showConfirmDialog("本次检查已逾期，请新建检查");
            return;
        }
        //ObservableBoolean bUploadOK=new ObservableBoolean(false);
        //0:初始化，1：位置校验成功，100:位置错误，2：图片上传成功，200：网络错误。3:提交成功，300：网络错误
        ObservableInt obSaveState=new ObservableInt(0);


        obSaveState.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
                DataBaseGreenImpl database = AppApplication.dataBase;
                switch(obSaveState.get()){
                    case 1:
                        dealCheckData();
                        if(oldCheck!=null) {
                            CheckTemp checkTemp=new CheckTemp(oldCheck);
                            database.insertOrReplaceCheckTempData(checkTemp);
                            oldCheck.delete();
                            oldCheck = null;
                        }
                        database.insertOrReplaceCheckData(check);
                        if(obNewData.get()) {
                            bridge.setCheckID(check.getExamineID());
                            database.insertOrReplaceBridgeData(DataSession.getCurrentQiaoLiang());
                        }
                        showDialog("本地存储完毕，正在网络提交");

                        List imgList=new ArrayList();
                        for(String imgkey:checkImage.keySet()){
                            imgList.addAll(checkImage.get(imgkey));
                        }
                        ImageData.uploadImageList(subscribeBase,imgList,bridge.getDaiMa(),obSaveState);
                        break;
                    case 2:
                        RequestBase<Check> requestData = new RequestBase<>();
                        if (obNewData.get())
                            requestData.AddItems.add(check);
                        else
                            requestData.EditItems.add(check);

                        subscribeBase.setCheckData(requestData).subscribe(new BridegeNetObserver<ResponseInfo>() {
                            @Override
                            public void onNext(ResponseInfo res) {
                                if (res.checkSuccess()) {
                                    showConfirmDialog("数据提交成功");
                                    dealCheckData();
                                    database.insertOrReplaceCheckData(check);
                                    obNewData.set(false);
                                } else
                                    showConfirmDialog(res.message+"请稍后同步");
                            }

                            @Override
                            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                super.onError(e);
                                obSaveState.set(300);
                            }
                        });
                        break;
                    case 100:
                        showConfirmDialog("位置错误，不能保存数据");
                        break;
                    case 200:
                    case 300:
                        showConfirmDialog("网络错误，请稍后同步提交");
                        break;
                }
            }
        });

        showDialog("正在保存数据");

        ActivityUtils.checkPositionData(AppManager.getAppManager().currentActivity(),bridge.getLat(),bridge.getLng(),bridge.getQiaoChang(),obSaveState);
        //obSaveState.set(1);

    }

    public void dealNetData(String tpclass,Object res) {
        if (tpclass == null)
            return;
        if (tpclass.equals("set_check_data")) {
            ResponseInfo netRes = (ResponseInfo) res;
            if (netRes.state.equals("success")) {
                    ToastUtils.showShort("数据录入成功");
            } else
                ToastUtils.showShort(netRes.message);
        }
        else if(tpclass.equals("upload_patrol")){
        }
    }

    public ObservableList<FragmentCheckItemViewModel> viewModelsItems = new ObservableArrayList<>();
    public ItemBinding<FragmentCheckItemViewModel> itemBinding = ItemBinding.of(new OnItemBind<FragmentCheckItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, FragmentCheckItemViewModel viewModel) {
            //通过item的类型, 动态设置Item加载的布局
            if (position==0) {
                itemBinding.set(BR.viewModel, R.layout.layout_check_viewpager_comm_item);
            } else
                itemBinding.set(BR.viewModel, R.layout.layout_check_viewpager_item);
        }
    });

    public final FragmentCheckBindingAdapter adapter = new FragmentCheckBindingAdapter();
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer index) {

            //ToastUtils.showShort("ViewPager切换：" + index);
            //currentIndex=index;
        }
    });
    public final BindingViewPagerAdapter.PageTitles<FragmentCheckItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<FragmentCheckItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, FragmentCheckItemViewModel item) {
            return "条目" + position;
        }
    };

    public String getPageTitle(int position){
        return viewModelsItems.get(position).name;
    }

    public int getPageTitleBadgeColor(int position){
        int res=0;
        ObservableField<String> obj=viewModelsItems.get(position).disease;
        if(obj==null)
            return 0;
        switch (obj.get()){
            case "1":
            case "":
                res=0;
                break;
            case "2":
                res= SystemConfig.getPingJiColor(2);
                break;
            case "3":
                res= SystemConfig.getPingJiColor(5);
                break;
        }
        return res;
    }

    public BindingCommand createNewClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(check.getDate().getDate()==new Date().getDate()) {
                showConfirmDialog("当日不能添加新检查，请修改本检查");
                return;
            }
            //if(ConvertUtils.patrolData.patrol.getDate().)
            Bundle bundle=new Bundle();
            bundle.putString(SystemConfig.Bundle_Examine_Type,SystemConfig.Bundle_Examine_Type_New);
            startContainerActivity(FragmentCheck.class.getCanonicalName(),bundle);
            finish();
        }
    });


    public BindingCommand createCopyNewClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(check.getDate().getDate()==new Date().getDate()) {
                showConfirmDialog("当日不能添加新检查，请修改本检查");
                return;
            }
            Bundle bundle=new Bundle();
            bundle.putString(SystemConfig.Bundle_Examine_Type,SystemConfig.Bundle_Examine_Type_CopyNew);
            startContainerActivity(FragmentCheck.class.getCanonicalName(),bundle);
            finish();
        }
    });

    SingleLiveEvent<Void> openHisDialogEvent=new SingleLiveEvent();
    ArrayList hisKeySet;
    ArrayList hisValueSet;

    public void getHistoryData(){
        Observable<ResponseList<CheckTemp>> dbob=Observable.create(new ObservableOnSubscribe<ResponseList<CheckTemp>>() {
            @Override
            public void subscribe(ObservableEmitter<ResponseList<CheckTemp>> emitter) throws Exception{
                List tplist=AppApplication.dataBase.getCheckTempList(bridge.getDaiMa(),0,0);
                if(tplist==null || tplist.size()==0)
                    emitter.onComplete();
                emitter.onNext(new ResponseList<CheckTemp>(tplist,"db"));
                emitter.onComplete();
            }
        });

        Observable<ResponseList<CheckTemp>> netob=subscribeBase.getCheckTempListData(bridge.getDaiMa(),1,SystemConfig.PageSizeBridge);

        Observable.concat(dbob,netob).firstElement()
                .subscribe(new BridegeNetObserver<ResponseList<CheckTemp>>() {
                    @Override
                    public void onSuccess(ResponseList<CheckTemp> resData) {
                        List<CheckTemp> datalist=resData.rows;
                        if(datalist==null || datalist.size()==0)
                            return;
                        if(resData.CODE==null || !resData.CODE.equals("db"))
                            AppApplication.dataBase.insertCheckTempListData(datalist);
                        hisKeySet=new ArrayList();
                        hisValueSet=new ArrayList();
                        for(int i=0;i<datalist.size();i++){
                            hisKeySet.add(datalist.get(i).getExamineID());
                            String tps=datalist.get(i).getDiseaseInfo();
                            if(tps.isEmpty())
                                tps="未发现异常";
                            hisValueSet.add(ConvertUtils.getDateNameNoYear(datalist.get(i).getDate())+"  ("+tps+")");
                        }
                        openHisDialogEvent.call();
                    }
                });
    }

    public void openHistoryActivity(String examineid){
        Bundle bundle=new Bundle();
        bundle.putString(SystemConfig.Bundle_Examine_Type,SystemConfig.Bundle_Examine_Type_History);
        bundle.putString(SystemConfig.Bundle_Examine_Type_History_Data,examineid);
        startContainerActivity(FragmentCheck.class.getCanonicalName(),bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
