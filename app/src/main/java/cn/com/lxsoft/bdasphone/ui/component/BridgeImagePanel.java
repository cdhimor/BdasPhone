package cn.com.lxsoft.bdasphone.ui.component;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.lxsoft.bdasphone.R;
import cn.com.lxsoft.bdasphone.app.AppApplication;
import cn.com.lxsoft.bdasphone.entity.ImageData;
import cn.com.lxsoft.bdasphone.net.BridegeNetObserver;
import cn.com.lxsoft.bdasphone.net.ResponseInfo;
import cn.com.lxsoft.bdasphone.net.SubscribeBase;
import cn.com.lxsoft.bdasphone.ui.examine.FragmentPatrol;
import cn.com.lxsoft.bdasphone.ui.main.MainActivity;
import cn.com.lxsoft.bdasphone.utils.ActivityUtils;
import cn.com.lxsoft.bdasphone.utils.FileUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.ImageUtils;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;

public class BridgeImagePanel extends LinearLayout {
    LinearLayout listPanel;
    ImageView addButton;
    BaseFragment fragment;
    List<ImageData> imgList;
    SubscribeBase subscribeBase;
    String bridgeID;


    BaseFragment.OnLoadImageOK frameLoadImageOK;

    public interface OnLoadImage {
        // 回调方法
        boolean onLoad(List<Uri> tplist);
    }
    public OnLoadImage onLoadImageOK;

    public interface OnLoadImageData {
        // 回调方法
        boolean onLoad(List<ImageData> tplist);
    }
    public OnLoadImageData onLoadImageDataOK;

    public BridgeImagePanel(Context ct, AttributeSet attrs) {
        super(ct, attrs);

        LayoutInflater.from(ct).inflate(R.layout.layout_image_panel, this, true);

        listPanel = findViewById(R.id.panel_imageList);

        addButton = findViewById(R.id.patrol_add_image);
    }

    public void initNet(SubscribeBase sub,String bid){
        subscribeBase=sub;
        bridgeID=bid;
    }

    public void init(BaseFragment fg) {
        fragment = fg;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChooseDialog();
            }
        });

        //((BaseFragment)fragment)
        frameLoadImageOK= new BaseFragment.OnLoadImageOK() {
            @Override
            public void onLoad(Intent intent) {
                List<Uri> list = Matisse.obtainResult(intent);
                imgList = ImageData.createImageList(list,true);
                /*
                for (int i = 0; i < imgList.size(); i++) {
                    uploadToNet(imgList.get(i));
                }
                */
                if(onLoadImageDataOK!=null && onLoadImageDataOK.onLoad(imgList)) {
                    showImage(imgList);
                }
                if(onLoadImageOK!=null && onLoadImageOK.onLoad(list)) {
                        showImage(imgList);
                }
                //viewModel.addDiseaseImages(list);
            }
        };
    }

    public void uploadToNet(ImageData imageData){
        if(subscribeBase==null || bridgeID==null)
            return;
        Observer<ResponseInfo> ob=new BridegeNetObserver<ResponseInfo>(){
            @Override
            public void onNext(ResponseInfo res) {
                imageData.setNetNameFromNet(res.data);
            }
        };
        Observable.just(imageData)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap(new Function< ImageData, Observable<ResponseInfo> >() {
                @Override
                public Observable<ResponseInfo> apply(ImageData data) throws Exception {
                    Context context=AppApplication.getInstance().getBaseContext();
                    File file = ImageUtils.compressBitmap(FileUtils.getFilePathByUri(context,imageData.getUri()), ImageUtils.getDiskCacheDir(context), "patrol");
                    //File file = new File(FileUtils.getFilePathByUri(getApplication().getBaseContext(), patrolData.images.get(i).getUri()));
                    return subscribeBase.uploadCommMedia(bridgeID, file);
                }
            })
            .observeOn(Schedulers.io())
            .subscribe(ob);
    }

    public void openChooseDialog(){
        ActivityUtils.openImageLoadDialog(BridgeImagePanel.this, fragment);
        fragment.onLoadImageOK=frameLoadImageOK;
    }

    public void setButtonHidden(){
        addButton.setVisibility(GONE);
    }

    public void showImage(List<ImageData> list){
        if(list==null)
            return;
        imgList=list;
        //    imgList=new ArrayList<>();
        //imgList.addAll(list);
        for(int i=0;i<list.size();i++){
            addImageView(list.get(i));
        }
    }

    public void deleteImageData(ImageData imageData){
        if(imgList!=null)
            imgList.remove(imageData);
    }

    protected void addImageView(ImageData imageData){
        ImageViewBridge imageView = new ImageViewBridge(listPanel.getContext(), fragment);
        imageView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MaterialDialogUtils.showBasicDialogNoTitle(listPanel.getContext(),"是否确定删除该图片？").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteImageData(imageData);
                        listPanel.removeView(imageView);
                    }
                }).show();
                return false;
            }
        });
        listPanel.addView(imageView);
        imageView.setImageData(imageData);
        /*
        if(imageData.bUpLoad){
            FrameLayout layout = new FrameLayout(listPanel.getContext());
            FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            listPanel.addView(layout,layoutParam);
            layout.addView(imageView);
            ImageView delbut = new ImageView(listPanel.getContext());
            FrameLayout.LayoutParams delParam = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            delParam.gravity=Gravity.BOTTOM | Gravity.RIGHT;
            delbut.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete_gray_24dp)); //可用
            layout.addView(delbut,delParam);
            delbut.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteImageData(imageData);
                    listPanel.removeView(layout);
                }
            });
        }
        else {
            listPanel.addView(imageView);

        }
        */
        imageView.setImageData(imageData);
    }

    public class ImageViewBridge extends AppCompatImageView {
        public ImageData imageData;
        private Fragment fragment;

        public ImageViewBridge(Context context, Fragment fg) {
            super(context);

            LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 120);
            setScaleType(ImageView.ScaleType.CENTER_CROP);
            para.setMargins(32, 16, 16, 32);
            setLayoutParams(para);
            //setBackgroundResource(R.drawable.shape_rect_border_lightgray);

            fragment = fg;
        }

        public void setImageData(ImageData data) {
            imageData = data;
            RoundedCorners roundedCorners = new RoundedCorners(20);
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);//.override(300, 300);

            //RequestOptions myOptions = new RequestOptions().transform(new GlideRoundImage(this.getContext(),20));
            //RequestOptions myOptions = new RequestOptions().transform(new GlideRoundTransform(this.getContext(),30));
            Glide.with(fragment)
                    .load(imageData.getImgNetUri())
                    .apply(options)
                    .into(this);
            data.bShowInList = true;
        }


        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(width, (int) (width * 3 / 4));
        }

    }
}