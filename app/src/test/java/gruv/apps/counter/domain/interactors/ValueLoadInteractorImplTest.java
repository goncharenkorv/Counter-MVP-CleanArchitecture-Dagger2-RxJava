package gruv.apps.counter.domain.interactors;

import android.content.Context;
import android.content.SharedPreferences;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.interactors.impl.ValueLoadInteractorImpl;
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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Тест интерактора ValueLoadInteractorImpl
 *
 * @author Goncharenko Ruslan
 */
public class ValueLoadInteractorImplTest {

    private static final int TEST_VALUE = 5;

    @Mock
    private MainPresenter.View mView;
    @Mock
    private Context mContext;

    private ValueLoadInteractorImpl mInteractor;
    private Repository mRepository;
    private SoundVibrateCallbackImpl mCallbacksImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ComponentBuilder.getPresenterComponent(mView, mContext);

        Scheduler testScheduler = Schedulers.single();//

        mInteractor = new ValueLoadInteractorImpl(mContext, testScheduler, testScheduler);

        mRepository = spy(mInteractor.mRepository);
        mInteractor.mRepository = (mRepository);

        mCallbacksImpl = spy(mInteractor.mSoundVibrateCallbacksImpl);
        mInteractor.mSoundVibrateCallbacksImpl = (mCallbacksImpl);
    }

    @Test
    public void runTest() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor dataEditor = Mockito.mock(SharedPreferences.Editor.class);//getSharedPreferences(context).edit();
        Mockito.when(mContext.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(mContext.getSharedPreferences(anyString(), anyInt()).edit()).thenReturn(dataEditor);

        StorageModel storageModel = new StorageModel(TEST_VALUE);

        when(mRepository.get()).thenReturn(storageModel);

        mInteractor.execute().subscribe(this::testDomain);

        TestUtils.waitForFinish();

        Mockito.verify(mRepository).load(mContext);
        Mockito.verify(mRepository).getDomainModel();

        Mockito.verify(mRepository).get();

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);
    }

    private void testDomain(DomainModel domainModel){
        DomainModel domainModelExpected = new DomainModel(String.valueOf(TEST_VALUE));
        assertEquals(domainModelExpected, domainModel);
    }
}
