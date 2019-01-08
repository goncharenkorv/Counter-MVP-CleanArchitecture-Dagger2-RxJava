package gruv.apps.counter.presentation.presenters.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.interactors.impl.CleanValueInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.DecrementButtonEnabledInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.IncrementButtonEnabledInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueDecreaserInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueIncreaserInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueLoadInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueSaveInteractorImpl;
import gruv.apps.counter.domain.interactors.impl.ValueUpdaterInteractorImpl;
import gruv.apps.counter.presentation.presenters.base.AbstractPresenter;
import gruv.apps.counter.presentation.presenters.MainPresenter;

/**
 * Реализация (имплементация) главного презентера
 *
 * @author Goncharenko Ruslan
 */
public class MainPresenterImpl extends AbstractPresenter implements MainPresenter {

    @Inject
    CleanValueInteractorImpl mCleanValueInteractorImpl;
    @Inject
    ValueSaveInteractorImpl mValueSaveInteractorImpl;
    @Inject
    ValueLoadInteractorImpl mValueLoadInteractorImpl;
    @Inject
    ValueIncreaserInteractorImpl mValueIncreaserInteractorImpl;
    @Inject
    ValueDecreaserInteractorImpl mValueDecreaserInteractorImpl;
    @Inject
    ValueUpdaterInteractorImpl mValueUpdaterInteractorImpl;
    @Inject
    IncrementButtonEnabledInteractorImpl mIncrementButtonEnabledInteractorImpl;
    @Inject
    DecrementButtonEnabledInteractorImpl mDecrementButtonEnabledInteractorImpl;

    /**
     * public  - только для тестов
     * protected было бы достаточно для обеспечения видимости в наследуемых классах
     */
    @NonNull
    @Inject
    //public CallbacksImpl mSoundVibrateCallbacksImpl;
    MainPresenterCallbackImpl mainPresenterCallbackImpl;

    public MainPresenterImpl() {
        super();
        ComponentBuilder.getPresenterComponent(null, null).inject(this);
    }

    @Override
    public void resume() {
        update();
    }

    @Override
    public void saveState(Context context) {
        mValueSaveInteractorImpl.execute()
                .subscribe();
    }

    @Override
    public void loadState(Context context) {
        mValueLoadInteractorImpl.execute()
                .subscribe(mainPresenterCallbackImpl::onValueUpdate);
    }

    @Override
    public void increase() {
        mValueIncreaserInteractorImpl.execute()
                .subscribe(mainPresenterCallbackImpl::onValueUpdate);
        // Обновляем доступность кнопок:
        updateIncrementButtonEnabled();
        updateDecrementButtonEnabled();
    }

    @Override
    public void decrease() {
        mValueDecreaserInteractorImpl.execute()
                .subscribe(mainPresenterCallbackImpl::onValueUpdate);
        // Обновляем доступность кнопок:
        updateIncrementButtonEnabled();
        updateDecrementButtonEnabled();
    }

    //@SuppressLint("CheckResult")
    @Override
    public void clean() {
        mCleanValueInteractorImpl.execute()
                .subscribe(mainPresenterCallbackImpl::onValueUpdate);
    }

    @Override
    public void pause() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void onError(String message) {
    }

    private void update() {
        // Обновляем значение счетчика
        updateValue();

        // Обновляем доступность кнопок:
        updateIncrementButtonEnabled();
        updateDecrementButtonEnabled();
    }

    private void updateValue() {
        //mValueUpdaterInteractorImpl.execute();
        mValueUpdaterInteractorImpl.execute()
                .subscribe(mainPresenterCallbackImpl::onValueUpdate);
    }

    private void updateIncrementButtonEnabled() {
        mIncrementButtonEnabledInteractorImpl.execute()
                .subscribe(mainPresenterCallbackImpl::onIncrementButtonEnabled);
    }

    private void updateDecrementButtonEnabled() {
        mDecrementButtonEnabledInteractorImpl.execute()
                .subscribe(mainPresenterCallbackImpl::onDecrementButtonEnabled);
    }
}
