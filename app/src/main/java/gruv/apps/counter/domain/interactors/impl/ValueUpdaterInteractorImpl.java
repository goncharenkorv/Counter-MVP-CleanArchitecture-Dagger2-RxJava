package gruv.apps.counter.domain.interactors.impl;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;

import gruv.apps.counter.domain.interactors.base.AbstractInteractor;
import gruv.apps.counter.domain.model.DomainModel;
import gruv.apps.counter.storage.converters.StorageDomainModelConverter;
import gruv.apps.counter.storage.model.StorageModel;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Интерактор обновления значения счетчика.
 * Т.е. вывод обновленного значения в главном потоке
 *
 * @author Goncharenko Ruslan
 */
public class ValueUpdaterInteractorImpl extends AbstractInteractor {

    public ValueUpdaterInteractorImpl(@NonNull Scheduler subscribeOnScheduler, @NonNull Scheduler observeOnScheduler) {
        super(subscribeOnScheduler, observeOnScheduler);
    }

    public Observable<DomainModel> execute(){
        return getObservable()
                .subscribeOn(mSubscribeOnScheduler)
                .observeOn(mObserveOnScheduler);

        /*
        return Observable.just(run())
               .subscribeOn(Schedulers.io())
               .observeOn(mObserveOnScheduler);
        */
    }

    private Observable<DomainModel> getObservable(){
        return Observable.fromCallable(new Callable<DomainModel>() {
            @Override public DomainModel call() {
                return run();
            }
        });
    }

    private DomainModel run() {
        DomainModel domainModel = mRepository.getDomainModel();
        return domainModel;
        /*
        StorageModel storageModel = mRepository.get();
        //конвертируем из модели хранилища в модель для домена
        DomainModel domainModel = StorageDomainModelConverter.convertToDomainModel(storageModel);
        return domainModel;
        */
    }
}
