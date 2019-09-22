package cn.com.lxsoft.bdasphone.ui.browse;

import android.databinding.ObservableField;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.utils.ConvertUtils;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class QiaoLiangListItemViewModel extends ItemViewModel<BaseViewModel> {
    public ObservableField<QiaoLiang> entity = new ObservableField<>();
    public String mingCheng;
    public String leiXing;
    public String luXian;
    public String info4;
    public boolean bShowJieGou=true;
    public String jieGou;
    public ObservableField<Drawable> drawableBack=new ObservableField<>();
    public Drawable drawableIcon;


    public boolean bCanClick=true;

    public interface OnItemClickListener {
        // 回调方法
        void onClick(QiaoLiang tpQiaoLiang);
    }

    OnItemClickListener mOnItemClickListener;


    public QiaoLiangListItemViewModel(@NonNull BaseViewModel viewModel, QiaoLiang tpEntity)
    {
        super(viewModel);
        mingCheng=tpEntity.getMingCheng();
        leiXing=tpEntity.getleiXingInfo();
        if(tpEntity.getLuXian()!=null)
            luXian=tpEntity.getLuXianID().concat(tpEntity.getLuXian().getMingCheng());
        else
            luXian=tpEntity.getLuXianID();
        if(tpEntity.getDanWei()!=null)
            info4=tpEntity.getDanWei().getMingCheng();

        if(bShowJieGou)
            //jieGou=tpEntity.getjieGouInfo().concat(" ∙ ").concat(Float.toString(tpEntity.getZhuangHao())).concat("Z ∙ ").concat(Float.toString(tpEntity.getQiaoChang())).concat("L ∙ ").concat(Float.toString(tpEntity.getQiaoKuan())).concat("W ∙ ").concat(Float.toString(tpEntity.getQiaoGao()).concat("H"));
            jieGou=tpEntity.getjieGouInfo().concat(" ∙ ").concat(Float.toString(tpEntity.getZhuangHao())).concat("Z ∙ ").concat(Float.toString(tpEntity.getQiaoChang())).concat("L ∙ ").concat(Float.toString(tpEntity.getQiaoKuan())).concat("W ∙ ").concat(ConvertUtils.getDateNameOnlyYear(tpEntity.getJianQiaoNianYue()));

        setPingJiaColor(tpEntity.getPingJi());

        //grad.

        int iconID=R.mipmap.ic_qiaoxing_liang;
        String jg=tpEntity.getjieGouPJInfo();
        if(!ConvertUtils.isSpace(jg)){
            if(jg.equals("104"))
                iconID = R.mipmap.ic_qiaoxing_zhuhe;
            else {
                switch (jg.substring(0, 1)) {
                    case "2":
                        iconID = R.mipmap.ic_qiaoxing_gong;
                        break;
                    case "5":
                        iconID = R.mipmap.ic_qiaoxing_xiela;
                        break;
                    case "4":
                        iconID = R.mipmap.ic_qiaoxing_xuansuo;
                        break;
                }
            }
        }
        drawableIcon=ContextCompat.getDrawable(viewModel.getApplication(),iconID);


        entity.set(tpEntity);

    }

    public void setPingJiaColor(int pj){
        int color=SystemConfig.getPingJiColor(pj);
        GradientDrawable grad = new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{color, Color.WHITE});
        grad.setCornerRadius(24);
        drawableBack.set(grad);
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener ){
        mOnItemClickListener=onItemClickListener;
    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //if(bCanClick)
               mOnItemClickListener.onClick(entity.get());
        }
    });

}
