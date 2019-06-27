package cn.com.lxsoft.bdasphone.ui.examine;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.BR;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.DataSession;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.ui.browse.QiaoLiangListItemViewModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class FragmentPatrolViewModel extends BaseViewModel {
    public String sExamineID="";
    Patrol patrol;
    QiaoLiang bridge;
    public QiaoLiangListItemViewModel qiaoLiangViewModel;

    public FragmentPatrolViewModel(@NonNull Application application) {
        super(application);


    }

    //给ViewPager添加ObservableList
    public ObservableList<FragmentPatrolItemViewModel> items = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<FragmentPatrolItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_patrol_viewpager_item);


    @Override
    public void onCreate(){
        titles=new ArrayList<>();
        titles.add("桥路连接处");
        titles.add("桥面铺装、伸缩缝");
        titles.add("栏杆或护栏");
        titles.add("标志标牌");
        titles.add("桥梁线形");
        titles.add("备注");

        items.clear();
        //模拟3个ViewPager页面
        for (int i = 0; i < titles.size(); i++) {
            FragmentPatrolItemViewModel itemViewModel = new FragmentPatrolItemViewModel(this, titles.get(i));
            items.add(itemViewModel);
        }

        adapter.fragmentPatrolViewModel=this;

        bridge=DataSession.getCurrentQiaoLiang();
        sExamineID=bridge.getPatrolID();
        if(sExamineID!=null && !sExamineID.equals(""))
        {
            DataBase database = AppApplication.dataBase;
            patrol=database.getPatrolData(sExamineID);
            //bridge=patrol.getBridge();

            items.get(0).setDiseaseInfo(patrol.getQiaoLuLianJie());
            items.get(1).setDiseaseInfo(patrol.getPuZhuangSSF());
            items.get(2).setDiseaseInfo(patrol.getLanGan());
            items.get(3).setDiseaseInfo(patrol.getBiaoZhi());
            items.get(4).setDiseaseInfo(patrol.getXianXing());
        }
        else {
            patrol = new Patrol();
        }
        qiaoLiangViewModel=new QiaoLiangListItemViewModel(this,bridge);
        qiaoLiangViewModel.bCanClick=false;

    }


    public void dealPatrolCreateOK(){
        Date date=new Date();
        boolean isInsert=false;
        if(patrol.getExamineID()==null || patrol.getExamineID()=="")
            isInsert=true;

        if(isInsert) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            patrol.setExamineID(bridge.getDaiMa().concat(sdf.format(date)));
        }
        patrol.setBridgeID(bridge.getDaiMa());
        patrol.setDate(date);
        patrol.setWorkerID(DataSession.getCurrentUser().getLoginName());

        patrol.setQiaoLuLianJie(items.get(0).getDiseaseInfo());
        patrol.setPuZhuangSSF(items.get(1).getDiseaseInfo());
        patrol.setLanGan(items.get(2).getDiseaseInfo());
        patrol.setBiaoZhi(items.get(3).getDiseaseInfo());
        patrol.setXianXing(items.get(4).getDiseaseInfo());

        DataBase database = AppApplication.dataBase;
        database.insertOrReplacePatrolData(patrol);
        if(isInsert){
            bridge.setPatrolID(patrol.getExamineID());
            database.insertOrReplaceBridgeData(bridge);
        }
        ToastUtils.showShort("数据录入成功");
    }


    List<String> titles;
    public void addPage() {

    }

    public List<String> getTitles()
    {
        return titles;
    }

    //给ViewPager添加PageTitle
    public final BindingViewPagerAdapter.PageTitles<FragmentPatrolItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<FragmentPatrolItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, FragmentPatrolItemViewModel item) {
            return "条目" + position;
        }
    };



    //给ViewPager添加Adpter，使用自定义的Adapter继承BindingViewPagerAdapter，重写onBindBinding方法
    //public final BindingViewPagerAdapter<FragmentPatrolItemViewModel> adapter = new BindingViewPagerAdapter();
    public final FragmentPatrolBindingAdapter adapter = new FragmentPatrolBindingAdapter();
    //ViewPager切换监听
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer index) {
            ToastUtils.showShort("ViewPager切换：" + index);
        }
    });


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
