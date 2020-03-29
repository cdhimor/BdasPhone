package cn.com.lxsoft.bdasphone;

import android.app.Activity;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.ui.main.MainActivity;
import cn.com.lxsoft.bdasphone.ui.main.MainViewModel;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@Config(shadows = {ShadowLog.class}, application = AppApplication.class, sdk = 28)
@RunWith(RobolectricTestRunner.class)
public class ExampleUnitTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Before
    public void setUp() throws Exception {
        //将rxjava 的异步操作转化为同步
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

        //初始化Mockito
        MockitoAnnotations.initMocks(this);

        //获取测试的activity
        //loginActivity = Robolectric.setupActivity(LoginActivity.class);

        //gson = new Gson();
/*
//        因为我们不需要它LoginModel中的方法返回真正的值，只是需要mock它的方法返回我们想要的值，所以用@Mock
//        而LoginModule我们是需要它提供真是的view所以我们用spy
        LoginModule loginModule = Mockito.spy(new LoginModule(loginActivity));
        Mockito.when(loginModule.provideLoginModel(Mockito.any(LoginModel.class)))
                .thenReturn(loginModel);

//        我们mock出的module需要注入到测试的activity中
        DaggerLoginComponent
                .builder()
                .appComponent(ArmsUtils.obtainAppComponentFromContext(loginActivity))
                .loginModule(loginModule)
                .build()
                .inject(loginActivity);
*/
    }


    @Test
    public void addition_isCorrect() {
        Activity activity = Robolectric.setupActivity(MainActivity.class);

        //assertEquals(4, 2 + 2);
    }

}