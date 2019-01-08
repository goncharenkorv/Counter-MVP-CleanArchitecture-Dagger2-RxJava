package gruv.apps.counter.di;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import gruv.apps.counter.presentation.presenters.MainPresenter;

/**
 * @author Goncharenko Ruslan
 */
public class ComponentBuilder {

    private static PresenterComponent mPresenterComponent;

    private ComponentBuilder() {
        throw new AssertionError("Не должен быть инициализирован");
    }

    @NonNull
    @Singleton
    public static PresenterComponent getPresenterComponent(MainPresenter.View view, Context context) {
        if (mPresenterComponent == null) {
            mPresenterComponent = DaggerPresenterComponent.builder()
                    .presenterModule(new PresenterModule(view, context))
                    .build();
        }
        return mPresenterComponent;
    }
}
