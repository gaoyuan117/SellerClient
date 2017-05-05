package com.kaichaohulian.baocms.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Environment;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.utils.CrashHandler;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferenceSettings;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferences;
import com.kaichaohulian.baocms.ecdemo.common.utils.FileAccessor;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.ecdemo.ui.huawei.PustDemoActivity;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.FileUtil;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 程序基类
 * Created by ljl on 2016/12/11.
 */
public class MyApplication extends MultiDexApplication {
    private String TAG = "MyApplication";
    private static MyApplication mInstance;
    private static PustDemoActivity mPustTestActivity = null;

    // 保存图片到SD卡 path
    public String SAVE_PIC_PATH;
    // 保存图片到SD卡 path
    public String VOICE_PATH;
    // 当前登录的用户对象
    public UserInfo UserInfo = null;
    // 可全局使用的“主线程handler”
    public static Handler sGlobalHandler = new Handler();

    private final int UPDATE_TIME = 5000;
    private int LOCATION_COUTNS = 0;
    private LocationClient locationClient;
    public BDLocation BDLocation;

    public static MyApplication getInstance() {
        return mInstance;
    }

    public List<ContactFriendsEntity> contactList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        SAVE_PIC_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Buyers/saveImage/";
        VOICE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Buyers/voice/";

        FileUtil.getInstance().createDirIfNotExist(SAVE_PIC_PATH);
        SharedPrefsUtil.putValue(getApplicationContext(), "mychange", "850.50");
        intiYunTx();
        BaiduSdk();

        // 设置尺寸信息
        getDeviceScreenSize();
    }

    public void BaiduSdk() {
        locationClient = new LocationClient(this);
        //设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");       //设置返回值的坐标类型。
        option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级
        option.setScanSpan(UPDATE_TIME);    //设置定时定位的时间间隔。单位毫秒
        option.setAddrType("all");
        locationClient.setLocOption(option);
        //注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    return;
                }
                if (LOCATION_COUTNS < 1) {
                    StringBuffer sb = new StringBuffer(256);
                    LOCATION_COUTNS++;
                    sb.append("\n检查位置更新次数：");
                    sb.append(String.valueOf(LOCATION_COUTNS));
                    DBLog.i("TAG", sb.toString());
                    LogUtil.e("TRACE", "current loc : " + sb.toString());
                    BDLocation = location;
                } else {
                    locationClient.stop();
                }
            }

            public void onReceivePoi(BDLocation location) {
            }
        });
        locationClient.start();
    }

    /**
     * 检测当前系统声音是否为正常模式
     */
    public boolean isAudioNormal() {
        AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
    }

    private void intiYunTx() {
        CCPAppManager.setContext(mInstance);
        FileAccessor.initFileAccess();
        setChattingContactId();
        initImageLoader();
        CrashHandler.getInstance().init(this);
        SDKInitializer.initialize(mInstance);
//        CrashReport.initCrashReport(getApplicationContext(), "900050687", true);
    }

    /**
     * 保存当前的聊天界面所对应的联系人、方便来消息屏蔽通知
     */
    private void setChattingContactId() {
        try {
            ECPreferences.savePreference(ECPreferenceSettings.SETTING_CHATTING_CONTACTID, "", true);
        } catch (InvalidClassException e) {
            e.printStackTrace();
        }
    }

    public void setMainActivity(PustDemoActivity activity) {
        mPustTestActivity = activity;
    }

    public PustDemoActivity getMainActivity() {
        return mPustTestActivity;
    }

    private void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "ECSDK_Demo/image");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .threadPoolSize(1)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new WeakMemoryCache())
                // .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(CCPAppManager.md5FileNameGenerator)
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiscCache(cacheDir, null, CCPAppManager.md5FileNameGenerator))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                // .writeDebugLogs() // Remove for release app
                .build();//开始构建
        ImageLoader.getInstance().init(config);
    }

    /**
     * 返回配置文件的日志开关
     *
     * @return
     */
    public boolean getLoggingSwitch() {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(
                    getPackageName(), PackageManager.GET_META_DATA);
            boolean b = appInfo.metaData.getBoolean("LOGGING");
            LogUtil.w("[ECApplication - getLogging] logging is: " + b);
            return b;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean getAlphaSwitch() {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(
                    getPackageName(), PackageManager.GET_META_DATA);
            boolean b = appInfo.metaData.getBoolean("ALPHA");
            LogUtil.w("[ECApplication - getAlpha] Alpha is: " + b);
            return b;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    public List<ContactFriendsEntity> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactFriendsEntity> contactList) {
        this.contactList = contactList;
    }

    private static int screenWidth;
    private static int screenHeight;
    private static float screenDensity;

    private void getDeviceScreenSize() {
        Display dis = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        dis.getMetrics(metrics);

        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        screenDensity = metrics.density;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static float getScreenDensity() {
        return screenDensity;
    }


}
