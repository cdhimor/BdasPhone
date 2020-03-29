package me.goldze.mvvmhabit.binding.viewadapter.BottomNavigationView;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by goldze on 2017/6/16.
 */

public class ViewAdapter {
    /**
     * @param bindingCommand //绑定监听
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"onNavigationItemSelected"}, requireAll = false)
    public static void setItemChanged(final BottomNavigationView bnv, final BindingCommand<MenuItem> bindingCommand) {
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                bindingCommand.execute(item);
                return false;
            }
        });
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"selectedID"}, requireAll = false)
    public static void selectedIDCommand(final BottomNavigationView bnv,final int position) {
        bnv.setSelectedItemId(bnv.getMenu().getItem(position).getItemId());
    }

}
