package cn.com.lxsoft.bdasphone.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.ui.component.LaybelLayout;
import cn.com.lxsoft.bdasphone.utils.ConvertUtils;

public class SearchActivity extends AppCompatActivity {
    LinearLayout panelSearchItem;
    List<SearchCheckBox> checkBoxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);

        panelSearchItem=findViewById(R.id.layout_searchPanelItem);
        checkBoxList=new ArrayList<>();

        addSearchPanelItem("1.1","桥梁类型");
        addSearchPanelItem("1.4","公路等级");

        findViewById(R.id.button_searchOK).setOnClickListener(new View.OnClickListener() {
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
                        resCode+=checkBoxList.get(i).code+",";
                    }
                }
                if(num>0) {
                    resName += num + "个条件";
                    resCode = resCode.substring(0, resCode.length() - 1);
                    Intent intent = new Intent();
                    intent.putExtra("data_return", resName + "#" + resCode);
                    setResult(RESULT_OK, intent);
                }
                else {
                    setResult(RESULT_CANCELED);
                }
                finish();
            }
        });
        //LaybelLayout laybelLayout = (LaybelLayout) findViewById(R.id.laybelLayout);
        //laybelLayout.setAdapter(new LaybelLayout.Adapter(Content.content));
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    protected void addSearchPanelItem(String dictID,String name){
        List<DataDict.DictItem> list=DataDict.getDictList(dictID);
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams textViewLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //textViewLayout.setMargins(0,0,0,16);
        textView.setLayoutParams(textViewLayout);
        textView.setText(name);
        //textView.setTextColor(getResources().getColor(R.color.black));
        textView.setTextSize(14);
        panelSearchItem.addView(textView);

        LaybelLayout laybelLayout=new LaybelLayout(this);
        LinearLayout.LayoutParams laybelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //laybelParams.setMargins(0,16,0,16);
        laybelLayout.setLayoutParams(laybelParams);
        laybelLayout.setLinePadding(8);
        panelSearchItem.addView(laybelLayout);

        for(int i=0;i<list.size();i++){
            SearchCheckBox checkBox=new SearchCheckBox(this);
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
            checkBox.code="m"+dictID+":"+list.get(i).code;
            checkBox.name=name+" 是 "+list.get(i).name;
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
