package cn.com.lxsoft.bdasphone.net;

import java.util.Map;

import cn.com.lxsoft.bdasphone.entity.BridgeChart;
import cn.com.lxsoft.bdasphone.entity.Check;
import cn.com.lxsoft.bdasphone.entity.CheckTemp;
import cn.com.lxsoft.bdasphone.entity.Construct;
import cn.com.lxsoft.bdasphone.entity.DanWei;
import cn.com.lxsoft.bdasphone.entity.Disease;
import cn.com.lxsoft.bdasphone.entity.Engineer;
import cn.com.lxsoft.bdasphone.entity.LuXian;
import cn.com.lxsoft.bdasphone.entity.Patrol;
import cn.com.lxsoft.bdasphone.entity.PatrolTemp;
import cn.com.lxsoft.bdasphone.entity.QiaoLiang;
import cn.com.lxsoft.bdasphone.entity.UpdateVersion;
import cn.com.lxsoft.bdasphone.entity.User;
import cn.com.lxsoft.bdasphone.entity.YearTest;
import cn.com.lxsoft.bdasphone.net.reponse.LoginResponse;
import cn.com.lxsoft.bdasphone.net.reponse.StateResponse;
import cn.com.lxsoft.bdasphone.net.request.LoginRequest;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by 眼神 on 2018/3/27.
 * <p>
 * 存放所有的Api
 */

public interface HttpApix {
    @GET("WebApp/app_department_list")
    Observable<ResponseList<DanWei>> getDepartmentData();

    @GET("WebApp/app_road_list")
    Observable<ResponseList<LuXian>> getRoadData();

    @GET("webapp/GetGridJson_for_gydw")
    Observable<ResponseList<User>> getUserData();

    @GET("updateversion.json")
    Observable<UpdateVersion> getApkUpdateData();

    @GET("report/qltj/Webapp_GetReport")
    Observable<ResponseList<BridgeChart>> getReportData(@Query("gydw") String gydw, @Query("fucname") String type);

    @GET("WebApp/app_bridge_list")
    Observable<ResponseList<QiaoLiang>> getBridgeData(@Query("page") Integer page, @Query("rows") Integer rows);

    @POST("comm/login")
    Observable<LoginResponse> checkLogin(@Body LoginRequest request);


    @GET("WebApp/Qlinfo_For_qldm")
    Observable<ResponseContent> getContentData(@Query("qldm") String qldm);

    @GET("WebApp/app_bridge_update_gis")
    Observable<ResponseInfo> setBridgeGis(@Query("DM") String dm, @Query("LAT") double lat, @Query("LNG") double lng);

    @POST("QLJCManage/Rcxc/Create")
    Observable<ResponseInfo> setPatrolDataEx(@Body RequestBase<Patrol> base);

    @FormUrlEncoded
    @POST("QLJCManage/Rcxc/Create")
    Observable<ResponseInfo> setPatrolData(@Field("objs") String base);

    @FormUrlEncoded
    @POST("QLJCManage/Jcjc/Create")
    Observable<ResponseInfo> setCheckData(@Field("objs") String base);

    @Multipart
    @POST("QLDangAnMange/FileForm/Upfile_Pic")
    Observable<ResponseInfo> upLoadCommMedia(@QueryMap Map<String, String> map, @Part MultipartBody.Part part);

    @Multipart
    @POST("QLJCManage/Rcxc/Upfile")
    Observable<ResponseInfo> upLoadPatrolMedia(@QueryMap Map<String, String> map, @Part MultipartBody.Part part);

    @Multipart
    @POST("QLJCManage/Jcjc/Upfile")
    Observable<ResponseInfo> upLoadCheckMedia(@QueryMap Map<String, String> map, @Part MultipartBody.Part part);

    @GET("QLJCManage/Rcxc/GetGridJson_For_NewTime")
    Observable<ResponseList<Patrol>> getPatrolData(@Query("page") Integer page, @Query("rows") Integer rows, @Query("sidx") String px, @Query("sord") String sord);

    @GET("QLJCManage/Rcxc/GetGridJson")
    Observable<ResponseList<PatrolTemp>> getPatrolTempData(@Query("qldm") String qldm, @Query("page") Integer page, @Query("rows") Integer rows, @Query("sidx") String px, @Query("sord") String sord);

    @GET("QLJCManage/jcjc/GetGridJson_For_NewTime")
    Observable<ResponseList<Check>> getCheckData(@Query("page") Integer page, @Query("rows") Integer rows, @Query("sidx") String px, @Query("sord") String sord);

    @GET("QLJCManage/jcjc/GetGridJson")
    Observable<ResponseList<CheckTemp>> getCheckTempData(@Query("qldm") String qldm, @Query("page") Integer page, @Query("rows") Integer rows, @Query("sidx") String px, @Query("sord") String sord);

    @GET("QLJCManage/dqjc/GetGridJson_For_NewTime")
    Observable<ResponseList<YearTest>> getYearTestData(@Query("page") Integer page, @Query("rows") Integer rows, @Query("sidx") String px, @Query("sord") String sord);

    @GET("QLDangAnMange/bujian/GetQlbj_list")
    Observable<ResponseList<Construct>> getConstructData(@Query("qldm") String qldm);

    @GET("QLJCManage/bjbh/getDqjcList_ForJcid")
    Observable<ResponseList<Disease>> getDiseaseData(@Query("jcId") String qldm);

    @GET("gcsManage/Gcs_view/GetFormJson")
    Observable<Engineer> getEngineerData(@Query("keyValue") String keyvalue);

    @GET("gcsManage/Gcs_view/GetGridJson")
    Observable<ResponseList<Engineer>> getEngineerListData(@Query("page") Integer page, @Query("rows") Integer rows, @Query("sidx") String px, @Query("sord") String sord);

    @GET("wnm/login/login_result.json")
    Observable<ResponseBody> getVPNLogin(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST("https://113.247.254.58:11111/wnm/login/login_result.json")
    Observable<ResponseInfo> getVPNLogin(@Field("user_name") String user, @Field("password") String pass);

    @GET("api")
    Observable<ResponseBody> getWeatherDataForMap(@QueryMap Map<String, String> map);



    //天气预报接口测试  @GET 不支持@Body类型
    //@POST("api")
    //Observable<ResponseBody> getWeatherDataForBody(@Body BaseRequestBean<WeatherRequestBean> requestBean);

    /**
     * 文件下载
     */
    @GET()
    @Streaming//使用Streaming 方式 Retrofit 不会一次性将ResponseBody 读取进入内存，否则文件很多容易OOM
    Call<ResponseBody> downloadFile(@Url String fileUrl);//返回值使用 ResponseBody 之后会对ResponseBody 进行读取

    @GET()
    @Streaming
    Observable<ResponseBody> downloadFileWithUrlSync(@Url String fileUrl);
}
