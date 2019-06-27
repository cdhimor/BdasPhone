package cn.com.lxsoft.bdasphone.ui.examine;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.ContextWrapper;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.widget.WheelView;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import cn.com.lxsoft.bdasphone.databinding.LayoutPatrolViewpagerItemBinding;
//import q.rorbin.verticaltablayout.adapter.TabAdapter;
//import q.rorbin.verticaltablayout.widget.TabView;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;

/**
 * Created by goldze on 2018/6/21.
 */

public class FragmentPatrolBindingAdapter extends BindingViewPagerAdapter<FragmentPatrolItemViewModel> /*implements TabAdapter*/ {
    public FragmentPatrolViewModel fragmentPatrolViewModel;

    @Override
    public void onBindBinding(final ViewDataBinding binding, int variableId, int layoutRes, final int position, FragmentPatrolItemViewModel itemViewModel) {
        super.onBindBinding(binding, variableId, layoutRes, position, itemViewModel);

        //这里可以强转成ViewPagerItemViewModel对应的ViewDataBinding，
        LayoutPatrolViewpagerItemBinding itemBinding = (LayoutPatrolViewpagerItemBinding) binding;

        itemViewModel.bCheckDisease.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if(itemViewModel.bCheckDisease.get()) {
                    itemBinding.butAddDisease.setVisibility(View.GONE);
                }
                else
                    itemBinding.butAddDisease.setVisibility(View.VISIBLE);
            }
        });

        itemViewModel.oALDiseaseList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<String>>() {
            @Override
            public void onChanged(ObservableList<String> sender) {
                return;
            }

            @Override
            public void onItemRangeChanged(ObservableList<String> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<String> sender, int positionStart, int itemCount) {
                parseDiseaseList(sender.subList(positionStart,positionStart+itemCount),itemBinding,itemViewModel);
            }

            @Override
            public void onItemRangeMoved(ObservableList<String> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<String> sender, int positionStart, int itemCount) {

            }
        });

        itemViewModel.clickEvent.observe((LifecycleOwner) itemBinding.getRoot().getContext(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                SinglePicker<DataDict.DictItem> picker = new SinglePicker<>(ActivityUtils.getActivityFromView(itemBinding.getRoot()), DataDict.getDictList("5.1"));
                picker.setCanceledOnTouchOutside(false);
                picker.setSelectedIndex(1);
                picker.setCycleDisable(false);
                picker.setTextSize(16);
                picker.setOffset(4);
                picker.setLineSpaceMultiplier(3);
                picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<DataDict.DictItem>() {
                    @Override
                    public void onItemPicked(int index, DataDict.DictItem item) {
                        itemViewModel.oALDiseaseList.add(item.code);
                    }
                });
                picker.show();
            }
        });

        parseDiseaseList(itemViewModel.oALDiseaseList,itemBinding,itemViewModel);

        return;
    }

    void parseDiseaseList(List<String> diseaseList,LayoutPatrolViewpagerItemBinding itemBinding,FragmentPatrolItemViewModel itemViewModel)
    {
        LayoutInflater inflater = LayoutInflater.from(itemBinding.getRoot().getContext());
        //ViewGroup view=(ViewGroup)((ViewGroup)inflater.inflate(R.layout.layout_patrol_disease_item,_binding.layoutDiseaseList,false)).getChildAt(0);
        for(int i=0;i<diseaseList.size();i++) {
            String res=DataDict.getDict("5.1", diseaseList.get(i));
            ViewGroup view=(ViewGroup)inflater.inflate(R.layout.layout_patrol_disease_item,itemBinding.layoutDiseaseList,false);
            TextView tv=(TextView)view.getChildAt(0);
            tv.setText(res);
            ImageView iv=(ImageView)view.getChildAt(1);
            final int num=i;
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemBinding.layoutDiseaseList.removeView(view);
                    itemViewModel.oALDiseaseList.remove(num);
                }
            });
            itemBinding.layoutDiseaseList.addView(view);
        }
    }

    /*
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public TabView.TabBadge getBadge(int position) {
        if (position == 2) return new TabView.TabBadge.Builder().setBadgeNumber(666)
                .setExactMode(true).build();
        return null;
    }

    @Override
    public TabView.TabIcon getIcon(int position) {
        return null;
    }


    @Override
    public TabView.TabTitle getTitle(int position) {

        String title=fragmentPatrolViewModel.getTitles().get(position);
        return new TabView.TabTitle.Builder()
                .setContent(title)
                .setTextColor(0xFF36BC9B, 0xFF757575)
                .build();
    }

    @Override
    public int getBackground(int position) {
        return R.drawable.shape_rect_border_primarycolor;
    }*/

    @Override
    public int getCount() {
        return fragmentPatrolViewModel.getTitles().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title=fragmentPatrolViewModel.getTitles().get(position);
        return title;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
