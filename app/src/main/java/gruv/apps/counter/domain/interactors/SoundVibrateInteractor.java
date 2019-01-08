package gruv.apps.counter.domain.interactors;

import gruv.apps.counter.domain.Constants;

/**
 * Интерфейс для вибратора и звука
 *
 * @author Goncharenko Ruslan
 */
public interface SoundVibrateInteractor {

    interface Callback {

        // interactor callback методы
        void vibrate(long duration);

        void playSound(Constants.Sound sound);
    }

}
