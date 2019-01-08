package gruv.apps.counter.domain.interactors;

/**
 * Интерфейс доступности кнопки увеличения
 *
 * @author Goncharenko Ruslan
 */
public interface IncrementButtonEnabledInteractor {

    interface Callback {
        // interactor callback методы
        void onIncrementButtonEnabled(boolean enabled);
    }

}
