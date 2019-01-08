package gruv.apps.counter.domain.interactors;

import gruv.apps.counter.domain.model.DomainModel;

/**
 * Интерфейс обновления значения счетчика.
 *
 * @author Goncharenko Ruslan
 */
public interface ValueUpdaterInteractor {

    interface Callback {
        // interactor callback методы
        void onValueUpdate(DomainModel domainModel);
    }

}
