package cn.com.lxsoft.bdasphone.ui.examine;

import android.content.Context;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.LayoutTestViewpagerItemBinding;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.DiseaseInfo;
import cn.com.lxsoft.bdasphone.entity.ImageData;
import cn.com.lxsoft.bdasphone.ui.component.BridgeImagePanel;
import cn.com.lxsoft.bdasphone.ui.component.DiseaseInput;
import cn.com.lxsoft.bdasphone.ui.component.DiseaseInputPanel;
import me.goldze.mvvmhabit.utils.ConvertUtils;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by goldze on 2018/6/21.
 */

public class FragmentTestBindingAdapter extends BindingViewPagerAdapter<FragmentTestItemViewModel> implements TabAdapter {
    //public FragmentTestViewModel fragmentTestViewModel;
    public FragmentTest fragmentTest;
    public List<String> titles;
    public List<ObservableInt> listBadgeNum;
    public List<ObservableInt> listBadgeColor;
    protected Map<String,MaterialDialog> gjDialogMap;
    protected Map<String,MaterialDialog> dsDialogMap;

    public ArrayList<Integer> unitPosList;

    @Override
    public void onBindBinding(final ViewDataBinding _binding, int variableId, int layoutRes, final int position, FragmentTestItemViewModel viewModel) {
        super.onBindBinding(_binding, variableId, layoutRes, position, viewModel);

        if(viewModel.viewModelType!=3)
            return;

        LayoutTestViewpagerItemBinding binding = (LayoutTestViewpagerItemBinding) _binding;
        Context context=_binding.getRoot().getContext();

        boolean bGW=false;
        if(viewModel.construct.getBuJian().equals("401")){
            binding.layoutPfInfoPanel.setVisibility(View.GONE);
            bGW=true;
        }
        final boolean checkGW=bGW;

        binding.butAddTestDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disease disease=viewModel.addDisease();
                DiseaseInputPanel inputPanel=new DiseaseInputPanel(context);
                if(checkGW) {
                    inputPanel.bInitGJ = false;
                    inputPanel.sAutoGJ = "1";
                }
                inputPanel.init(viewModel.getConstruct(),disease,binding.layoutTestDiseaseList,fragmentTest);
                //addDiseaseData(binding,viewModel,disease);
            }
        });

        List<Disease> dList=viewModel.getDiseaseList();
        for(int i=0;i<dList.size();i++)
        {
            DiseaseInputPanel inputPanel=new DiseaseInputPanel(context);
            if(checkGW)
                inputPanel.bInitGJ=false;
            inputPanel.init(viewModel.getConstruct(),dList.get(i),binding.layoutTestDiseaseList,fragmentTest);
            //addDiseaseData(binding,viewModel,dList.get(i));
        }

        return;
    }

    @Override
    public TabView.TabBadge getBadge(int position) {
        if(listBadgeNum.get(position).get()==0)
            return null;
        else {
            int mcolor=listBadgeColor.get(position).get();
            int tpcolor=0xffffffff;
            if(mcolor==SystemConfig.getPingJiColor(3))
                tpcolor=0xff000000;
            return new TabView.TabBadge.Builder().setBadgeNumber(listBadgeNum.get(position).get()).setExactMode(true).setBackgroundColor(mcolor).setBadgeTextColor(tpcolor).build();
        }
    }

    @Override
    public TabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public TabView.TabTitle getTitle(int position) {
        String title=titles.get(position);
        return new TabView.TabTitle.Builder()
                .setContent(title)
                .setTextColor(0xFF009900, Color.BLACK)
                .build();
    }

    @Override
    public int getBackground(int position) {
        if(unitPosList!=null && unitPosList.indexOf(position)!=-1)
            return R.drawable.shape_rect_noborderradius_lightgray;
        else
            return R.drawable.shape_rect_botomrightline_lightgray;
    }

    @Override
    public int getCount() {
        if(titles==null)
            return 0;
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(titles==null)
            return "";
        String title=titles.get(position);
        return title;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
