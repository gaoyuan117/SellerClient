package com.kaichaohulian.baocms.rxjava;

import android.widget.Toast;


import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.util.NetworkUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2017/3/27.
 */

public class RxUtils {

    public static <T> ObservableTransformer<T, T> io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (!NetworkUtil.isNetworkAvailable(MyApplication.getInstance())) {
                                    Toast.makeText(MyApplication.getInstance(), "网络连接异常", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
