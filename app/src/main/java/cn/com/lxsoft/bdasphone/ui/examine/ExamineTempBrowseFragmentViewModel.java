package cn.com.lxsoft.bdasphone.ui.examine;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import cn.com.lxsoft.bdasphone.BR;
import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.app.SystemConfig;
import cn.com.lxsoft.bdasphone.database.DataBaseGreenImpl;
import cn.com.lxsoft.bdasphone.database.greendao.DataSession;
import cn.com.lxsoft.bdasphone.entity.Check;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.PatrolTemp;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.YearTest;
import cn.com.lxsoft.bdasphone.net.BridegeNetObserver;
import cn.com.lxsoft.bdasphone.net.RequestBase;
import cn.com.lxsoft.bdasphone.net.ResponseInfo;
import cn.com.lxsoft.bdasphone.net.ResponseList;
import cn.com.lxsoft.bdasphone.ui.browse.BaseBrowseFragmentViewModel;
import cn.com.lxsoft.bdasphone.ui.browse.QiaoLiangListItemViewModel;
import cn.com.lxsoft.bdasphone.ui.browse.SlideBrowseViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

import static android.support.constraint.Constraints.TAG;


public class ExamineTempBrowseFragmentViewModel extends BaseBrowseFragmentViewModel {
    public short nBrowseType=SystemConfig.ExamineStyle_Patrol;
    public QiaoLiang qiaoLiang;

    public ExamineTempBrowseFragmentViewModel(@NonNull Application application) {
        super(application);
        nActivityPosition=1;
    }

    protected void dealMainDataBinding(int pageIndex) {
        //pageIndexMain.set(pageIndex);

        Observable dbob=Observable.create(new ObservableOnSubscribe<List<PatrolTemp>>() {
            @Override
            public void subscribe(ObservableEmitter<List<PatrolTemp>> emitter) throws Exception{
                emitter.onNext(AppApplication.dataBase.getPatrolTempList(qiaoLiang.getDaiMa(),0,0));
                emitter.onComplete();

            }
        });

        //Observable netob=subscribeBase.getPatrolListData();


        dbob.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<Object>>() {
                            @Override
                            public void accept(List<Object> qiaoLiangs) {
                                pageIndexMain.set(pageIndex);
                                for (int i = 0; i < qiaoLiangs.size(); i++) {
                                    dealMainItem(qiaoLiangs.get(i));
                                }
                            }
                        }
                );
    }


    public void dealNetData(){
        DataBaseGreenImpl dataBase = AppApplication.dataBase;
        showDialog("开始接收检查数据");
        if(nBrowseType==SystemConfig.ExamineStyle_Patrol) {
            List<PatrolTemp> patrolTemplist = dataBase.getPatrolTempList("", 0, 1);
            if (patrolTemplist.size() > 0) {
                RequestBase<PatrolTemp> requestData = new RequestBase<>();
                requestData.AddItems.addAll(patrolTemplist);
                subscribeBase.setPatrolTempData(requestData).subscribe(new BridegeNetObserver<ResponseInfo>() {
                    @Override
                    public void onNext(ResponseInfo res) {
                        if (res.checkSuccess()) {
                            showDialog("本地历史数据提交成功");
                        }
                        dealNetDataEx();
                    }
                });
            }
            else
                dealNetDataEx();
        }
    }


    public void dealNetDataEx(){
        DataBaseGreenImpl dataBase = AppApplication.dataBase;
        if(nBrowseType==SystemConfig.ExamineStyle_Patrol) {
            Log.d(TAG, "======================开始获取patrollist网络数据 " + Thread.currentThread().getName());
                subscribeBase.getPatrolListData(1, SystemConfig.DATA_MAX_NUM)
                        .observeOn(Schedulers.io())
                        .doOnNext(new Consumer<ResponseList<Patrol>>() {
                            @Override
                            public void accept(ResponseList<Patrol> resData) throws Exception {
                                Log.d(TAG, "======================初始化patrollist数据 " + Thread.currentThread().getName());

                                dataBase.insertPatrolListData(resData.rows);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BridegeNetObserver<ResponseList<Patrol>>() {
                            @Override
                            public void onNext(ResponseList<Patrol> resData) {
                                //dealMainDataBinding(0);
                                onToolbarDiseaseSearch(searchType);
                                dismissDialog();
                                //dismissRefresh.call();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                super.onError(e);
                                dismissDialog();
                            }
                        });
        }
        else if(nBrowseType==SystemConfig.ExamineStyle_Check){
            subscribeBase.getCheckListData(1, SystemConfig.DATA_MAX_NUM)
                    .observeOn(Schedulers.io())
                    .doOnNext(new Consumer<ResponseList<Check>>() {
                        @Override
                        public void accept(ResponseList<Check> resData) throws Exception {
                            Log.d(TAG, "======================初始化patrollist数据 " + Thread.currentThread().getName());
                            dataBase.insertCheckListData(resData.rows, false);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BridegeNetObserver<ResponseList<Check>>() {
                        @Override
                        public void onNext(ResponseList<Check> resData) {
                            onToolbarDiseaseSearch(searchType);
                            dismissDialog();
                            //dismissRefresh.call();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            super.onError(e);
                            dismissDialog();
                        }
                    });
        }
        else if(nBrowseType==SystemConfig.ExamineStyle_Test){
            subscribeBase.getYearTestListData(1, SystemConfig.DATA_MAX_NUM)
                    .observeOn(Schedulers.io())
                    .doOnNext(new Consumer<ResponseList<YearTest>>() {
                        @Override
                        public void accept(ResponseList<YearTest> resData) throws Exception {
                            Log.d(TAG, "======================初始化testlist数据 " + Thread.currentThread().getName());
                            dataBase.insertYearTestListData(resData.rows);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BridegeNetObserver<ResponseList<YearTest>>() {
                        @Override
                        public void onNext(ResponseList<YearTest> resData) {
                            onToolbarDiseaseSearch(searchType);
                            dismissDialog();
                            //dismissRefresh.call();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            super.onError(e);
                            dismissDialog();
                        }
                    });
        }

    }

    @Override
    public void onCreate() {

        //whereConditionMain=QiaoLiangDao.Properties.DaiMa.isNotNull();
        //propertyOrderMain=QiaoLiangDao.Properties.DaiMa;

        //dealObservableData();
        //AppApplication.dataBase.deletePatrolListData();

        observableListMain = new ObservableArrayList<>();
        itemBindingMain = ItemBinding.of(BR.viewModel, R.layout.layout_examine_item);
        adapterMain = new BindingRecyclerViewAdapter<>();

        onToolbarDiseaseSearch(0);

        if(dataNum==0)
            dealNetData();

        /*
        slideBrowseViewModel.setItemClickListener(new SlideBrowseViewModel.OnItemClickListener() {
            @Override
            public void onItemClick(short listType, Object object) {
                if(listType==SystemConfig.SlideList_Type_Path){
                    setSearchData("m0.4",(((LuXian)object).getBianHao()));
                }
                else if(listType==SystemConfig.SlideList_Type_Dept) {
                    setSearchData("m0.3",(((DanWei)object).getDaiMa()));
                }
                dealMainDataBinding(0);
            }
        });
        */

        //slideBrowseViewModel.onCreate();
    }

    protected void dealMainItem(Object tpobj) {
        QiaoLiang tpEntity = (QiaoLiang) tpobj;
        ExamineListItemViewModel itemViewModel = new ExamineListItemViewModel(ExamineTempBrowseFragmentViewModel.this,tpEntity,nBrowseType);
        //itemViewModel.parentViewModel=ExamineBrowseFragmentViewModel.this;
        //双向绑定动态添加Item
        observableListMain.add(itemViewModel);
        itemViewModel.setItemClickListener(new QiaoLiangListItemViewModel.OnItemClickListener() {
            @Override
            public void onClick(QiaoLiang tpQiaoLiang) {
                //oStringChooseQiaoLiang.set(tpQiaoLiang.getDaiMa());
                //DataSession.setCurrentQiaoLiang(tpQiaoLiang);
                DataSession.setCurrentQiaoLiang(tpQiaoLiang);
                switch (nBrowseType){
                    case SystemConfig.ExamineStyle_Patrol:
                        //mBundle.putString("id",tpQiaoLiang.getPatrolID());
                        startContainerActivity(FragmentPatrol.class.getCanonicalName());
                        break;
                    case SystemConfig.ExamineStyle_Check:
                        startContainerActivity(FragmentCheck.class.getCanonicalName());
                        break;
                    case SystemConfig.ExamineStyle_Test:
                        startContainerActivity(FragmentTest.class.getCanonicalName());
                        break;
                }
            }
        });
    }

    int searchType=1;

    public void onToolbarDiseaseSearch(int type)
    {
        searchType=type;
        dataNum=AppApplication.dataBase.setExamineSearchData(nBrowseType,searchType);
        pageCount=(int)Math.ceil((float)dataNum/SystemConfig.PageSizeBridge);
        pageIndexMain.set(-1);

        observableListMain.clear();
        if(dataNum!=0)
            dealMainDataBinding(0);
    }

    public void onRefreshCommand(){
            dealNetData();
    }

    public BindingCommand onLoadMoreCommandQiaoLiang = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if(pageIndexMain.get()==pageCount-1)
                return;
            else
                dealMainDataBinding(pageIndexMain.get()+1);
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}