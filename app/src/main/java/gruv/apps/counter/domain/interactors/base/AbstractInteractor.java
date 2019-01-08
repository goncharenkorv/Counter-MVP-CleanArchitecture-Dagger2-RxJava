package gruv.apps.counter.domain.interactors.base;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.repository.Repository;
import gruv.apps.counter.presentation.presenters.impl.SoundVibrateCallbackImpl;
import io.reactivex.Scheduler;

/**
 * Этот абстрактный класс реализует некоторые общие методы для всех интеракторов.
 * Отменяя интерактор (cancel), проверьте, работает ли он
 * и завершение интерактора содержит в основном один и тот же код, поэтому этот класс был создан.
 * Методы поля объявлены как volatile, так как мы могли бы использовать эти методы из разных потоков (в основном из пользовательского интерфейса).
 *
 * Например, когда активити уничтожается, нам, вероятно, следует отменить интерактор,
 * но запрос будет поступать из потока пользовательского интерфейса, если только запрос не был специально назначен фоновому потоку.
 *
 * @author Goncharenko Ruslan
 */
public abstract class AbstractInteractor {

    /**
     * public  - только для тестов
     * protected было бы достаточно для обеспечения видимости в наследуемых классах
     */
    @NonNull
    @Inject
    public SoundVibrateCallbackImpl mSoundVibrateCallbacksImpl;

    /**
     * public  - только для тестов
     * protected было бы достаточно для обеспечения видимости в наследуемых классах
     */
    @NonNull
    @Inject
    public Repository mRepository;

    @NonNull
    protected Scheduler mSubscribeOnScheduler;

    @NonNull
    protected Scheduler mObserveOnScheduler;

    public AbstractInteractor(@NonNull Scheduler subscribeOnScheduler, @NonNull Scheduler observeOnScheduler) {
        ComponentBuilder.getPresenterComponent(null, null).inject(this);
        mObserveOnScheduler = observeOnScheduler;
        mSubscribeOnScheduler = subscribeOnScheduler;
    }
}
