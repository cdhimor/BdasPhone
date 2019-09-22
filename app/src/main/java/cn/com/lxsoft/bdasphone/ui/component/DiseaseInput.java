package cn.com.lxsoft.bdasphone.ui.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;

import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.entity.DataDict;
import cn.com.lxsoft.bdasphone.entity.DiseaseInfo;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import me.goldze.mvvmhabit.utils.ConvertUtils;

public class DiseaseInput extends LinearLayout {
    String bhData="";

    public DiseaseInput(Context context, AttributeSet attrs) {
        super(context, attrs);

        TextView tv = new TextView(this.getContext());
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,ConvertUtils.dp2px(32));
        tv.setLayoutParams(layoutParams);
        tv.setText("病害");
        this.addView(tv);
    }

    public void setData(List<DiseaseInfo> dList){
        if(dList.isEmpty() || dList==null)
            return;
        this.removeAllViews();
        for(int i=0;i<dList.size();i++)
        {
            DiseaseInfo di=dList.get(i);
            addDiseaseLabel(di.getDingXingMiaoShu(),di.getDingLiangMiaoShu());
        }
        bhData=dList.get(0).getBianMa();
    }

    public void setData(String bhID)
    {
        if(bhID.isEmpty() || bhID==null)
            return;
        this.removeAllViews();
        bhData=bhID;
        DiseaseInfo di=DataDict.getBHInfo(bhID);
        if(di!=null)
            addDiseaseLabel(di.getDingXingMiaoShu(),di.getDingLiangMiaoShu());
    }

    public String getData(){
        return bhData;
    }

    protected void addDiseaseLabel(String dxStr,String dlStr){
        TextView tv = new TextView(this.getContext());
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(4));
        int padding=ConvertUtils.dp2px(4);
        tv.setPadding(padding,padding,padding,padding);
        tv.setLayoutParams(layoutParams);

        tv.setBackgroundResource(R.drawable.shape_rect_noborder_lightgray);
        tv.setTextColor(Color.BLACK);


        if(!dlStr.isEmpty()) {
            SpannableString strDX = new SpannableString(dxStr+"（*）");
            ConvertClickText ctDX=new ConvertClickText(this.getContext());
            CommClickText ctcDX=new CommClickText(this.getContext());

            SpannableString strDL = new SpannableString(dlStr+"（*）");
            ConvertClickText ctDL=new ConvertClickText(this.getContext());
            CommClickText ctcDL=new CommClickText(this.getContext());

            ctDX.setData(strDL,tv);
            ctDL.setData(strDX,tv);

            strDX.setSpan(ctDX,strDX.length()-3,strDX.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            strDL.setSpan(ctDL,strDL.length()-3,strDL.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            strDX.setSpan(ctcDX,0,strDX.length()-3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            strDL.setSpan(ctcDL,0,strDL.length()-3,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            tv.setText(strDX);
            tv.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
            tv.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明

        }
        else
            tv.setText(dxStr);
        this.addView(tv);
    }

    class CommClickText extends ClickableSpan {
        private Context context;

        public CommClickText(Context context) {
            this.context = context;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            //设置文本的颜色
            ds.setColor(getResources().getColor(R.color.black));
            //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            ((LinearLayout)(widget.getParent())).performClick();
        }
    }

    class ConvertClickText extends ClickableSpan {
        private Context context;
        TextView textView;
        SpannableString spanStr;

        public ConvertClickText(Context context) {
            this.context = context;
        }

        public void setData(SpannableString str, TextView et){
            textView=et;
            spanStr=str;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            //设置文本的颜色
            ds.setColor(getResources().getColor(R.color.colorPrimary));
            //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            textView.setText(spanStr);
        }
    }
}
