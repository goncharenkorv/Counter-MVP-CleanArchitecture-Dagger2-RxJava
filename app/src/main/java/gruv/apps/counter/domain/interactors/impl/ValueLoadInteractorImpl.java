package gruv.apps.counter.domain.interactors.impl;

import android.content.Context;
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
 * Интерактор восстановления значения счетчика (чтения сохраненного значения)
 *
 * @author Goncharenko Ruslan
 */
public class ValueLoadInteractorImpl extends AbstractInteractor {

    private Context mContext;

    public ValueLoadInteractorImpl(@NonNull Context context, @NonNull Scheduler subscribeOnScheduler, @NonNull Scheduler observeOnScheduler) {
        super(subscribeOnScheduler, observeOnScheduler);
        mContext = context;
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
        mRepository.load(mContext);
        DomainModel domainModel = mRepository.getDomainModel();
        return domainModel;
        /*
        mRepository.load(mContext);
        StorageModel storageModel = mRepository.get();
        //конвертируем из модели хранилища в модель для домена
        DomainModel domainModel = StorageDomainModelConverter.convertToDomainModel(storageModel);
        return domainModel;
        */
    }
}
