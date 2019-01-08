package gruv.apps.counter.domain.interactors.impl;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;

import gruv.apps.counter.domain.interactors.DecrementButtonEnabledInteractor;
import gruv.apps.counter.domain.interactors.base.AbstractInteractor;
import gruv.apps.counter.domain.model.DomainModel;
import gruv.apps.counter.storage.DataValueRepository;
import gruv.apps.counter.storage.model.StorageModel;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Интерактор доступности кнопки уменьшения
 *
 * @author Goncharenko Ruslan
 */
public class DecrementButtonEnabledInteractorImpl extends AbstractInteractor implements DecrementButtonEnabledInteractor {

    public DecrementButtonEnabledInteractorImpl(@NonNull Scheduler subscribeOnScheduler, @NonNull Scheduler observeOnScheduler) {
        super(subscribeOnScheduler, observeOnScheduler);
    }

    public Observable<Boolean> execute(){
        return getObservable()
                .subscribeOn(mSubscribeOnScheduler)
                .observeOn(mObserveOnScheduler);

        /*
        return Observable.just(run())
               .subscribeOn(Schedulers.io())
               .observeOn(mObserveOnScheduler);
        */
    }

    private Observable<Boolean> getObservable(){
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override public Boolean call() {
                return run();
            }
        });
    }

    private boolean run() {
        DomainModel domainModel = mRepository.getDomainModel();
        int value = Integer.valueOf(domainModel.getValue());
        return (value > DataValueRepository.MIN_VALUE);
        /*
        StorageModel storageModel = mRepository.get();
        int value = storageModel.getValue();
        return (value > DataValueRepository.MIN_VALUE);
        */
    }
}
