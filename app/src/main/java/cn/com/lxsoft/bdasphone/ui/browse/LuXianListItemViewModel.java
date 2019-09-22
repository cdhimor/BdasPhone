package cn.com.lxsoft.bdasphone.ui.browse;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import cn.com.lxsoft.bdasphone.entity.LuXian;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;


public class LuXianListItemViewModel extends ItemViewModel<BaseViewModel> {
    public ObservableField<LuXian> entity = new ObservableField<>();
    public String showName;
    public interface OnItemClickListener {
        // 回调方法
        void onClick(LuXian tpLuXian);
    }

    OnItemClickListener mOnItemClickListener;


    public LuXianListItemViewModel(@NonNull BaseViewModel viewModel, LuXian tpEntity)
    {
        super(viewModel);
        entity.set(tpEntity);
        if(tpEntity.getBianHao()!=null)
            showName="[".concat(tpEntity.getBianHao()).concat("]");
        if(tpEntity.getMingCheng()!=null)
            showName=showName.concat(tpEntity.getMingCheng());
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener ){
        mOnItemClickListener=onItemClickListener;
    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mOnItemClickListener.onClick(entity.get());
        }
    });

}
