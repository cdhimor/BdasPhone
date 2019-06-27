package cn.com.lxsoft.bdasphone.ui.examine;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.database.DataSession;
import cn.com.lxsoft.bdasphone.ui.browse.QiaoLiangListItemViewModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class FragmentCheckViewModel extends BaseViewModel {

    public QiaoLiangListItemViewModel qiaoLiangViewModel;

    public FragmentCheckViewModel(@NonNull Application application) {

        super(application);

        qiaoLiangViewModel=new QiaoLiangListItemViewModel(this,DataSession.getCurrentQiaoLiang());
        qiaoLiangViewModel.bCanClick=false;
    }

    //给ViewPager添加ObservableList
    public ObservableList<FragmentCheckItemViewModel> items = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<FragmentCheckItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.layout_check_viewpager_item);

    private int currentIndex=0;

    public FragmentCheckItemViewModel getCurrentItemViewModel(){
        return items.get(currentIndex);
    }

    List<String> titles;
    public void addPage() {
        titles=new ArrayList<>();
        titles.add("桥梁结构");
        titles.add("桥梁外观");
        titles.add("主梁");
        titles.add("斜拉索/杆");
        titles.add("桥面铺装");
        titles.add("伸缩缝");
        titles.add("人行道");
        titles.add("栏杆护栏");
        titles.add("排水设施");
        titles.add("墩台");
        titles.add("翼墙护坡");
        titles.add("交通设施");
        titles.add("观测点");
        titles.add("备注");

        items.clear();
        //模拟3个ViewPager页面
        for (int i = 0; i < titles.size(); i++) {
            FragmentCheckItemViewModel itemViewModel = new FragmentCheckItemViewModel(this, titles.get(i));
            items.add(itemViewModel);
        }

        adapter.fragmentCheckViewModel=this;
    }

    public List<String> getTitles()
    {
        return titles;
    }

    //给ViewPager添加PageTitle
    public final BindingViewPagerAdapter.PageTitles<FragmentCheckItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<FragmentCheckItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, FragmentCheckItemViewModel item) {
            return "条目" + position;
        }
    };


    //给ViewPager添加Adpter，使用自定义的Adapter继承BindingViewPagerAdapter，重写onBindBinding方法
    //public final BindingViewPagerAdapter<FragmentPatrolItemViewModel> adapter = new BindingViewPagerAdapter();
    public final FragmentCheckBindingAdapter adapter = new FragmentCheckBindingAdapter();
    //ViewPager切换监听
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer index) {

            ToastUtils.showShort("ViewPager切换：" + index);
            currentIndex=index;
        }
    });


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
