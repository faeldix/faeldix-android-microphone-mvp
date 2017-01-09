package rfreitas.com.br.record.record;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by rafael-iteris on 1/6/17.
 */

public class RecordPresenterTest {

    @Mock
    private RecordPresenter.RecordPresenterListener listener;

    @Mock
    private RecordView view;

    private RecordPresenter presenter;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        presenter = new RecordPresenter();

        presenter.setRecordPresenterListener(listener);
        presenter.setView(view);
    }

    @Test(expected = IllegalStateException.class)
    public void recordPresenterMustHasAView(){
        RecordPresenter presenter = new RecordPresenter();
        presenter.setRecordPresenterListener(listener);

        presenter.startRecord("", "");
    }

    @Test(expected = IllegalStateException.class)
    public void recordPresenterMustHasARecordPresenterListener(){
        RecordPresenter presenter = new RecordPresenter();
        presenter.setView(view);

        presenter.startRecord("", "");
    }

    @Test
    public void whenStartARecordOnPresenterTheListenerWithMethodonInitRecordMustBeCalled(){
        presenter.getRecordServiceListener().onStartRecord();
        verify(listener).onInitRecord();
    }

    @Test
    public void whenStopARecordOnPresenterTheListenerWithMethodOnStopRecordMustBeCalled(){
        presenter.getRecordServiceListener().onStop();
        verify(listener).onStopRecord();
    }

    @Test
    public void whenTheTimeOfRecordIsReachedTheViewWithMethodShowTimeIsReachedMustBeCalled(){
        presenter.getRecordServiceListener().onMaxTimeIsReached();
        verify(view).showTimeIsReached();
    }

    @Test
    public void whenAnErrorIsOcurredTheViewWithMethodOnErrorMusBeCalled(){
        presenter.getRecordServiceListener().onError("error");
        verify(view).showError(any(String.class));
    }

}
