package cn.com.lxsoft.bdasphone.ui.browse;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataSession;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class ContentFragmentViewModel extends BaseViewModel {
    public String sBridgeName;
    public QiaoLiang bridge;

    public ObservableList<ContentViewPagerViewModel> items = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<ContentViewPagerViewModel> itemBinding = ItemBinding.of(new OnItemBind<ContentViewPagerViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, ContentViewPagerViewModel item) {
            //通过item的类型, 动态设置Item加载的布局
            switch (item.contentType)
            {
                case SystemConfig.ContentPager_Type_JCSB:
                    itemBinding.set(BR.viewModel, R.layout.fragment_content_jcsb);
                    break;
                case SystemConfig.ContentPager_Type_JGSJ:
                    itemBinding.set(BR.viewModel, R.layout.fragment_content_jgsj);
                    break;
                case SystemConfig.ContentPager_Type_JJZB:
                    itemBinding.set(BR.viewModel, R.layout.fragment_content_jjzb);
                    break;
                case SystemConfig.ContentPager_Type_DLXX:
                    itemBinding.set(BR.viewModel, R.layout.fragment_gis_content);
                    break;
            }
        }
    });
    public final ContentViewPagerAdapter adapter = new ContentViewPagerAdapter();

    public ContentFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        bridge=DataSession.getCurrentQiaoLiang();
        sBridgeName= bridge.getMingCheng();

    }

    public List<String> titles;
    public void addPage() {
        titles=new ArrayList<>();
        titles.add("基础识别");
        titles.add("结构数据");
        titles.add("经济指标");
        titles.add("地理信息");

        items.clear();

        ContentViewPagerViewModel itemViewModel = new ContentViewPagerViewModel(this,SystemConfig.ContentPager_Type_JCSB,bridge);
        items.add(itemViewModel);

        itemViewModel = new ContentViewPagerViewModel(this,SystemConfig.ContentPager_Type_JGSJ,bridge);
        items.add(itemViewModel);

        itemViewModel = new ContentViewPagerViewModel(this,SystemConfig.ContentPager_Type_JJZB,bridge);
        items.add(itemViewModel);

        itemViewModel = new ContentViewPagerViewModel(this,SystemConfig.ContentPager_Type_DLXX,bridge);
        items.add(itemViewModel);

        adapter.contentFragmentViewModel=this;
        //adapter.contentFragment=this.get
    }

    //登录按钮的点击事件
    public BindingCommand onConfirmClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            items.get(3).saveOrReplaceData();
            ToastUtils.showShort("更新成功！");
        }
    });


}
