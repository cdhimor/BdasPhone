package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.Application;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import org.greenrobot.greendao.Property;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.DataSession;
import cn.com.lxsoft.bdasphone.database.greendao.BridgeJCSBDao;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.ui.examine.FragmentCheck;
import cn.com.lxsoft.bdasphone.ui.examine.FragmentPatrol;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;


public class BrowseFragmentViewModel extends BaseBrowseFragmentViewModel {
    public ObservableInt oIntToolbarSelectedPanel = new ObservableInt(0);

    public SlideBrowseViewModel slideBrowseViewModel;

    public BrowseFragmentViewModel(@NonNull Application application) {

        super(application);
        slideBrowseViewModel=new SlideBrowseViewModel(application);
    }

    @Override
    public void onCreate() {

        observableListMain = new ObservableArrayList<>();
        itemBindingMain = ItemBinding.of(BR.viewModel, R.layout.layout_qiaoliang_item);
        adapterMain = new BindingRecyclerViewAdapter<>();
        //dealMainDataBinding(pageIndexMain.get());

        slideBrowseViewModel.setItemClickListener(new SlideBrowseViewModel.OnItemClickListener() {
            @Override
            public void onItemClick(short listType, Object object) {
                if(listType==SystemConfig.SlideList_Type_Path){
                    setSearchData("m0.4",(((LuXian)object).getBianHao()));
                }
                else if(listType==SystemConfig.SlideList_Type_Dept) {
                    setSearchData("m0.3",(((DanWei)object).getDaiMa()));
                }
                dealMainDataBinding(0);
            }
        });

        slideBrowseViewModel.onCreate();

        /*
        if(DataSession.getSearchInfo()!="")
            initSearchInfo=DataSession.getSearchInfo();
        DataSession.setSearchInfo("");
        */
    }

    protected void dealMainItem(Object tpobj){
        QiaoLiang tpEntity=(QiaoLiang)tpobj;
        QiaoLiangListItemViewModel itemViewModel = new QiaoLiangListItemViewModel(BrowseFragmentViewModel.this, tpEntity);
        //itemViewModel.parentViewModel=BrowseFragmentViewModel.this;
        //双向绑定动态添加Item
        observableListMain.add(itemViewModel);
        itemViewModel.setItemClickListener(new QiaoLiangListItemViewModel.OnItemClickListener() {
            @Override
            public void onClick(QiaoLiang tpQiaoLiang) {
                //oStringChooseQiaoLiang.set(tpQiaoLiang.getDaiMa());
                DataSession.setCurrentQiaoLiang(tpQiaoLiang);
                startContainerActivity(ContentFragment.class.getCanonicalName());
            }
        });
    }

    public void onToolbarSelectedPanelChange(int i) {
        if (i == 5) {//luxian
            slideBrowseViewModel.initData(SystemConfig.SlideList_Type_Path);
        }
        else if(i==4){
            slideBrowseViewModel.initData(SystemConfig.SlideList_Type_Dept);
        }
        //else if (oIntToolbarSelectedPanel.get() != i)
        oIntToolbarSelectedPanel.set(i);
    }

    public void onToolbarQuickSearch(int searchNum) {
        String searchRes="";
        if(searchNum==-1)
            setSearchData("m0.1","NOTNULL");
        if(searchNum>20)
            setSearchData("m1.1", Integer.toString(searchNum - 20));
        else if (searchNum > 10)
            setSearchData("m6.0", Integer.toString(searchNum - 10));
        dealMainDataBinding(0);
    }

    private int curOrderNum=0;
    public void onToolbarOrderSet(int orderNum)
    {
        boolean bUp=oBoolMainOrderUp.get();
        Property pt=QiaoLiangDao.Properties.DaiMa;
        switch (orderNum)
        {
            case 2:
                pt=QiaoLiangDao.Properties.LuXianID;
                break;
            case 3:
                pt=QiaoLiangDao.Properties.PingJi;
                break;
            case 4:
                pt=QiaoLiangDao.Properties.QiaoChang;
                break;
        }
        if(curOrderNum==orderNum)
            bUp=!bUp;
        curOrderNum=orderNum;
        dealMainDataBinding(pt,bUp,0);
    }


    protected List<Object> getMainListData(WhereCondition whereCondition, Property property,boolean bUp,int pageIndex)
    {
        DataBase database = AppApplication.dataBase;
        List tplist;
        //    tplist=database.getQiaoLiangData(whereConditionMain,propertyOrderMain,oBoolMainOrderUp.get(),pageIndex);
        //if(pageIndex==0)
        //    tplist=database.getQiaoLiangDataAdv(advSearchCondition,propertyOrderMain,oBoolMainOrderUp.get(),pageIndex);
        //else
            tplist=database.getBridgePageData(pageIndex);
        return tplist;
    }

    public void setSearchHistoryData(String name,String data){
        if(name=="")
            return;
        DataBase database = AppApplication.dataBase;
        database.addSearchHistoryData(name,data);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        slideBrowseViewModel.onDestroy();

    }



}