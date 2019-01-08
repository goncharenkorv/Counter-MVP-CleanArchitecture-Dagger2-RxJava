package gruv.apps.counter.domain.interactors.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Callable;

import gruv.apps.counter.domain.interactors.base.AbstractInteractor;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Интерактор сохранения значения счетчика (записи значения)
 *
 * @author Goncharenko Ruslan
 */
public class ValueSaveInteractorImpl extends AbstractInteractor {

    private Context mContext;

    public ValueSaveInteractorImpl(@NonNull Context context, @NonNull Scheduler subscribeOnScheduler, @NonNull Scheduler observeOnScheduler) {
        super(subscribeOnScheduler, observeOnScheduler);
        mContext = context;
    }

    public Observable<Integer> execute(){
        return getObservable()
                .subscribeOn(mSubscribeOnScheduler)
                .observeOn(mObserveOnScheduler);

        /*
        return Observable.just(run())
               .subscribeOn(Schedulers.io())
               .observeOn(mObserveOnScheduler);
        */
    }

    private Observable<Integer> getObservable(){
        return Observable.fromCallable(new Callable<Integer>() {
            @Override public Integer call() {
                return run();
            }
        });
    }

    private int run() {
        mRepository.save(mContext);
        return 0;
    }
}
