package cn.com.lxsoft.bdasphone.ui.examine;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.DataSession;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.YearTest;
import cn.com.lxsoft.bdasphone.ui.browse.QiaoLiangListItemViewModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class FragmentTestViewModel extends BaseViewModel {
    QiaoLiang bridge;
    YearTest yearTest;

    public QiaoLiangListItemViewModel qiaoLiangViewModel;

    public FragmentTestViewModel(@NonNull Application application) {

        super(application);

        qiaoLiangViewModel=new QiaoLiangListItemViewModel(this,DataSession.getCurrentQiaoLiang());
        qiaoLiangViewModel.bCanClick=false;
    }

    @Override
    public void onCreate() {
        bridge=DataSession.getCurrentQiaoLiang();

        String sExamineID=bridge.getYearTestID();
        if(sExamineID!=null && !sExamineID.equals(""))
        {
            DataBase database = AppApplication.dataBase;
            yearTest=database.getYearTestData(sExamineID);
        }
        else {
            yearTest = new YearTest();
        }
    }

    //给ViewPager添加ObservableList
    public ObservableList<FragmentTestItemViewModel> itemViewModels = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<FragmentTestItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_test_viewpager_item);

    private int currentIndex=0;

    public FragmentTestItemViewModel getCurrentItemViewModel(){
        return itemViewModels.get(currentIndex);
    }

    List<String> titles;
    List<Construct> constructs;
    public void addPage() {
        DataBase database = AppApplication.dataBase;
        constructs=database.getConstructData(bridge.getDaiMa());

        List<Disease> dList=database.getDiseaseData(bridge.getDaiMa());

        titles=new ArrayList<>();
        for(int i=0;i<constructs.size();i++){
            String tpname=DataDict.getDict("6.2",constructs.get(i).getBuJian());
            titles.add(tpname);
            Construct construct=constructs.get(i);
            FragmentTestItemViewModel itemViewModel = new FragmentTestItemViewModel(this,construct);
            itemViewModels.add(itemViewModel);

            List<Disease> ctDiseaseList=null;

            for(int x=0;x<dList.size();x++)
            {
                Disease disease=dList.get(x);
                if(disease.getCaiZhi().equals(construct.getCaiZhi()) && disease.getBuJian().equals(construct.getBuJian())){
                    if(ctDiseaseList==null)
                        ctDiseaseList=new ArrayList<>();
                    ctDiseaseList.add(disease);
                }

            }
            if(ctDiseaseList!=null)
                itemViewModel.setDiseaseList(ctDiseaseList);
        }

        adapter.titles=titles;
    }

    public void saveData(){
        int num=0;
        DataBase database = AppApplication.dataBase;
        List<Disease> diseaseList=new ArrayList<>();
        for(int i=0;i<itemViewModels.size();i++)
        {
            diseaseList.addAll(itemViewModels.get(i).buildDisease());
        }
        database.insertOrReplaceDiseaseData(diseaseList);
        num=diseaseList.size();
        ToastUtils.showShort("更新完毕，共更新" + num+"条病害数据");
        evaluateTestData(diseaseList,constructs);

    }

    class GjSt{
        public float score;
        public List<String> bhList;
    }

    class BjSt{
        public float score;
        public float weight;
        public HashMap<String,GjSt> gjMap;
    }

    public void evaluateTestData(List<Disease> dList,List<Construct> consList) {
        List dataList = new ArrayList();
        float upScore=0,dwScore=0,sfScore=0;
        String resBjScore="";

        HashMap<String, Float> wtMap = DataDict.getConstructWeight(bridge.getJieGou().substring(4));

        HashMap<String, BjSt> bjMap = new HashMap<>();
        for (int i = 0; i < consList.size(); i++) {
            Construct ct = consList.get(i);
            BjSt bjData;
            if (bjMap.containsKey(ct.getBuJian())) {
                bjData = bjMap.get(ct.getBuJian());
            } else {
                bjData = new BjSt();
                bjData.gjMap = new HashMap<>();
                bjMap.put(ct.getBuJian(), bjData);
                //bjData.weight = wtMap.get(ct.getBuJian());
            }

            String[] tplist = ct.getGouJian().split(",");
            for (int x = 0; x < tplist.length; x++) {
                GjSt gjData = new GjSt();
                bjData.gjMap.put(tplist[x], gjData);
                List<String> bhList = null;
                for (int y = 0; y < dList.size(); y++) {
                    Disease di = dList.get(y);
                    if (di.getBuJian().equals(ct.getBuJian()) && di.getGouJian().equals(tplist[x])) {
                        if (bhList == null)
                            bhList = new ArrayList();
                        bhList.add(di.getBingHai());
                    }
                    gjData.bhList = bhList;
                }
            }
        }

        for (String bjKey : bjMap.keySet()) {
            HashMap<String, GjSt> gjMap = bjMap.get(bjKey).gjMap;
            float sumGj=0,minGj=100;
            boolean bLess40=false;
            for (String gjKey : gjMap.keySet()) {
                List<String> bhList = gjMap.get(gjKey).bhList;
                float score = 100;
                if(bhList!=null) {
                    if (bhList.size() == 1) {
                        score = 100f - getDMark(bhList.get(0));
                    } else if (bhList.size() > 1) {
                        float sumU = getDMark(bhList.get(0));
                        int dp = 0;
                        for (int z = 1; z < bhList.size(); z++) {
                            dp = getDMark(bhList.get(z));
                            if (dp == 100) {
                                score = 0;
                                break;
                            }
                            sumU += dp * (100f - sumU) / 100f / (float) Math.sqrt(z);
                        }
                        if (score != 0)
                            score = 100f - sumU;
                    }
                }
                gjMap.get(gjKey).score=score;
                sumGj+=score;
                if(score<minGj)
                    minGj=score;
                if(score<40)
                    bLess40=true;
            }
            float bjScore=0;
            if(bLess40)
                bjScore=minGj;
            else
                bjScore=sumGj/gjMap.size()-(100-minGj)/getTMark(gjMap.size());
            bjMap.get(bjKey).score=bjScore;
            resBjScore+=","+bjKey+":"+Integer.toString((int)bjScore);
            switch (bjKey.charAt(0)){
                case '1':
                    upScore+=bjScore*wtMap.get(bjKey);
                    break;
                case '2':
                    dwScore+=bjScore*wtMap.get(bjKey);
                    break;
                case '3':
                    sfScore+=sfScore*wtMap.get(bjKey);
                    break;
            }
        }

        yearTest.setGouJianPingFen(resBjScore.substring(1));
        yearTest.setShangBuJieGouPingFen((int)upScore);
        yearTest.setXiaBuJieGouPingFen((int)dwScore);
        yearTest.setQiaoMianXiPingFen((int)sfScore);

        int endScore=(int)(upScore*wtMap.get("100")+dwScore*wtMap.get("200")+sfScore*wtMap.get("300"));
        int pingJi=1;
        yearTest.setPingFen(endScore);

        if(endScore<40)
            pingJi=5;
        else if (endScore<60)
            pingJi=4;
        else if(pingJi<78)
            pingJi=3;
        else if(pingJi<90)
            pingJi=2;
        yearTest.setPingJia(pingJi);
    }

    int[][] markMx={{0,20,35},{ 0,25,40,50},{0,35,45,60,100}};
    protected int getDMark(String dID){
        int highDJ=Integer.parseInt(dID.substring(7,8));
        int dDJ=Integer.parseInt(dID.substring(8,9));
        return markMx[highDJ-3][dDJ-1];
    }

    float[] tMarkMx={Float.POSITIVE_INFINITY,10, 9.7f, 10.5f, 10.2f, 8.9f, 8.7f, 8.5f, 9.3f, 9.1f, 8.8f, 8.7f, 8.5f, 8.3f, 8.2f, 7.08f, 6.96f, 6.84f, 6.72f, 6.6f, 7.48f, 7.36f, 7.24f, 7.12f, 6.00f, 5.88f, 5.76f, 5.64f, 5.52f, 5.4f, 4.9f, 4.4f, 4.0f, 3.6f, 3.2f, 2.8f, 2.5f, 2.3f};

    protected float getTMark(int size){
        if(size>100)
            return 2.3f;
        else if(size>=30)
            return tMarkMx[26+(int)Math.floor(size/10)];
        return tMarkMx[size-1];
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

            ToastUtils.showShort("ViewPager切换：" + titles.get(index));
            currentIndex=index;
        }
    });


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
