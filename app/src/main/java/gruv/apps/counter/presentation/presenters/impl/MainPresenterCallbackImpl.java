package gruv.apps.counter.presentation.presenters.impl;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.interactors.DecrementButtonEnabledInteractor;
import gruv.apps.counter.domain.interactors.IncrementButtonEnabledInteractor;
import gruv.apps.counter.domain.interactors.ValueUpdaterInteractor;
import gruv.apps.counter.domain.model.DomainModel;
import gruv.apps.counter.presentation.presenters.MainPresenter;

/**
 * Реализация колбэков
 *
 * @author Goncharenko Ruslan
 */
public class MainPresenterCallbackImpl implements
        ValueUpdaterInteractor.Callback,
        IncrementButtonEnabledInteractor.Callback,
        DecrementButtonEnabledInteractor.Callback{

    @Inject
    MainPresenter.View mView;

    public MainPresenterCallbackImpl() {
        ComponentBuilder.getPresenterComponent(null, null).inject(this);
    }

    @Override
    public void onValueUpdate(@NonNull DomainModel domainModel) {
        mView.setValue(domainModel.getValue());
    }

    @Override
    public void onIncrementButtonEnabled(boolean value) {
        mView.setIncrementButtonEnabled(value);
    }

    @Override
    public void onDecrementButtonEnabled(boolean value) {
        mView.setDecrementButtonEnabled(value);
    }
}
