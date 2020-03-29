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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.BR;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.BridgeBaseViewModel;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.DataBaseGreenImpl;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.ImageData;
import cn.com.lxsoft.bdasphone.entity.Patrol;
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
import cn.com.lxsoft.bdasphone.utils.FileUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import me.goldze.mvvmhabit.base.AppManager;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.http.NetworkUtil;
import me.goldze.mvvmhabit.utils.ImageUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

import static android.support.constraint.Constraints.TAG;

public class FragmentPatrolViewModel extends BridgeBaseViewModel {
    QiaoLiang bridge;
    public QiaoLiangListItemViewModel qiaoLiangViewModel;
    public ObservePatrol patrolData;
    Patrol oldPatrol;
    public String sBridgeName="";
    public ObservableBoolean obNewData=new ObservableBoolean(false);
    public ObservableBoolean obHistory=new ObservableBoolean(false);

    public class ObservePatrol {
        Patrol patrol;
        public String examineID;


        public ObservePatrol(Patrol p) {
            patrol = p;

        }

        public void initData() {
            dateStr.set(ConvertUtils.getDateName(patrol.getDate()));
            dateNextStr.set(ConvertUtils.getDateName(patrol.getNextDate()));

            patrol.__setDaoSession(AppApplication.dataBase.getDaoSession());
            if (patrol.getWorker() != null)
                workerName.set(patrol.getWorker().getName());
            if (patrol.getOwner() != null)
                ownerName.set(patrol.getOwner().getName());

            parseDisData(patrol.getQiaoLuLianJie(), qiaoLuLianJie);
            parseDisData(patrol.getPuZhuangSSF(), puZhuangSSF);
            parseDisData(patrol.getLanGan(), lanGan);
            parseDisData(patrol.getBiaoZhi(), biaoZhi);
            parseDisData(patrol.getXianXing(), xianXing);

            List<ImageData> ilist = ImageData.parseImageData(patrol.getImages());
            if (ilist != null)
                images.addAll(ilist);
        }

        private void parseDisData(String tpres, ObservableArrayList<String> disList) {
            if (tpres == null || tpres.equals("1") || tpres.isEmpty())
                return;
            disList.addAll(Arrays.asList(tpres.split("║")));
        }

        public ObservableField<String> dateStr = new ObservableField<>();
        public ObservableField<String> dateNextStr = new ObservableField<>();

        public ObservableField<String> ownerName = new ObservableField<>();

        public ObservableField<String> workerName = new ObservableField<>();

        public ObservableArrayList<String> qiaoLuLianJie = new ObservableArrayList<>();//桥路连接处
        public ObservableArrayList<String> puZhuangSSF = new ObservableArrayList<>();//桥面铺装或伸缩缝
        public ObservableArrayList<String> lanGan = new ObservableArrayList<>();//栏杆或护栏
        public ObservableArrayList<String> biaoZhi = new ObservableArrayList<>();//标志标牌
        public ObservableArrayList<String> xianXing = new ObservableArrayList<>();//桥梁线性

        public ObservableArrayList<ImageData> images = new ObservableArrayList<>();

        public Patrol buildPatrol() {
            patrol.setQiaoLuLianJie(qiaoLuLianJie.size() == 0 ? "1" : ConvertUtils.joinStr("║", qiaoLuLianJie));

            patrol.setPuZhuangSSF(puZhuangSSF.size() == 0 ? "1" : ConvertUtils.joinStr("║", puZhuangSSF));

            patrol.setLanGan(lanGan.size() == 0 ? "1" : ConvertUtils.joinStr("║", lanGan));

            patrol.setBiaoZhi(biaoZhi.size() == 0 ? "1" : ConvertUtils.joinStr("║", biaoZhi));

            patrol.setXianXing(xianXing.size() == 0 ? "1" : ConvertUtils.joinStr("║", xianXing));

            patrol.setImages(ImageData.createImageData(images));

            patrol.setBridgeCode(bridge.getDaiMa());

            patrol.setBridgeName(bridge.getMingCheng());

            return patrol;
        }

        public void updateMediaData(){
            patrol.setImages(ImageData.createImageData(images));
        }
    }

    public ObservableArrayList<String> getTypeDiseaseList(String type) {
        switch (type) {
            case "1":
                return patrolData.qiaoLuLianJie;
            case "2":
                return patrolData.puZhuangSSF;
            case "3":
                return patrolData.lanGan;
            case "4":
                return patrolData.biaoZhi;
            case "5":
                return patrolData.xianXing;
        }
        return null;
    }

    public FragmentPatrolViewModel(@NonNull Application application) {
        super(application);
    }

    //给ViewPager添加ObservableList
    public ObservableList<FragmentPatrolItemViewModel> items = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<FragmentPatrolItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_patrol_viewpager_item);

    @Override
    public void onCreate() {
        DataBaseGreenImpl database = AppApplication.dataBase;
        bridge = DataSession.getCurrentQiaoLiang();
        sBridgeName=bridge.getMingCheng();

        Patrol patrolx=null;

        Bundle mBundle=bundleArguments;
        if (mBundle != null) {
            if(mBundle.getString(SystemConfig.Bundle_Examine_Type).equals(SystemConfig.Bundle_Examine_Type_History)){
                PatrolTemp pt=database.getPatrolTempData(mBundle.getString(SystemConfig.Bundle_Examine_Type_History_Data));
                patrolx=new Patrol(pt);
                obHistory.set(true);
            }
            else {
                patrolx = database.getPatrolDataFromBridge(bridge.getDaiMa());
                obNewData.set(true);
                oldPatrol = patrolx;
                if (mBundle.getString(SystemConfig.Bundle_Examine_Type).equals(SystemConfig.Bundle_Examine_Type_CopyNew)) {
                    patrolx = database.getPatrolDataFromBridge(bridge.getDaiMa());
                } else {
                    patrolx = new Patrol();
                    patrolx.setWorker(DataSession.getCurrentUser());
                    List<User> users = database.getLeaderUser();

                    if (users.size() == 1)
                        patrolx.setOwner(users.get(0));
                }
                Date date = new Date();
                patrolx.setExamineID("X" + bridge.getDaiMa().concat("-" + ConvertUtils.getDateSimpleName(date)));
                patrolx.setBridgeCode(bridge.getDaiMa());
                patrolx.setBridgeName(bridge.getMingCheng());
                patrolx.setDate(date);

                int nextDay = 1;
                if (bridge.getExamineLevel() == 3)
                    nextDay = 7;
                Date nextDate = ConvertUtils.getNewDate(nextDay);
                patrolx.setNextDate(nextDate);
            }
        }
        else
            patrolx = database.getPatrolDataFromBridge(bridge.getDaiMa());

        if(patrolx==null)
            return;

        patrolData = new ObservePatrol(patrolx);

        qiaoLiangViewModel = new QiaoLiangListItemViewModel(this, bridge);
        //qiaoLiangViewModel.bCanClick = false;
        qiaoLiangViewModel.setItemClickListener(new QiaoLiangListItemViewModel.OnItemClickListener() {
            @Override
            public void onClick(QiaoLiang tpQiaoLiang) {
                //oStringChooseQiaoLiang.set(tpQiaoLiang.getDaiMa());
                DataSession.setCurrentQiaoLiang(tpQiaoLiang);
                startContainerActivity(ContentFragment.class.getCanonicalName());
            }
        });
    }

    public void initPatrolData() {
        patrolData.initData();
    }

    public void getDiseasData(String type, ArrayList keySet, ArrayList valueSet) {
        HashMap<String, String> reslist = new HashMap<>();
        ObservableArrayList<String> tplist = getTypeDiseaseList(type);
        for (String key : diseaseList.keySet()) {
            if (key.substring(0, 1).equals(type) && !tplist.contains(key)) {
                keySet.add(key);
                valueSet.add(diseaseList.get(key));
            }
        }
    }

    HashMap<String, String> diseaseList = DataDict.getDictMap("5.1");

    public String transDisName(String code) {
        return diseaseList.get(code);
    }

    public void addDiseaseData(String type, ArrayList<String> reslist) {
        ObservableArrayList<String> tplist = getTypeDiseaseList(type);
        for (int i = 0; i < reslist.size(); i++) {
            if (!tplist.contains(reslist.get(i)))
                tplist.add(reslist.get(i));
        }
    }

    public void addDiseaseImages(List<ImageData> tplist) {
        if (tplist.size() > 0)
            patrolData.images.addAll(tplist);
    }

    public void saveData() {
        if(patrolData.patrol.beNewPatrol() || obHistory.get()){
            showConfirmDialog("本次检查已逾期，请新建检查");
            return;
        }
        ObservableInt obSaveState=new ObservableInt(0);

        obSaveState.addOnPropertyChangedCallback(new android.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
                DataBaseGreenImpl database = AppApplication.dataBase;
                switch (obSaveState.get()){
                    case 1:
                        if(oldPatrol!=null) {
                            PatrolTemp patrolTemp=new PatrolTemp(oldPatrol);
                            database.insertOrReplacePatrolTempData(patrolTemp);
                            oldPatrol.delete();
                            oldPatrol = null;
                        }
                        database.insertOrReplacePatrolData(patrolData.buildPatrol());

                        if(NetworkUtil.isNetworkAvailable(AppApplication.getInstance().getBaseContext())){
                            showDialog("本地存储完毕，正在网络提交");
                            ImageData.uploadImageList(subscribeBase,patrolData.images.subList(0,patrolData.images.size()),bridge.getDaiMa(),obSaveState);
                        }
                        else{
                            showDialog("未联网，请稍后重新提交");
                        }
                        break;
                    case 2:
                        patrolData.updateMediaData();
                        RequestBase<Patrol> requestData = new RequestBase<>();
                        if (obNewData.get()) {
                            bridge.setPatrolID(patrolData.patrol.getExamineID());
                            database.insertOrReplaceBridgeData(bridge);
                            requestData.AddItems.add(patrolData.patrol);
                        } else {
                            requestData.EditItems.add(patrolData.patrol);
                        }
                        subscribeBase.setPatrolData(requestData)
                                .subscribe(new BridegeNetObserver<ResponseInfo>(){
                                    @Override
                                    public void onNext(ResponseInfo res) {
                                        if(res.checkSuccess()) {
                                            database.insertOrReplacePatrolData(patrolData.patrol);
                                            showConfirmDialog("数据提交成功");
                                            obNewData.set(false);
                                        }else
                                            showConfirmDialog(res.message);
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
                        showConfirmDialog("网络错误，请稍后重新提交");
                        break;
                }
            }
        });

        showDialog("正在提交数据");

        ActivityUtils.checkPositionData(AppManager.getAppManager().currentActivity(),bridge.getLat(),bridge.getLng(),bridge.getQiaoChang(),obSaveState);
        //obSaveState.set(1);

        //subscribeBase.setPatrolData(requestData);

    }

    public BindingCommand createNewClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(patrolData.patrol.getDate().getDate()==new Date().getDate()) {
                showConfirmDialog("当日不能添加新检查，请修改本检查");
                return;
            }
            //if(ConvertUtils.patrolData.patrol.getDate().)
            Bundle bundle=new Bundle();
            bundle.putString(SystemConfig.Bundle_Examine_Type,SystemConfig.Bundle_Examine_Type_New);
            startContainerActivity(FragmentPatrol.class.getCanonicalName(),bundle);
            finish();
        }
    });


    public BindingCommand createCopyNewClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(patrolData.patrol.getDate().getDate()==new Date().getDate()) {
                showConfirmDialog("当日不能添加新检查，请修改本检查");
                return;
            }
            Bundle bundle=new Bundle();
            bundle.putString(SystemConfig.Bundle_Examine_Type,SystemConfig.Bundle_Examine_Type_CopyNew);
            startContainerActivity(FragmentPatrol.class.getCanonicalName(),bundle);
            finish();
        }
    });

    SingleLiveEvent<Void> openHisDialogEvent=new SingleLiveEvent();
    ArrayList hisKeySet;
    ArrayList hisValueSet;

    public void getHistoryData(){
        Observable<ResponseList<PatrolTemp>> dbob=Observable.create(new ObservableOnSubscribe<ResponseList<PatrolTemp>>() {
            @Override
            public void subscribe(ObservableEmitter<ResponseList<PatrolTemp>> emitter) throws Exception{
                List tplist=AppApplication.dataBase.getPatrolTempList(bridge.getDaiMa(),0,0);
                if(tplist==null || tplist.size()==0)
                    emitter.onComplete();
                emitter.onNext(new ResponseList<PatrolTemp>(tplist,"db"));
                emitter.onComplete();
            }
        });

        Observable<ResponseList<PatrolTemp>> netob=subscribeBase.getPatrolTempListData(bridge.getDaiMa(),1,SystemConfig.PageSizeBridge);

        Observable.concat(dbob,netob).firstElement()
                .subscribe(new BridegeNetObserver<ResponseList<PatrolTemp>>() {
                    @Override
                    public void onSuccess(ResponseList<PatrolTemp> resData) {
                        List<PatrolTemp> datalist=resData.rows;
                        if(datalist==null || datalist.size()==0)
                            return;
                        if(resData.CODE==null || !resData.CODE.equals("db"))
                            AppApplication.dataBase.insertPatrolTempListData(datalist);
                        hisKeySet=new ArrayList();
                        hisValueSet=new ArrayList();
                        for(int i=0;i<datalist.size();i++){
                            hisKeySet.add(datalist.get(i).getExamineID());
                            String tps=datalist.get(i).getDiseaseInfo();
                            if(tps.isEmpty())
                                tps="未发现异常";
                            hisValueSet.add(ConvertUtils.getDateNameNoYear(datalist.get(i).getDate())+"     ("+tps+")");
                        }
                        openHisDialogEvent.call();
                    }
            });
    }

    public void openHistoryActivity(String examineid){
        Bundle bundle=new Bundle();
        bundle.putString(SystemConfig.Bundle_Examine_Type,SystemConfig.Bundle_Examine_Type_History);
        bundle.putString(SystemConfig.Bundle_Examine_Type_History_Data,examineid);
        startContainerActivity(FragmentPatrol.class.getCanonicalName(),bundle);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
