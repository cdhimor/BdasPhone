package cn.com.lxsoft.bdasphone.ui.browse;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;


public class DeptListItemViewModel extends ItemViewModel<BaseViewModel> {
    //public ObservableField<DanWei> entity = new ObservableField<>();
    public String showName;
    protected DanWei entity;
    public interface OnItemClickListener {
        // 回调方法
        void onClick(DanWei tpDanWei);
    }

    OnItemClickListener mOnItemClickListener;


    public DeptListItemViewModel(@NonNull BaseViewModel viewModel, DanWei tpEntity)
    {
        super(viewModel);
        entity=tpEntity;
        showName=tpEntity.getMingCheng();
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener ){
        mOnItemClickListener=onItemClickListener;
    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mOnItemClickListener.onClick(entity);
        }
    });

}
