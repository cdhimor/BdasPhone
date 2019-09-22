package cn.com.lxsoft.bdasphone.ui.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;

import cn.com.lxsoft.bdasphone.database.DataBase;
import cn.com.lxsoft.bdasphone.databinding.FragmentSearchBinding;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragment;
import cn.com.lxsoft.bdasphone.ui.component.LaybelLayout;
import cn.com.lxsoft.bdasphone.ui.examine.ExamineBrowseFragment;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import cn.com.lxsoft.bdasphone.utils.ConvertUtils;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

import static android.content.Context.MODE_PRIVATE;


public class FragmentSearch extends BaseFragment<FragmentSearchBinding, BaseViewModel> {
    @Override
    public void initParam() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_search;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    LinearLayout panelSearchItem;
    List<SearchCheckBox> checkBoxList;
    List<String> searchHistoryList=null;


    @Override
    public void initData() {
        //StatusBarUtils.setBar(this.getActivity(),R.color.colorPrimary,false);
        panelSearchItem=binding.layoutSearchPanelItem;
        checkBoxList=new ArrayList<>();

        addSearchPanelItem("1.1","桥梁类型");
        addSearchPanelItem("6.0","技术状况评级");
        addSearchPanelItem("1.7","设计荷载等级");
        addSearchPanelItem("1.3","跨越地物类型");
        addSearchPanelItem("1.4","公路技术等级");


        binding.buttonSearchOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resName="";
                String resCode="";
                int num=0;
                for(int i=0;i<checkBoxList.size();i++)
                {
                    if(checkBoxList.get(i).isChecked()){
                        num++;
                        if(resName=="")
                            resName=checkBoxList.get(i).name;
                        resCode=SystemConfig.BuildMutipleSearchData(resCode,checkBoxList.get(i).code);
                    }
                }
                if(num>0) {
                    if(num>1)
                        resName += num + "条件";
                    //resCode = resCode.substring(0, resCode.length() - 1);
                    startContainerActivity(BrowseFragment.class.getCanonicalName(),SystemConfig.buildBundleSearchData(resName,resCode));
                }
                else {
                    Activity activity=ActivityUtils.getActivityFromView(v);
                    activity.onBackPressed();
                }
            }
        });

        DataBase database = AppApplication.dataBase;
        String[] hisNameList=database.getSearchDataName();
        if(hisNameList!=null) {
            binding.layoutSearchHistoryPanel.setAdapter(new LaybelLayout.Adapter(hisNameList));
            binding.layoutSearchHistoryPanel.setOnItemClickListener(new LaybelLayout.OnItemClickListener() {
                String[] hisDataList=null;
                @Override
                public void onItemClick(int p) {
                    if(hisDataList==null)
                        hisDataList=database.getSearchHistoryData();
                    viewModel.startContainerActivity(BrowseFragment.class.getCanonicalName(),SystemConfig.buildBundleSearchData(hisNameList[p],hisDataList[p]));
                    //Toast.makeText(DefaultTextViewActivity.this, Content.content[p], Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void initViewObservable() {
        FragmentSearch bf = this;
    }

    protected void addSearchPanelItem(String dictID,String name){
        List<DataDict.DictItem> list=DataDict.getDictList(dictID);
        TextView textView = new TextView(this.getContext());
        LinearLayout.LayoutParams textViewLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //textViewLayout.setMargins(0,0,0,16);
        textView.setLayoutParams(textViewLayout);
        textView.setText(name);
        //textView.setTextColor(getResources().getColor(R.color.black));
        textView.setTextSize(14);
        panelSearchItem.addView(textView);

        LaybelLayout laybelLayout=new LaybelLayout(this.getContext());
        LinearLayout.LayoutParams laybelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //laybelParams.setMargins(0,16,0,16);
        laybelLayout.setLayoutParams(laybelParams);
        laybelLayout.setLinePadding(8);
        panelSearchItem.addView(laybelLayout);

        for(int i=0;i<list.size();i++){
            SearchCheckBox checkBox=new SearchCheckBox(this.getContext());
            LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkParams.setMargins(0,0,ConvertUtils.dp2px(16),0);
            checkBox.setLayoutParams(checkParams);

            //checkBox.ca
            checkBox.setPadding(ConvertUtils.dp2px(8),0,ConvertUtils.dp2px(8),0);
            checkBox.setBackground(getResources().getDrawable(R.drawable.sel_radio_search_func));
            //checkBox.setTextAppearance(R.drawable.sel_radio_search_func);
            checkBox.setSystemUiVisibility(View.GONE);
            checkBox.setTextSize(12);
            checkBox.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            checkBox.setText(list.get(i).name);
            checkBox.code=SystemConfig.BuildSearchData("m"+dictID,list.get(i).code);
            checkBox.name=list.get(i).name;
            checkBox.setButtonDrawable(null);
            laybelLayout.addView(checkBox);

            checkBoxList.add(checkBox);
        }
    }

    public class SearchCheckBox extends AppCompatCheckBox
    {
        public String name;
        public String code;
        public SearchCheckBox(Context ct){
            super(ct);
        }
    }

}
