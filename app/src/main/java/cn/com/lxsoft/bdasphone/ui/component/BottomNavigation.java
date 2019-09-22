package cn.com.lxsoft.bdasphone.ui.component;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.view.MenuItem;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.ui.browse.BrowseActivity;
import cn.com.lxsoft.bdasphone.ui.main.MainActivity;
import cn.com.lxsoft.bdasphone.utils.BottomNavigationViewHelper;

public class BottomNavigation extends BottomNavigationView implements BottomNavigationView.OnNavigationItemSelectedListener {

    public BottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        BottomNavigationViewHelper.disableShiftMode(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        //this.selec
        return true;
    }
}
