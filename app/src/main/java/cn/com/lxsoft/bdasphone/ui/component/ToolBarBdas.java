package cn.com.lxsoft.bdasphone.ui.component;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.databinding.FragmentBrowseBinding;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseFragment;
import cn.com.lxsoft.bdasphone.ui.main.MainActivity;
import cn.com.lxsoft.bdasphone.ui.search.FragmentSearch;
import cn.com.lxsoft.bdasphone.ui.search.SearchActivity;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ContainerActivity;

public class ToolBarBdas extends Toolbar {
    String name;
    int type=1;//1:title 2:search;3:title without button
    boolean bInit=true;
    Context myContext;
    EditText editText;
    LinearLayout panelSearchInfo;
    ListPopupWindow listPopupWindow;
    ImageButton advSearchButton;
    public ImageButton qRCodeButton;
    private List<String> listSearchDataCode;
    private List<String> listSearchDataName;
    public Boolean bOpenNew=false;

    public interface OnSetSearchOK {
        // 回调方法
        void onSearch(String name,String sSearch);
    }
    OnSetSearchOK mOnSetSearchOK;

    public interface OnConfirmOK {
        // 回调方法
        void onConfirmOK();
    }
    OnConfirmOK mOnConfirmOK;

    public ToolBarBdas(Context ct, AttributeSet attrs){
        super(ct,attrs);
        myContext=ct;

        TypedArray typedArray = myContext.obtainStyledAttributes(attrs, R.styleable.ToolBarBdas);
        int indexCount = typedArray.getIndexCount();

        this.setTitleTextColor(Color.WHITE);
        this.setContentInsetsAbsolute(0,0);
        this.setEnabled(false);
        //expandedTitleMarginBottom

        for (int i = 0; i < indexCount; i++) {
            int id = typedArray.getIndex(i);
            switch (id) {
                case R.styleable.ToolBarBdas_name:
                    name = typedArray.getString(id);
                    break;
                case R.styleable.ToolBarBdas_type:
                    type = typedArray.getInt(id,1);
                    break;
                case R.styleable.ToolBarBdas_init:
                    bInit=typedArray.getBoolean(id,true);
                    break;
                default:
                    break;
            }
        }

        if(bInit)
            setContent();
    }

    public void setType(int tp){
        type=tp;
    }

    public void setName(String tpname)
    {
        name=tpname;
    }

    protected BaseViewModel viewModel;
    public void setViewModel(BaseViewModel vm){
        viewModel=vm;
    }

    public void setContent(){
        if(type==1 || type==3) {
            LayoutInflater.from(myContext).inflate(R.layout.layout_toolbar, this, true);
            TextView tv=(TextView) findViewById(R.id.tv_toolbar_title);
            tv.setText(name);

            ImageView ivBack=findViewById(R.id.tv_toolbar_back);

            ivBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity=ActivityUtils.getActivityFromView(v);
                    activity.onBackPressed();
                }
            });

            ImageView ivOK=findViewById(R.id.toolbar_iv_ok);
            if(type==3) {
                ivOK.setVisibility(GONE);
            }
            else {
                ivOK.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnConfirmOK != null)
                            mOnConfirmOK.onConfirmOK();
                    }
                });
            }
        }

        if(type==2) {
            LayoutInflater.from(myContext).inflate(R.layout.layout_toolbar_search, this, true);
            editText=findViewById(R.id.editText_topSearch);
            panelSearchInfo=findViewById(R.id.panel_searchInfo);
            advSearchButton=findViewById(R.id.button_toolbar_advSearch);
            qRCodeButton=findViewById(R.id.button_toolbar_qrCode);

            panelSearchInfo.setVisibility(GONE);

            listSearchDataCode = new ArrayList<>();
            listSearchDataName = new ArrayList<>();

            listPopupWindow = new ListPopupWindow(myContext);
            listPopupWindow.setAdapter(new ArrayAdapter<String>(myContext, R.layout.layout_topsearch_item, listSearchDataName));
            listPopupWindow.setWidth(LayoutParams.WRAP_CONTENT);
            listPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);
            listPopupWindow.setAnchorView(editText);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
            listPopupWindow.setModal(true);//设置是否是模式

            editText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((EditText)v).setText("");
                }
            });

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.toString().equals(""))
                        return;
                    listSearchDataCode.clear();
                    listSearchDataName.clear();
                    listSearchDataCode.add(SystemConfig.BuildMutipleSearchData(SystemConfig.BuildSearchData("m0.1",s.toString()),SystemConfig.BuildSearchData("m0.2",s.toString())));
                    listSearchDataName.add("检索桥梁名称、代码 包含 "+s.toString());
                    if(s.length()>1){
                        LinkedHashMap<String,String> tplist=DataDict.getSearchList(s.toString());
                        for(String key:tplist.keySet())
                        {
                            listSearchDataCode.add(key);
                            listSearchDataName.add(tplist.get(key));
                        }
                    }
                    listPopupWindow.show();
                }
            });

            panelSearchInfo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(GONE);
                    editText.setVisibility(VISIBLE);
                    editText.setText("");
                    editText.requestFocus();
                }
            });

            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    //editText.setText(listData.get(position));
                    String[] tplist=listSearchDataName.get(position).split(" ");
                    addSearchInfo(tplist[tplist.length-1],listSearchDataCode.get(position));
                    listPopupWindow.dismiss();
                }
            });

            advSearchButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(viewModel!=null)
                        viewModel.startContainerActivity(FragmentSearch.class.getCanonicalName());
                }
            });

        }
    }

    public void addSearchInfo(String name,String code)
    {
        if(bOpenNew){
            viewModel.startContainerActivity(BrowseFragment.class.getCanonicalName(),SystemConfig.buildBundleSearchData(name,code));
            return;
        }
        panelSearchInfo.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(panelSearchInfo.getContext());
        ViewGroup view=(ViewGroup)inflater.inflate(R.layout.layout_search_info_item,panelSearchInfo,false);
        TextView tv=(TextView)view.getChildAt(0);
        tv.setText(name);
        ImageView iv=(ImageView)view.getChildAt(1);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //itemBinding.layoutDiseaseList.removeView(view);
                //itemViewModel.oALDiseaseList.remove(num);
                mOnSetSearchOK.onSearch("","");
                panelSearchInfo.removeView(view);
            }
        });
        panelSearchInfo.addView(view);
        editText.setVisibility(GONE);
        panelSearchInfo.setVisibility(VISIBLE);

        if(mOnSetSearchOK!=null)
            mOnSetSearchOK.onSearch(name,code);


    }

    public void setSearchListener(OnSetSearchOK onItemClickListener ){
        mOnSetSearchOK=onItemClickListener;
    }

    public void setConfirmOKListener(OnConfirmOK onItemClickListener ){
        mOnConfirmOK=onItemClickListener;
    }

    public void testOK()
    {
        return;
    }

}
