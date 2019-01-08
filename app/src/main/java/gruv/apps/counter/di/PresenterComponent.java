package gruv.apps.counter.di;

import javax.inject.Singleton;

import dagger.Component;
import gruv.apps.counter.domain.interactors.base.AbstractInteractor;
import gruv.apps.counter.presentation.presenters.base.AbstractPresenter;
import gruv.apps.counter.presentation.presenters.impl.MainPresenterCallbackImpl;
import gruv.apps.counter.presentation.presenters.impl.MainPresenterImpl;
import gruv.apps.counter.presentation.presenters.impl.SoundVibrateCallbackImpl;
import gruv.apps.counter.presentation.ui.activities.MainActivity;

/**
 * Компонент презентера (для dagger 2)
 *
 * @author Goncharenko Ruslan
 */
@Component(modules = {PresenterModule.class})
@Singleton
public interface PresenterComponent {

    void inject(MainActivity mainActivity);
    void inject(AbstractInteractor abstractInteractor);
    void inject(MainPresenterImpl mainPresenterImpl);
    void inject(AbstractPresenter abstractPresenter);
    void inject(SoundVibrateCallbackImpl soundVibrateCallbackImpl);
    void inject(MainPresenterCallbackImpl mainPresenterCallbackImpl);
}
