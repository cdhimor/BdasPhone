package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.Application;
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
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.database.greendao.QiaoLiangDao;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;


public class SlideBrowseViewModel extends BaseBrowseFragmentViewModel {
    public short showListType;
    public String sSearchHint="路线";
    public String sSearchText="";
    public ObservableBoolean bSlideOpen=new ObservableBoolean(false);

    public interface OnItemClickListener {
        // 回调方法
        void onItemClick(short listType,Object object);
    }

    OnItemClickListener mOnItemClickListener;

    public SlideBrowseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        observableListMain = new ObservableArrayList<>();
        itemBindingMain = ItemBinding.of(BR.viewModel, R.layout.layout_luxian_item);
        adapterMain = new BindingRecyclerViewAdapter<>();
    }

    public void initData(short tptype)
    {
        if(tptype!=showListType) {
            if(tptype==SystemConfig.SlideList_Type_Path) {
                itemBindingMain = ItemBinding.of(BR.viewModel, R.layout.layout_luxian_item);
                sSearchHint="路线";
            }
            else {
                itemBindingMain = ItemBinding.of(BR.viewModel, R.layout.layout_dept_item);
                sSearchHint="单位";
            }
            showListType=tptype;
            dealMainDataBinding(0);
        }
        bSlideOpen.set(true);
    }

    protected List<Object> getMainListData(WhereCondition whereCondition, Property property,boolean bUp,int pageIndex)
    {
        DataBase database = AppApplication.dataBase;
        List tplist=null;
        if(showListType==SystemConfig.SlideList_Type_Path) {
            tplist = database.getLuXianData(sSearchText);
        }
        else
            tplist = database.getDeptData(sSearchText);
        return tplist;
    }

    protected void dealMainItem(Object obj){
        if(showListType==SystemConfig.SlideList_Type_Path) {
            LuXian luXian = (LuXian) obj;
            LuXianListItemViewModel itemViewModel = new LuXianListItemViewModel(SlideBrowseViewModel.this, luXian);
            //双向绑定动态添加Item
            observableListMain.add(itemViewModel);
            itemViewModel.setItemClickListener(new LuXianListItemViewModel.OnItemClickListener() {
                @Override
                public void onClick(LuXian tpLuXian) {
                    bSlideOpen.set(false);
                    mOnItemClickListener.onItemClick(showListType,tpLuXian);
                    //oStringChooseLuXian.set(tpLuXian.getBianHao());
                    //dealMainDataBinding(getWhereCondition("LuXian",tpLuXian.getBianHao()),0);
                }
            });
        }
        else
        {
            DanWei danWei = (DanWei) obj;
            DeptListItemViewModel itemViewModel = new DeptListItemViewModel(SlideBrowseViewModel.this, danWei);
            //双向绑定动态添加Item
            observableListMain.add(itemViewModel);
            itemViewModel.setItemClickListener(new DeptListItemViewModel.OnItemClickListener() {
                @Override
                public void onClick(DanWei tpDept) {
                    bSlideOpen.set(false);
                    mOnItemClickListener.onItemClick(showListType,tpDept);
                    //dealMainDataBinding(getWhereCondition("LuXian",tpLuXian.getBianHao()),0);
                }
            });
        }
    }

    //用户名输入框焦点改变的回调事件
    public BindingCommand<String> onSlideSearchTextChangeCommand = new BindingCommand<>(new BindingConsumer<String>() {
        @Override
        public void call(String editText) {
            sSearchText=editText;
            dealMainDataBinding(0);
        }
    });

    public void setItemClickListener(OnItemClickListener onItemClickListener ){
        mOnItemClickListener=onItemClickListener;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}