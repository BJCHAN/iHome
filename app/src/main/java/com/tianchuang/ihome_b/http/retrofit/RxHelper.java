package com.tianchuang.ihome_b.http.retrofit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Abyss on 2017/1/10.
 * description:rx的一些巧妙的操作
 *
 *                    _ooOoo_
 *                   o8888888o
 *                   88" . "88
 *                   (| -_- |)
 *                    O\ = /O
 *                ____/`---'\____
 *              .   ' \\| |// `.
 *               / \\||| : |||// \
 *             / _||||| -:- |||||- \
 *               | | \\\ - /// | |
 *             | \_| ''\---/'' | |
 *              \ .-\__ `-` ___/-. /
 *           ___`. .' /--.--\ `. . __
 *        ."" '< `.___\_<|>_/___.' >'"".
 *       | | : `- \`.;`\ _ /`;.`/ - ` : | |
 *         \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 *                    `=---='
 *
 * .............................................
 *          佛祖保佑             永无BUG
 *  佛曰:
 *          写字楼里写字间，写字间里程序员；
 *          程序人员写程序，又拿程序换酒钱。
 *          酒醒只在网上坐，酒醉还来网下眠；
 *          酒醉酒醒日复日，网上网下年复年。
 *          但愿老死电脑间，不愿鞠躬老板前；
 *          奔驰宝马贵者趣，公交自行程序员。
 *          别人笑我忒疯癫，我笑自己命太贱；
 *          不见满街漂亮妹，哪个归得程序员？
 **/
public class RxHelper {

    /**
     * 对服务器返回的结果做一些预先的处理
     * */
    public static <T> Observable.Transformer<HttpModle<T>,T> handleResult() {
        return new Observable.Transformer<HttpModle<T>, T>() {
            @Override
            public Observable<T> call(Observable<HttpModle<T>> httpModleObservable) {
                return httpModleObservable.flatMap(new Func1<HttpModle<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(HttpModle<T> tHttpModle) {
                        if (tHttpModle.success()) {
                            return createDate(tHttpModle.data);
                        } else {
                            return Observable.error(new ServerException(tHttpModle.msg));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable<T> createDate(final T data) {
     return Observable.create(new Observable.OnSubscribe<T>(){

         @Override
         public void call(Subscriber<? super T> subscriber) {
             try {
                 subscriber.onNext(data);
                 subscriber.onCompleted();
             } catch (Exception e) {
                 subscriber.onError(e);
             }

         }
     });
    }
}
