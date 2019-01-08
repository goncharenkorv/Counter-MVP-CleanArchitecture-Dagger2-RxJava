package gruv.apps.counter.presentation.presenters.impl;

import javax.inject.Inject;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.Constants;
import gruv.apps.counter.domain.interactors.SoundVibrateInteractor;
import gruv.apps.counter.presentation.presenters.MainPresenter;

/**
 * Реализация колбэков
 *
 * @author Goncharenko Ruslan
 */
public class SoundVibrateCallbackImpl implements SoundVibrateInteractor.Callback {

    @Inject
    MainPresenter.View mView;

    public SoundVibrateCallbackImpl() {
        ComponentBuilder.getPresenterComponent(null, null).inject(this);
    }

    @Override
    public void vibrate(long duration) {
        mView.vibrate(duration);
    }

    @Override
    public void playSound(Constants.Sound sound) {
        mView.playSound(sound);
    }
}
