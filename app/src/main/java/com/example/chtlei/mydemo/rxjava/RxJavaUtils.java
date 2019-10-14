package com.example.chtlei.mydemo.rxjava;

import android.util.Log;

import com.example.chtlei.mydemo.eventbus.event.RxJavaEventMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaUtils {

    private static class Singleton {
        private static RxJavaUtils mInstance = new RxJavaUtils();
    }

    public static RxJavaUtils getInstance() {
        return Singleton.mInstance;
    }

    public void testCreate () {
        Observable<Object> observable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                emitter.onNext("next");
                emitter.onComplete();
                emitter.onNext("next2");
            }
        });

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("LCT","observer----->onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                Log.i("LCT","observer----->onNext" + " " + o.toString());
                EventBus.getDefault().post(new RxJavaEventMessage(o.toString()));
            }

            @Override
            public void onError(Throwable e) {
                Log.i("LCT","observer----->onError" + " " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.i("LCT","observer----->onComplete");
            }
        };

        observable.subscribe(observer);
    }

    public void testJust() {
        Observable.just("one","two","three")
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        EventBus.getDefault().post(new RxJavaEventMessage(s));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void testMap() {
        Observable.just(1,2,3)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return String.valueOf(integer) + "map";
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        EventBus.getDefault().post(new RxJavaEventMessage(s));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void testFlatMap() {
        Observable.just(initData())
                .flatMap(new Function<RxJavaBean, ObservableSource<RxJavaBean.Message>>() {
                    @Override
                    public ObservableSource<RxJavaBean.Message> apply(RxJavaBean rxJavaBean) throws Exception {
                        return Observable.fromIterable(rxJavaBean.getMessage());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RxJavaBean.Message>() {
                        @Override
                    public void accept(RxJavaBean.Message message) throws Exception {
                        Log.i("LCT","accept----->message is "+ message.toString());
                        EventBus.getDefault().post(new RxJavaEventMessage(message.toString()));
                    }
                });
    }

    private RxJavaBean initData() {

        List<RxJavaBean.Message> messages = new ArrayList<>();
        RxJavaBean.Message message = new RxJavaBean.Message("chtlei",28);
        RxJavaBean.Message message2 = new RxJavaBean.Message("chtlei",29);
        RxJavaBean.Message message3 = new RxJavaBean.Message("chtlei",30);
        messages.add(message);
        messages.add(message2);
        messages.add(message3);
        RxJavaBean rxJavaBean = new RxJavaBean(200, "success", messages);

        return rxJavaBean;
    }
}
