package gruv.apps.counter.domain.interactors;

import android.content.Context;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.Constants;
import gruv.apps.counter.domain.interactors.impl.ValueIncreaserInteractorImpl;
import gruv.apps.counter.domain.model.DomainModel;
import gruv.apps.counter.domain.repository.Repository;
import gruv.apps.counter.presentation.presenters.MainPresenter;
import gruv.apps.counter.presentation.presenters.impl.SoundVibrateCallbackImpl;
import gruv.apps.counter.storage.model.StorageModel;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Тест интерактора ValueIncreaserInteractorImpl
 *
 * @author Goncharenko Ruslan
 */
public class ValueIncreaserInteractorImplTest {

    private static final long VIBRATION_ENCREASE_DURATION = 40; // Milliseconds

    private static final int TEST_VALUE = 5;

    @Mock
    private MainPresenter.View mView;
    @Mock
    private Context mContext;

    private ValueIncreaserInteractorImpl mInteractor;
    private Repository mRepository;
    private SoundVibrateCallbackImpl mCallbacksImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ComponentBuilder.getPresenterComponent(mView, mContext);

        Scheduler testScheduler = Schedulers.single();//new TestScheduler();

        mInteractor = new ValueIncreaserInteractorImpl(testScheduler, testScheduler);

        mRepository = spy(mInteractor.mRepository);
        mInteractor.mRepository = (mRepository);

        mCallbacksImpl = spy(mInteractor.mSoundVibrateCallbacksImpl);
        mInteractor.mSoundVibrateCallbacksImpl = (mCallbacksImpl);
    }

    @Test
    public void runTest() {

        StorageModel storageModel = new StorageModel(TEST_VALUE);
        DomainModel domainModel = new DomainModel(String.valueOf(TEST_VALUE + 1));
        StorageModel setStorageModel = new StorageModel(TEST_VALUE + 1);

        when(mRepository.get()).thenReturn(storageModel);

        mInteractor.execute().subscribe(this::testDomain);

        TestUtils.waitForFinish();

        Mockito.verify(mRepository).get();
        Mockito.verify(mRepository).getDomainModel();
        Mockito.verify(mRepository).setDomainModel(domainModel);
        Mockito.verify(mRepository).set(setStorageModel);

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);

        // Проверка вызова колбэков
        Mockito.verify(mCallbacksImpl).vibrate(VIBRATION_ENCREASE_DURATION);
        Mockito.verify(mCallbacksImpl).playSound(Constants.Sound.INCREMENT_SOUND);
    }

    private void testDomain(DomainModel domainModel){
        DomainModel domainModelExpected = new DomainModel(String.valueOf(TEST_VALUE + 1));
        assertEquals(domainModelExpected, domainModel);
    }
}
