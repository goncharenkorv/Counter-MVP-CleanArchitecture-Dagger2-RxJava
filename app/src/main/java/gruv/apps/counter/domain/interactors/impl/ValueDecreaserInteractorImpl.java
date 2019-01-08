package gruv.apps.counter.domain.interactors.impl;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;

import gruv.apps.counter.domain.Constants;
import gruv.apps.counter.domain.interactors.base.AbstractInteractor;
import gruv.apps.counter.domain.model.DomainModel;
import gruv.apps.counter.storage.DataValueRepository;
import gruv.apps.counter.storage.converters.StorageDomainModelConverter;
import gruv.apps.counter.storage.model.StorageModel;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Интерактор уменьшения значения счетчика
 *
 * @author Goncharenko Ruslan
 */
public class ValueDecreaserInteractorImpl extends AbstractInteractor {

    public ValueDecreaserInteractorImpl(@NonNull Scheduler subscribeOnScheduler, @NonNull Scheduler observeOnScheduler) {
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
        int value = Integer.valueOf(domainModel.getValue());
        if (value > DataValueRepository.MIN_VALUE) {
            value--;
            domainModel.setValue(String.valueOf(value));
            mRepository.setDomainModel(domainModel);

            mSoundVibrateCallbacksImpl.vibrate(Constants.VIBRATION_DECREASE_DURATION);
            mSoundVibrateCallbacksImpl.playSound(Constants.Sound.DECREMENT_SOUND);
        }
        return domainModel;
        /*
        StorageModel storageModel = mRepository.get();
        int value = storageModel.getValue();
        if (value > DataValueRepository.MIN_VALUE) {
            value--;
            storageModel.setValue(value);
            mRepository.set(storageModel);

            mSoundVibrateCallbacksImpl.vibrate(Constants.VIBRATION_DECREASE_DURATION);
            mSoundVibrateCallbacksImpl.playSound(Constants.Sound.DECREMENT_SOUND);
        }

        //конвертируем из модели хранилища в модель для домена
        DomainModel domainModel = StorageDomainModelConverter.convertToDomainModel(storageModel);
        return domainModel;
        */
    }
}
