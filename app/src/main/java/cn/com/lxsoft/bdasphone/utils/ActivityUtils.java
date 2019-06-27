package cn.com.lxsoft.bdasphone.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import me.goldze.mvvmhabit.base.ContainerActivity;
import me.goldze.mvvmhabit.utils.ConvertUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ActivityUtils {

    public static Activity getActivityFromView(View view) {
        if (null != view) {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity) context;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        return null;
    }

    public static Object getActivityFromViewX(View view) {
        if (null != view) {
            Context context = view.getContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Activity) {
                    return (Activity) context;
                }
                else if(context instanceof ContainerActivity) {
                    return (ContainerActivity) context;
                }
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        return null;
    }


    public static void initRecyclerView(RecyclerView recyclerView,Context context)
    {
        //recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dv=new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        //dv.setDrawable(ContextCompat.getDrawable(this,R.drawable.shape_line_qllist));
        recyclerView.addItemDecoration(dv);

    }

    public static void showDrawerPanel(DrawerLayout mDrawerLayout,Activity activity,boolean bShow){
        if(bShow  && !mDrawerLayout.isDrawerOpen(Gravity.END))
            mDrawerLayout.openDrawer(Gravity.END);
        else if(!bShow && mDrawerLayout.isDrawerOpen(Gravity.END))
            mDrawerLayout.closeDrawer(Gravity.END);
        //((InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showDrawerPanelStart(DrawerLayout mDrawerLayout,Activity activity,boolean bShow){
        if(bShow  && !mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.openDrawer(Gravity.START);
        else if(!bShow && mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START);
        //((InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static Bitmap getBridgeIconBitmap(QiaoLiang ql, Resources res)
    {
        GradientDrawable grad = (GradientDrawable)ql.getBridgeDrawable();
        grad.setCornerRadius(24);
        grad.setSize(128,128);

        int w = grad.getIntrinsicWidth();
        int h = grad.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        grad.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        grad.draw(canvas);

        Bitmap iconBitmap=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.mipmap.ic_qiaoxing_gong),128,128,true);
        canvas.drawBitmap(iconBitmap,0,0,new Paint());

        return bitmap;

    }

    public static void refreshViewHeight(ViewGroup viewGroup){
        int height=0;
        for(int i=0;i<viewGroup.getChildCount();i++)
        {
            View view =viewGroup.getChildAt(i);
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            height += view.getMeasuredHeight();
            //int width = view.getMeasuredWidth();
        }

        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,height);
        viewGroup.setLayoutParams(layoutParams);

    }

}
