package cn.com.lxsoft.bdasphone.ui.examine;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.BridgeBaseViewModel;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.DataBaseGreenImpl;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.entity.BridgeEvaluation;
import cn.com.lxsoft.bdasphone.entity.BridgeMaintain;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.entity.YearTest;
import cn.com.lxsoft.bdasphone.net.BridegeNetObserver;
import cn.com.lxsoft.bdasphone.net.ResponseList;
import cn.com.lxsoft.bdasphone.ui.browse.QiaoLiangListItemViewModel;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class FragmentTestViewModel extends BridgeBaseViewModel {
    QiaoLiang bridge;
    YearTest yearTest;

    class UnitData{
        public String code;
        public String name;
        public FragmentTestItemViewModel viewModel;
        public int position;
    }
    ArrayList<UnitData> unitList;
    ArrayList<Integer> unitPosList=new ArrayList<>();
    //HashMap<String,List<Disease>> mapDisease=new HashMap<>();

    List<String> titles;
    List<Construct> constructs;
    List<Disease> diseaseList;
    List<ObservableInt> listBadgeNum=new ArrayList<>();
    List<ObservableInt> listBadgeColor=new ArrayList<>();


    public QiaoLiangListItemViewModel qiaoLiangViewModel;

    public FragmentTestViewModel(@NonNull Application application) {

        super(application);

        qiaoLiangViewModel=new QiaoLiangListItemViewModel(this,DataSession.getCurrentQiaoLiang());
        qiaoLiangViewModel.bCanClick=false;
    }

    Boolean bGetCt=false,bGetDs=false;
    ObservableBoolean bInitDataOK=new ObservableBoolean(false);
    @Override
    public void onCreate() {
        bridge=DataSession.getCurrentQiaoLiang();
        DataBaseGreenImpl database = AppApplication.dataBase;
        yearTest=database.getYearTestDataFromBridge(bridge.getDaiMa());
        String[] ctList=bridge.getJieGou().split(",");
        if(ctList.length>0) {
            unitList=new ArrayList<>();
            for (int i = 0; i < ctList.length; i++) {
                UnitData ud=new UnitData();
                ud.code=ctList[i];
                ud.name=DataDict.getDict("1.2",ctList[i]);
                unitList.add(ud);
            }
        }

        if(yearTest!=null)
        {
            constructs=database.getConstructData(bridge.getDaiMa());

            diseaseList=database.getDiseaseData(yearTest.getExamineID());

            if(constructs==null || constructs.size()==0){
                subscribeBase.getConstructData(bridge.getDaiMa())
                    .subscribe(new BridegeNetObserver<ResponseList<Construct>>(){
                        @Override
                        public void onNext(ResponseList<Construct> resData) {
                            List<Construct> tplist = resData.DATA;
                            database.insertConstructData(tplist);
                            constructs=tplist;
                            bGetCt=true;
                            if(bGetCt && bGetDs)
                                initData();
                        }
                    });
            }
            else
                bGetCt=true;

            //if(diseaseList==null || diseaseList.size()==0){
                subscribeBase.getDiseaseData(yearTest.getExamineID())
                        .subscribe(new BridegeNetObserver<ResponseList<Disease>>(){
                            @Override
                            public void onNext(ResponseList<Disease> resData) {
                                List<Disease> tplist = resData.DATA;
                                database.insertDiseaseData(tplist);
                                diseaseList=tplist;
                                bGetDs=true;
                                if(bGetCt && bGetDs)
                                    initData();
                            }
                        });
            //}
            //else
            //    bGetDs=true;

            //if(bGetCt && bGetDs)
            //    initData();
        }
        else {
            yearTest = new YearTest();
            yearTest.setWorkerID(DataSession.getCurrentUser().getLoginName());
            List<User> users=database.getLeaderUser();

            if(users.size()==1)
                yearTest.setOwnerID(users.get(0).getLoginName());

            Date date=new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            yearTest.setExamineID("D"+bridge.getDaiMa().concat("-"+sdf.format(date)));
            yearTest.setBridgeID(bridge.getDaiMa());
            yearTest.setDate(date);
        }

        //DataBase database = AppApplication.dataBase;


    }

    public void initData(){
        titles=new ArrayList<>();

        titles.add("基本信息");
        FragmentTestItemViewModel commViewModel = new FragmentTestItemViewModel(this,null,0,1);
        itemViewModels.add(commViewModel);
        //listBadge.add(new ObservableBoolean());
        listBadgeNum.add(new ObservableInt(0));
        listBadgeColor.add(new ObservableInt(0));
        //commViewModel.setYearTest(yearTest);

        //单项控制处理
        titles.add("高危部件");
        Construct gwct=new Construct();
        gwct.setBridgeID(bridge.getDaiMa());
        gwct.setBuJian("401");
        gwct.setCaiZhi("");
        FragmentTestItemViewModel gwViewModel = new FragmentTestItemViewModel(this,gwct,0,3);
        itemViewModels.add(gwViewModel);
        //listBadge.add(new ObservableBoolean());
        listBadgeNum.add(new ObservableInt(0));
        listBadgeColor.add(new ObservableInt(0));
        List<Disease> gwDiseaseList=null;
        for(int x=0;x<diseaseList.size();x++)
        {
            Disease disease=diseaseList.get(x);
            if(disease.getBuJian().equals("401")){
                if(gwDiseaseList==null)
                    gwDiseaseList=new ArrayList<>();
                gwDiseaseList.add(disease);
            }
        }
        if(gwDiseaseList!=null) {
            gwViewModel.setDiseaseList(gwDiseaseList);
        }

        int tppos=0;
        String tpxh="";
        for(int i=0;i<constructs.size();i++){
            Construct construct=constructs.get(i);
            if(unitList!=null && !tpxh.equals(construct.getXuHao())){
                FragmentTestItemViewModel itemViewModel = new FragmentTestItemViewModel(this,construct,i+1,2);
                itemViewModels.add(itemViewModel);
                //listBadge.add(new ObservableBoolean());
                tpxh=construct.getXuHao();
                unitList.get(tppos).viewModel=itemViewModel;
                titles.add("单元"+(tppos+1)+"\n"+unitList.get(tppos).name);
                unitList.get(tppos).position=titles.size()-1;
                listBadgeNum.add(new ObservableInt(0));
                listBadgeColor.add(new ObservableInt(0));
                tppos++;
            }
            String tpname = DataDict.getDict("6.2", construct.getBuJian());
            //多材质部件处理
            if ((i < constructs.size() - 1 && construct.getBuJian().equals(constructs.get(i + 1).getBuJian())) || (i > 0 && construct.getBuJian().equals(constructs.get(i - 1).getBuJian())))
                titles.add(getCaiZhi(construct) + tpname);
            else
                titles.add(tpname);
            FragmentTestItemViewModel itemViewModel = new FragmentTestItemViewModel(this, construct, i + 1, 3);
            itemViewModels.add(itemViewModel);
            //listBadge.add(new ObservableBoolean());
            itemViewModel.setDiseaseChangeListener(new FragmentTestItemViewModel.OnDiseaseChange() {
                @Override
                public void onChange(int pos, List<Disease> list) {
                    //listBadge.get(pos).set(true);
                }
            });

            List<Disease> ctDiseaseList = null;

            for (int x = 0; x < diseaseList.size(); x++) {
                Disease disease = diseaseList.get(x);
                if (disease.getCaiZhi().equals(construct.getCaiZhi()) && disease.getBuJian().equals(construct.getBuJian())) {
                    if (ctDiseaseList == null)
                        ctDiseaseList = new ArrayList<>();
                    ctDiseaseList.add(disease);
                }
            }
            if (ctDiseaseList != null) {
                itemViewModel.setDiseaseList(ctDiseaseList);
            }
            listBadgeNum.add(new ObservableInt(0));
            listBadgeColor.add(new ObservableInt(0));
        }

        adapter.titles=titles;
        adapter.listBadgeNum=listBadgeNum;
        adapter.listBadgeColor=listBadgeColor;

        if(unitList!=null) {
            for (int i = 0; i < unitList.size(); i++) {
                unitPosList.add(unitList.get(i).position);
            }
        }
        adapter.unitPosList=unitPosList;
        //adapter.fragmentTestViewModel=this;

        updatePingJiaData();

        bInitDataOK.set(true);
    }

    protected String getCaiZhi(Construct ct){
        String cz=ct.getCaiZhi();
        if(cz==null || cz.isEmpty())
            return "";
        String res="";
        cz="/"+cz;
        switch (ct.getBuJian()){
            case "101":
            case "102":
            case "103":
            case "104":
            case "105":
            case "106":
            case "107":
            case "108":
                res=DataDict.getDict("6.31",cz);
                break;
            case "161":
                res=DataDict.getDict("6.32",cz);
                break;
            case "121":
            case "125":
                res=DataDict.getDict("6.33",cz);
                break;
            case "141":
            case "152":
                res=DataDict.getDict("6.34",cz);
                break;
            case "301":
                res=DataDict.getDict("6.35",cz);
                break;
        }
        if(!res.isEmpty())
            return res+"\n";
        return "";
    }

    //给ViewPager添加ObservableList
    public ObservableList<FragmentTestItemViewModel> itemViewModels = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    //public ItemBinding<FragmentTestItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_test_viewpager_item);
    //给ViewPager添加ItemBinding
    public ItemBinding<FragmentTestItemViewModel> itemBinding = ItemBinding.of(new OnItemBind<FragmentTestItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, FragmentTestItemViewModel viewModel) {
            //通过item的类型, 动态设置Item加载的布局
            switch (viewModel.viewModelType) {
                case 1:
                    itemBinding.set(BR.viewModel, R.layout.layout_test_viewpager_comm_item);
                    break;
                case 2:
                    itemBinding.set(BR.viewModel, R.layout.layout_test_viewpager_comm_dy_item);
                    break;
                case 3:
                    itemBinding.set(BR.viewModel, R.layout.layout_test_viewpager_item);
                    break;
            }
        }
    });

    private int currentIndex=0;

    public FragmentTestItemViewModel getCurrentItemViewModel(){
        return itemViewModels.get(currentIndex);
    }

    //List<ObservableBoolean> listBadge=new ArrayList<>();
    public void addPage() {

        //updatePingJiaData();
    }

    public void updatePingJiaData(){
        if(yearTest.getGouJianPingFen()!=null) {
            String[] tplist = yearTest.getGouJianPingFen().split("[:,]");
            if (tplist.length > 0) {
                for(int i=0;i<tplist.length;i+=2){
                    for(int z=0;z<itemViewModels.size();z++) {
                        Construct ct = itemViewModels.get(z).construct;
                        if(ct==null)continue;
                        if (tplist[i].equals(ct.getBuJian()))
                            itemViewModels.get(z).bjFen.set(tplist[i + 1]);
                    }
                }
            }
        }
        itemViewModels.get(0).setYearTest(yearTest);

        for(int i=1;i<itemViewModels.size();i++) {
            int tpDis=0,upDis=0;
            List<Disease> dlist=itemViewModels.get(i).getDiseaseList();
            if(dlist!=null) {
                for (int x = 0; x < dlist.size(); x++) {
                    Disease di = dlist.get(x);
                    tpDis = di.getBiaoDu();
                    upDis = (tpDis > upDis) ? tpDis : upDis;
                }
                listBadgeNum.get(i).set(dlist.size());
                listBadgeColor.get(i).set(SystemConfig.getPingJiColor(upDis));
            }
        }
    }

    public void saveData(){
        int num=0;
        DataBase database = AppApplication.dataBase;
        List<Disease> diseaseList=new ArrayList<>();
        for(int i=0;i<itemViewModels.size();i++)
        {
            if(itemViewModels.get(i).viewModelType==3)
                diseaseList.addAll(itemViewModels.get(i).buildDisease());
        }
        for(int x=0;x<diseaseList.size();x++) {
            diseaseList.get(x).setExamineID(yearTest.getExamineID());
        }

        database.insertDiseaseData(diseaseList);
        num=diseaseList.size();

        BridgeEvaluation be=new BridgeEvaluation(yearTest);
        ArrayList<BridgeEvaluation.PJData> pjDataList=new ArrayList<>();
        be.evaluateTestData("101,201".split(","),diseaseList, constructs,pjDataList);

        if(unitList!=null && pjDataList.size()==unitList.size()) {
            for (int n = 0; n < pjDataList.size(); n++) {
                unitList.get(n).viewModel.dYPingFen=String.valueOf(pjDataList.get(n).pjFen);
                unitList.get(n).viewModel.dYUpFen=String.valueOf(pjDataList.get(n).upFen);
                unitList.get(n).viewModel.dYDwFen=String.valueOf(pjDataList.get(n).dwFen);
                unitList.get(n).viewModel.dYSfFen=String.valueOf(pjDataList.get(n).sfFen);
            }
        }

        database.insertOrReplaceYearTestData(yearTest);
        bridge.setYearTestID(yearTest.getExamineID());
        bridge.setPingJi(yearTest.getPingJia());
        database.insertOrReplaceBridgeData(DataSession.getCurrentQiaoLiang());

        //BridgeMaintain bridgeMaintain=new BridgeMaintain(bridge,yearTest);
        //database.insertOrReplaceMaintainData(bridgeMaintain);

        ToastUtils.showShort("共更新" + num+"条病害数据，桥梁评价已完成");

        updatePingJiaData();

        qiaoLiangViewModel.setPingJiaColor(yearTest.getPingJia());


    }

    protected int dealGouJianData(){
        return 0;
    }

    public List<String> getTitles()
    {
        return titles;
    }

    //给ViewPager添加PageTitle
    public final BindingViewPagerAdapter.PageTitles<FragmentTestItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<FragmentTestItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, FragmentTestItemViewModel item) {
            //return "条目" + item.name;
            return "";
        }
    };


    //给ViewPager添加Adpter，使用自定义的Adapter继承BindingViewPagerAdapter，重写onBindBinding方法
    //public final BindingViewPagerAdapter<FragmentPatrolItemViewModel> adapter = new BindingViewPagerAdapter();
    public final FragmentTestBindingAdapter adapter = new FragmentTestBindingAdapter();
    //ViewPager切换监听
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer index) {

            //ToastUtils.showShort("ViewPager切换：" + titles.get(index));
            currentIndex=index;
        }
    });


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
