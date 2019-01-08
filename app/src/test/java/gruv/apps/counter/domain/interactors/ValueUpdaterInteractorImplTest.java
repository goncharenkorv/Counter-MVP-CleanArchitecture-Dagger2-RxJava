package gruv.apps.counter.domain.interactors;

import android.content.Context;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.interactors.impl.ValueUpdaterInteractorImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Тест интерактора ValueUpdaterInteractorImpl
 *
 * @author Goncharenko Ruslan
 */
public class ValueUpdaterInteractorImplTest {

    private static final int TEST_VALUE = 1;

    @Mock
    private MainPresenter.View mView;
    @Mock
    private Context mContext;

    private ValueUpdaterInteractorImpl mInteractor;
    private Repository mRepository;
    private SoundVibrateCallbackImpl mCallbacksImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ComponentBuilder.getPresenterComponent(mView, mContext);

        Scheduler testScheduler = Schedulers.single();//

        mInteractor = new ValueUpdaterInteractorImpl(testScheduler, testScheduler);

        mRepository = spy(mInteractor.mRepository);
        mInteractor.mRepository = (mRepository);

        mCallbacksImpl = spy(mInteractor.mSoundVibrateCallbacksImpl);
        mInteractor.mSoundVibrateCallbacksImpl = (mCallbacksImpl);
    }

    @Test
    public void runTest() {

        StorageModel storageModel = new StorageModel(TEST_VALUE);

        when(mRepository.get()).thenReturn(storageModel);

        mInteractor.execute().subscribe(this::testDomain);

        TestUtils.waitForFinish();

        verify(mRepository).get();
        verify(mRepository).getDomainModel();
        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mCallbacksImpl);
    }

    private void testDomain(DomainModel domainModel){
        DomainModel domainModelExpected = new DomainModel(String.valueOf(TEST_VALUE));
        assertEquals(domainModelExpected, domainModel);
    }
}
