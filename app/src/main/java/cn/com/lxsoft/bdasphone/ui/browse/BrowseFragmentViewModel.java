package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.Application;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import org.greenrobot.greendao.Property;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class BrowseFragmentViewModel extends BaseViewModel {
    public ObservableInt oIntToolbarSelectedPanel = new ObservableInt();

    public ObservableField<String> oStringChooseQiaoLiang = new ObservableField<>();
    public ObservableList<QiaoLiangListItemViewModel> observableListQiaoLiang = new ObservableArrayList<>();
    public ItemBinding<QiaoLiangListItemViewModel> itemBindingQiaoLiang = ItemBinding.of(BR.viewModel, R.layout.layout_qiaoliang_item);
    public final BindingRecyclerViewAdapter<QiaoLiangListItemViewModel> adapterQiaoLiang = new BindingRecyclerViewAdapter<>();
    public ObservableInt pageIndexQiaoLiang = new ObservableInt(0);
    private WhereCondition whereConditionQiaoLiang=QiaoLiangDao.Properties.Id.isNotNull();
    private Property propertyOrderQiaoLiang=QiaoLiangDao.Properties.DaiMa;
    public ObservableBoolean oBoolQiaoLiangOrderUp=new ObservableBoolean(true);


    public ObservableField<String> oStringLuXianSearchText = new ObservableField<>();
    public ObservableList<LuXianListItemViewModel> observableListLuXian = new ObservableArrayList<>();
    public ItemBinding<LuXianListItemViewModel> itemBindingLuXian = ItemBinding.of(BR.viewModel, R.layout.layout_luxian_item);
    public final BindingRecyclerViewAdapter<LuXianListItemViewModel> adapterLuXian = new BindingRecyclerViewAdapter<>();




    //boolean bDWDataInit

    public BrowseFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {

        oIntToolbarSelectedPanel.set(2);
        dealQiaoLiangDataBinding(pageIndexQiaoLiang.get());
    }

    public void onToolbarSelectedPanelChange(int i) {
        if (oIntToolbarSelectedPanel.get() != i)
            oIntToolbarSelectedPanel.set(i);
        if (i == 5 && observableListLuXian.isEmpty()) {//luxian
            dealLuXianDataBinding("");
        }
    }

    private WhereCondition getWhereCondition(String type, String res){
        WhereCondition wc=null;
        switch (type)
        {
            case "LuXian":
                wc= QiaoLiangDao.Properties.LuXianID.eq(res);
                break;
        }
        whereConditionQiaoLiang=wc;
        return wc;
    }

    private void dealLuXianDataBinding(String searchText) {
        DataBase database = AppApplication.dataBase;
        database.getLuXianData(searchText)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<LuXian>>() {
                            @Override
                            public void call(List<LuXian> luXians) {
                                observableListLuXian.clear();
                                for (int i = 0; i < luXians.size(); i++) {
                                    LuXianListItemViewModel itemViewModel = new LuXianListItemViewModel(BrowseFragmentViewModel.this, luXians.get(i));
                                    //双向绑定动态添加Item
                                    observableListLuXian.add(itemViewModel);
                                    itemViewModel.setItemClickListener(new LuXianListItemViewModel.OnItemClickListener() {
                                        @Override
                                        public void onClick(LuXian tpLuXian) {
                                            //oStringChooseLuXian.set(tpLuXian.getBianHao());
                                            dealQiaoLiangDataBinding(getWhereCondition("LuXian",tpLuXian.getBianHao()),0);
                                        }
                                    });
                                }
                            }
                        }
                );

    }

    private void dealQiaoLiangDataBinding(Property orderProperty,boolean bUp,int pageIndex){
        propertyOrderQiaoLiang=orderProperty;
        oBoolQiaoLiangOrderUp.set(bUp);
        dealQiaoLiangDataBinding(pageIndex);
    }

    private void dealQiaoLiangDataBinding(WhereCondition whereCondition,int pageIndex){
        whereConditionQiaoLiang=whereCondition;
        dealQiaoLiangDataBinding(pageIndex);
    }

    private void dealQiaoLiangDataBinding(int pageIndex) {
        pageIndexQiaoLiang.set(pageIndex);
        DataBase database = AppApplication.dataBase;
        database.getQiaoLiangData(whereConditionQiaoLiang,propertyOrderQiaoLiang,oBoolQiaoLiangOrderUp.get(),pageIndex)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<QiaoLiang>>() {
                            @Override
                            public void call(List<QiaoLiang> qiaoLiangs) {
                                if(pageIndex==0)
                                    observableListQiaoLiang.clear();
                                for (int i = 0; i < qiaoLiangs.size(); i++) {
                                    QiaoLiangListItemViewModel itemViewModel = new QiaoLiangListItemViewModel(BrowseFragmentViewModel.this, qiaoLiangs.get(i));
                                    //双向绑定动态添加Item
                                    observableListQiaoLiang.add(itemViewModel);
                                    itemViewModel.setItemClickListener(new QiaoLiangListItemViewModel.OnItemClickListener() {
                                        @Override
                                        public void onClick(QiaoLiang tpQiaoLiang) {
                                            oStringChooseQiaoLiang.set(tpQiaoLiang.getDaiMa());
                                        }
                                    });
                                }
                            }
                        }
                );
    }

    public void onToolbarQuickSearch(int searchNum) {
        WhereCondition wc=null;
        switch(searchNum) {
            case -1://点击取消
                wc=QiaoLiangDao.Properties.Id.ge(0);
                break;
            case 11:
                wc = QiaoLiangDao.Properties.PingJi.eq(1);
                break;
            case 12:
                wc = QiaoLiangDao.Properties.PingJi.eq(2);
                break;
            case 13:
                wc = QiaoLiangDao.Properties.PingJi.eq(3);
                break;
            case 14:
                wc = QiaoLiangDao.Properties.PingJi.eq(4);
                break;
            case 15:
                wc = QiaoLiangDao.Properties.PingJi.eq(5);
                break;
            case 21:
                wc = QiaoLiangDao.Properties.LeiXing.eq("1");
                break;
            case 22:
                wc = QiaoLiangDao.Properties.LeiXing.eq("2");
                break;
            case 23:
                wc = QiaoLiangDao.Properties.LeiXing.eq("3");
                break;
            case 24:
                wc = QiaoLiangDao.Properties.LeiXing.eq("4");
                break;
        }
        dealQiaoLiangDataBinding(wc,0);
    }

    private int curOrderNum=0;
    public void onToolbarOrderSet(int orderNum)
    {
        boolean bUp=oBoolQiaoLiangOrderUp.get();
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
        dealQiaoLiangDataBinding(pt,bUp,0);
    }

    //用户名输入框焦点改变的回调事件
    public BindingCommand<String> onLuXianSearchTextChangeCommand = new BindingCommand<>(new BindingConsumer<String>() {
        @Override
        public void call(String editText) {
            dealLuXianDataBinding(editText);
        }
    });

    public BindingCommand onLoadMoreCommandQiaoLiang = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            dealQiaoLiangDataBinding(pageIndexQiaoLiang.get()+1);
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
        observableListLuXian.clear();
        observableListQiaoLiang.clear();
        observableListLuXian=null;
        observableListQiaoLiang=null;
    }

}