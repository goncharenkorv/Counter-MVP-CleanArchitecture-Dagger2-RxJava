package gruv.apps.counter.domain.interactors;

import android.content.Context;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.interactors.impl.IncrementButtonEnabledInteractorImpl;
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
 * Тест интерактора IncrementButtonEnabledInteractorImpl
 *
 * @author Goncharenko Ruslan
 */
public class IncrementButtonEnabledInteractorImplTest {

    private static final int TEST_GOOD_VALUE = 5;
    private static final int TEST_BAD_VALUE = 1000000;

    @Mock
    private MainPresenter.View mView;
    @Mock
    private Context mContext;

    private IncrementButtonEnabledInteractorImpl mInteractor;
    private Repository mRepository;
    private SoundVibrateCallbackImpl mCallbacksImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ComponentBuilder.getPresenterComponent(mView, mContext);

        Scheduler testScheduler = Schedulers.single();//new TestScheduler();

        mInteractor = new IncrementButtonEnabledInteractorImpl(testScheduler, testScheduler);

        mRepository = spy(mInteractor.mRepository);
        mInteractor.mRepository = (mRepository);

        mCallbacksImpl = spy(mInteractor.mSoundVibrateCallbacksImpl);
        mInteractor.mSoundVibrateCallbacksImpl = (mCallbacksImpl);
    }

    @Test
    public void runTrueTest() {

        StorageModel storageModel = new StorageModel(TEST_GOOD_VALUE);

        when(mRepository.get()).thenReturn(storageModel);

        mInteractor.execute().subscribe(this::testGoodResult);

        TestUtils.waitForFinish();

        Mockito.verify(mRepository).get();
        Mockito.verify(mRepository).getDomainModel();

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);
    }

    @Test
    public void runFalseTest() {

        StorageModel storageModel = new StorageModel(TEST_BAD_VALUE);

        when(mRepository.get()).thenReturn(storageModel);

        mInteractor.execute().subscribe(this::testBadResult);

        TestUtils.waitForFinish();

        Mockito.verify(mRepository).get();
        Mockito.verify(mRepository).getDomainModel();

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);
    }

    private void testGoodResult(Boolean result){
        Boolean resultExpected = true;
        assertEquals(resultExpected, result);
    }

    private void testBadResult(Boolean result){
        Boolean resultExpected = false;
        assertEquals(resultExpected, result);
    }
}
