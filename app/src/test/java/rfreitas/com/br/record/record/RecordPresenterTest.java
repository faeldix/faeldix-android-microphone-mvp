package rfreitas.com.br.record.record;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import rfreitas.com.br.record.record.RecordPresenter;
import rfreitas.com.br.record.record.RecordView;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by rafael-iteris on 1/6/17.
 */

public class RecordPresenterTest {

    private RecordPresenter presenter;
    private RecordPresenter.RecordPresenterListener mock;
    private RecordView view;

    @Test(expected = IllegalStateException.class)
    public void recordPresenterMustHasAView(){
        RecordPresenter presenter = new RecordPresenter();
        presenter.setRecordPresenterListener(Mockito.mock(RecordPresenter.RecordPresenterListener.class));

        presenter.startRecord("", "");
    }

    @Test(expected = IllegalStateException.class)
    public void recordPresenterMustHasARecordPresenterListener(){
        RecordPresenter presenter = new RecordPresenter();
        presenter.setView(Mockito.mock(RecordView.class));

        presenter.startRecord("", "");
    }

    @Before
    public void init(){
        presenter = new RecordPresenter();

        mock = Mockito.mock(RecordPresenter.RecordPresenterListener.class);
        view = Mockito.mock(RecordView.class);

        presenter.setRecordPresenterListener(mock);
        presenter.setView(view);
    }

    @Test
    public void whenStartARecordOnPresenterTheListenerWithMethodonInitRecordMustBeCalled(){
        presenter.getRecordServiceListener().onStartRecord();
        verify(mock).onInitRecord();
    }

    @Test
    public void whenStopARecordOnPresenterTheListenerWithMethodOnStopRecordMustBeCalled(){
        presenter.getRecordServiceListener().onStop();
        verify(mock).onStopRecord();
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
