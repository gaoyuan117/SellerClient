package com.kaichaohulian.baocms.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.utils.OperationBimap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 图片浏览器
 * Created by ljl on 2016/12/11.
 */
public class ImagePagerActivity extends Activity{
    public static final String INTENT_IMGURLS = "imgurls";
    public static final String INTENT_POSITION = "position";
    public static final String INTENT_IMAGESIZE = "imagesize";

    private List<View> guideViewList = new ArrayList<View>();
    private LinearLayout guideGroup;
    public ImageSize imageSize;
    private int startPos;
    private ArrayList<String> imgUrls;

    public static void startImagePagerActivity(Context context, List<String> imgUrls, int position, ImageSize imageSize){
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<String>(imgUrls));
        intent.putExtra(INTENT_POSITION, position);
        intent.putExtra(INTENT_IMAGESIZE, imageSize);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepager);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        guideGroup = (LinearLayout) findViewById(R.id.guideGroup);

        getIntentData();

        ImageAdapter mAdapter = new ImageAdapter(this);
        mAdapter.setDatas(imgUrls);
        mAdapter.setImageSize(imageSize);
        viewPager.setAdapter(mAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<guideViewList.size(); i++){
                    guideViewList.get(i).setSelected(i == position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setCurrentItem(startPos);

        addGuideView(guideGroup, startPos, imgUrls);
    }

    private void getIntentData() {
        startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
        imgUrls = getIntent().getStringArrayListExtra(INTENT_IMGURLS);
        imageSize = (ImageSize) getIntent().getSerializableExtra(INTENT_IMAGESIZE);
    }

    private void addGuideView(LinearLayout guideGroup, int startPos, ArrayList<String> imgUrls) {
        if(imgUrls!=null && imgUrls.size()>0){
            guideViewList.clear();
            for (int i=0; i<imgUrls.size(); i++){
                View view = new View(this);
                view.setBackgroundResource(R.drawable.selector_guide_bg);
                view.setSelected(i == startPos);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.dimens_6),
                        getResources().getDimensionPixelSize(R.dimen.dimens_6));
                layoutParams.setMargins(10, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
            }
        }
    }

    private class ImageAdapter extends PagerAdapter {

        private List<String> datas = new ArrayList<String>();
        private LayoutInflater inflater;
        private Context context;
        private ImageSize imageSize;
        private ImageView smallImageView = null;

        public void setDatas(List<String> datas) {
            if(datas != null )
                this.datas = datas;
        }
        public void setImageSize(ImageSize imageSize){
            this.imageSize = imageSize;
        }

        public ImageAdapter(Context context){
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if(datas == null) return 0;
            return datas.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = inflater.inflate(R.layout.item_pager_image, container, false);
            if(view != null){
                final PhotoView imageView = (PhotoView) view.findViewById(R.id.image);

                if(imageSize!=null){
                    //预览imageView
                    smallImageView = new ImageView(context);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(imageSize.getWidth(), imageSize.getHeight());
                    layoutParams.gravity = Gravity.CENTER;
                    smallImageView.setLayoutParams(layoutParams);
                    smallImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ((FrameLayout)view).addView(smallImageView);
                }

                //loading
                final ProgressBar loading = new ProgressBar(context);
                FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingLayoutParams.gravity = Gravity.CENTER;
                loading.setLayoutParams(loadingLayoutParams);
                ((FrameLayout)view).addView(loading);

                final String imgurl = datas.get(position);


                Glide.with(context)
                        .load(imgurl)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存多个尺寸
                        .thumbnail(0.1f)//先显示缩略图  缩略图为原图的1/10
                        .into(new SimpleTarget<Bitmap>() {

                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                                                       /* if(smallImageView!=null){
                                    smallImageView.setVisibility(View.VISIBLE);
                                    Glide.with(context).load(imgurl).into(smallImageView);
                                }*/
                                loading.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                                          /*if(smallImageView!=null){
                                    smallImageView.setVisibility(View.GONE);
                                }*/
                                loading.setVisibility(View.GONE);
                            }

                            @Override
                            public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                loading.setVisibility(View.GONE);
                                imageView.setImageBitmap(resource);
                                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View view) {
                                        showCameraBox(resource);
                                        return false;
                                    }
                                });
                                imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                                    @Override
                                    public void onPhotoTap(View view, float x, float y) {
                                        finish();
                                    }
                                });
                                /*if(smallImageView!=null){
                                    smallImageView.setVisibility(View.GONE);
                                }*/
                            }

                        });


//                Glide.with(context)
//                        .load(imgurl)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存多个尺寸
//                        .thumbnail(0.1f)//先显示缩略图  缩略图为原图的1/10
//                        .into(new GlideDrawableImageViewTarget(avatarIV) {
//                            @Override
//                            public void onLoadStarted(Drawable placeholder) {
//                                super.onLoadStarted(placeholder);
//                               /* if(smallImageView!=null){
//                                    smallImageView.setVisibility(View.VISIBLE);
//                                    Glide.with(context).load(imgurl).into(smallImageView);
//                                }*/
//                                loading.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                                super.onLoadFailed(e, errorDrawable);
//                                /*if(smallImageView!=null){
//                                    smallImageView.setVisibility(View.GONE);
//                                }*/
//                                loading.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onResourceReady(final GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                                super.onResourceReady(resource, animation);
//                                loading.setVisibility(View.GONE);
//                                avatarIV.setOnLongClickListener(new View.OnLongClickListener() {
//                                    @Override
//                                    public boolean onLongClick(View view) {
//                                        showCameraBox(resource);
//                                        return false;
//                                    }
//                                });
//                                /*if(smallImageView!=null){
//                                    smallImageView.setVisibility(View.GONE);
//                                }*/
//                            }
//                        });

                container.addView(view, 0);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        private TextView saveBitmap, tvFromAlbum, tvCancel;
        public Dialog mDialog;
        public Window window;
        public View viewGroup;
        private void showCameraBox(final Bitmap resource) {
            mDialog = new Dialog(context, R.style.camera);
            mDialog.show();
            window = mDialog.getWindow();
            viewGroup = LayoutInflater.from(context).inflate(R.layout.dialog_camera, null);
            window.setContentView(viewGroup);
            saveBitmap = (TextView) viewGroup.findViewById(R.id.tv_take_photo);
            tvFromAlbum = (TextView) viewGroup.findViewById(R.id.tv_from_album);
            tvCancel = (TextView) viewGroup.findViewById(R.id.tv_cancel);
            saveBitmap.setVisibility(View.GONE);
            tvFromAlbum.setText("保存图片");
            tvFromAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OperationBimap.getInstance().saveImageToGallery(ImagePagerActivity.this, resource);
                    mDialog.dismiss();
                }
            });
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                }
            });
        }

    }

    public static class ImageSize implements Serializable{

        private int width;
        private int height;

        public ImageSize(int width, int height){
            this.width = width;
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }


}
