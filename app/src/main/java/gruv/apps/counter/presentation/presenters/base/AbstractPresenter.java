package gruv.apps.counter.presentation.presenters.base;

import gruv.apps.counter.di.ComponentBuilder;

/**
 * Это базовый класс для всех презентеров, которые общаются с интеракторами. Этот базовый класс будет содержать
 * ссылку на объекты Executor и MainThread, необходимые для запуска интеракторов в фоновом потоке.
 *
 * @author Goncharenko Ruslan
 */
public abstract class AbstractPresenter {

    public AbstractPresenter() {
        ComponentBuilder.getPresenterComponent(null, null).inject(this);
    }
}
