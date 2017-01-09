package rfreitas.com.br.record.player;

import android.content.Context;
import android.media.MediaPlayer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import rfreitas.com.br.record.record.Record;

import static org.junit.Assert.*;

/**
 * Created by rafaelfreitas on 1/7/17.
 */
public class PlayerPresenterTest {

    @Mock
    private MediaPlayer player;

    @Mock
    private PlayerView view;

    @Mock
    private Context context;

    @Mock
    private PlayerService service;

    @Mock
    private Record record;

    private PlayerPresenter presenter;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        presenter = new PlayerPresenter(context);
        presenter.setService(service);
        presenter.setView(view);
    }

    @Test
    public void whenPlayARecordThePlayMethodOfServiceMustBeCalled(){
        presenter.play(record);
        Mockito.verify(service).play(Mockito.any(Record.class));
    }

    @Test
    public void whenStopARecordTheStopMethodOfServiceMustBeCalled(){
        presenter.stop();
        Mockito.verify(service).stop();
    }

    @Test
    public void whenPlayARecordTheViewMustBeCallTheShowProgressOfPlayerMethod(){
        presenter.onStartPlayer();

        Mockito.verify(view).showProgressOfPlayer();
    }

    @Test
    public void whenStopARecordTheViewMustBeCallTheOnStopPlayer(){
        presenter.onStopPlayer();

        Mockito.verify(view).stopProgressOfPlayer();
    }

    @Test
    public void whenAnErrorHappensTheViewMustBeCallTheShowOnErrorMethod(){
        presenter.onError("");

        Mockito.verify(view).showError(Mockito.any(String.class));
    }

}