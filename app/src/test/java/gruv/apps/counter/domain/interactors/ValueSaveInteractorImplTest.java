package gruv.apps.counter.domain.interactors;

import android.content.Context;
import android.content.SharedPreferences;

import gruv.apps.counter.di.ComponentBuilder;
import gruv.apps.counter.domain.interactors.impl.ValueSaveInteractorImpl;
import gruv.apps.counter.domain.model.DomainModel;
import gruv.apps.counter.domain.repository.Repository;
import gruv.apps.counter.presentation.presenters.MainPresenter;
import gruv.apps.counter.presentation.presenters.impl.SoundVibrateCallbackImpl;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Тест интерактора ValueSaveInteractorImpl
 *
 * @author Goncharenko Ruslan
 */
public class ValueSaveInteractorImplTest {

    @Mock
    private MainPresenter.View mView;
    @Mock
    private Context mContext;

    private ValueSaveInteractorImpl mInteractor;
    private Repository mRepository;
    private SoundVibrateCallbackImpl mCallbacksImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ComponentBuilder.getPresenterComponent(mView, mContext);

        Scheduler testScheduler = Schedulers.single();

        mInteractor = new ValueSaveInteractorImpl(mContext, testScheduler, testScheduler);

        mRepository = spy(mInteractor.mRepository);
        mInteractor.mRepository = (mRepository);

        mCallbacksImpl = spy(mInteractor.mSoundVibrateCallbacksImpl);
        mInteractor.mSoundVibrateCallbacksImpl = (mCallbacksImpl);
    }

    @Test
    public void runTest() {
        final SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        final SharedPreferences.Editor dataEditor = Mockito.mock(SharedPreferences.Editor.class);
        Mockito.when(mContext.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        Mockito.when(mContext.getSharedPreferences(anyString(), anyInt()).edit()).thenReturn(dataEditor);

        mInteractor.execute().subscribe(this::testZero);

        TestUtils.waitForFinish();

        verify(mRepository).save(mContext);

        // Проверка, не осталось ли непровернного взаимодействия с моком
        Mockito.verifyNoMoreInteractions(mRepository);
    }

    private void testZero(int value){
        int expected = 0;
        assertEquals(expected, value);
    }
}
