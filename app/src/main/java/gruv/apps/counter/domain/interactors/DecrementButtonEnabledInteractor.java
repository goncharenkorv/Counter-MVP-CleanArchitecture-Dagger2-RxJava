package gruv.apps.counter.domain.interactors;

/**
 * Интерфейс доступности кнопки уменьшения
 *
 * @author Goncharenko Ruslan
 */
public interface DecrementButtonEnabledInteractor {

    interface Callback {
        // interactor callback методы
        void onDecrementButtonEnabled(boolean enabled);
    }

}
