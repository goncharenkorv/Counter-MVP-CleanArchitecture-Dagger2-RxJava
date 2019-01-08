package gruv.apps.counter.domain.interactors;

import android.content.Context;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.Constants;
import gruv.apps.counter.domain.interactors.impl.CleanValueInteractorImpl;
import gruv.apps.counter.domain.model.DomainModel;
import gruv.apps.counter.domain.repository.Repository;
import gruv.apps.counter.presentation.presenters.MainPresenter;
import gruv.apps.counter.presentation.presenters.impl.SoundVibrateCallbackImpl;
import gruv.apps.counter.storage.model.StorageModel;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Тест интерактора CleanValueInteractorImpl
 *
 * @author Goncharenko Ruslan
 */
public class CleanValueInteractorImplTest {

    private static final long VIBRATION_CLEAR_DURATION = 100; // Milliseconds

    @Mock
    private MainPresenter.View mView;
    @Mock
    private Context mContext;

    private CleanValueInteractorImpl mInteractor;
    private Repository mRepository;
    private SoundVibrateCallbackImpl mCallbacksImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ComponentBuilder.getPresenterComponent(mView, mContext);

        Scheduler testScheduler = Schedulers.single();//new TestScheduler()

        mInteractor = new CleanValueInteractorImpl(testScheduler, testScheduler);

        mRepository = spy(mInteractor.mRepository);
        mInteractor.mRepository = (mRepository);

        mCallbacksImpl = spy(mInteractor.mSoundVibrateCallbacksImpl);
        mInteractor.mSoundVibrateCallbacksImpl = (mCallbacksImpl);
    }

    @Test
    public void runTest() {

        StorageModel storageModel = new StorageModel(0);
        DomainModel domainModel = new DomainModel(String.valueOf(0));

        //when(mRepository.get()).thenReturn(storageModel);
        //when(mRepository.getDomainModel()).thenReturn(domainModel);

        mInteractor.execute().subscribe(this::testDomain);

        TestUtils.waitForFinish();

        Mockito.verify(mRepository).set(storageModel);
        Mockito.verify(mRepository).setDomainModel(domainModel);

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);

        // Проверка вызова колбэков
        Mockito.verify(mCallbacksImpl).vibrate(VIBRATION_CLEAR_DURATION);
        Mockito.verify(mCallbacksImpl).playSound(Constants.Sound.CLEAR_SOUND);
    }

    private void testDomain(DomainModel domainModel){
        DomainModel domainModelExpected = new DomainModel(String.valueOf(0));
        assertEquals(domainModelExpected, domainModel);
    }
}
