package gruv.apps.counter.domain.interactors.impl;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import gruv.apps.counter.domain.Constants;
import gruv.apps.counter.domain.interactors.base.AbstractInteractor;
import gruv.apps.counter.domain.model.DomainModel;
import gruv.apps.counter.storage.converters.StorageDomainModelConverter;
import gruv.apps.counter.storage.model.StorageModel;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Интерактор сброса в ноль значения счетчика (без сохранения значения) и обновления результата на экране
 *
 * @author Goncharenko Ruslan
 */
public class CleanValueInteractorImpl extends AbstractInteractor {

    @Inject
    public CleanValueInteractorImpl(@NonNull Scheduler subscribeOnScheduler, @NonNull Scheduler observeOnScheduler) {
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
        mSoundVibrateCallbacksImpl.vibrate(Constants.VIBRATION_CLEAR_DURATION);
        mSoundVibrateCallbacksImpl.playSound(Constants.Sound.CLEAR_SOUND);
        //конвертируем из модели хранилища в модель для домена
        String value = String.valueOf(0);
        DomainModel domainModel = new DomainModel(value);
        mRepository.setDomainModel(domainModel);
        return domainModel;
        /*
        StorageModel storageModel = new StorageModel(0);
        mRepository.set(storageModel);
        mSoundVibrateCallbacksImpl.vibrate(Constants.VIBRATION_CLEAR_DURATION);
        mSoundVibrateCallbacksImpl.playSound(Constants.Sound.CLEAR_SOUND);
        //конвертируем из модели хранилища в модель для домена
        DomainModel domainModel = StorageDomainModelConverter.convertToDomainModel(storageModel);
        return domainModel;
        */
    }
}
