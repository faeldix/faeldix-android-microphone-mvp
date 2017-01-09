package rfreitas.com.br.record.player;

import android.view.MotionEvent;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.Timer;

import rfreitas.com.br.record.BuildConfig;
import rfreitas.com.br.record.record.Record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rafaelfreitas on 1/8/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", constants = BuildConfig.class)
public class PlayerViewTest {

    @Mock
    private PlayerPresenter presenter;

    @Mock
    private Timer timer;

    @Mock
    private MotionEvent event;

    @Spy
    private PlayerFragment fragment;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        when(fragment.getNewTimer()).thenReturn(timer);

        SupportFragmentTestUtil.startFragment(fragment);

        fragment.setTimer(timer);
        fragment.setPresenter(presenter);
    }

    @Test
    public void whenPressThePlayButtonThePresenterMethodPlayMustBeCalled(){
        when(event.getAction()).thenReturn(MotionEvent.ACTION_DOWN);

        fragment.getOnTouchListener().onTouch(fragment.button, event);

        verify(presenter).play(any(Record.class));
    }

    @Test
    public void whenReleaseThePlayButtonThePresenterMethodStopMustBeCalled(){
        when(event.getAction()).thenReturn(MotionEvent.ACTION_UP);

        fragment.getOnTouchListener().onTouch(fragment.button, event);

        verify(presenter).stop();
    }

    @Test
    public void whenTheMethodShowProgressOfPlayerIsCalledTheTimeMustBeViewed(){
        fragment.showProgressOfPlayer();

        assertEquals(fragment.time.getVisibility(), View.VISIBLE);
    }

    @Test
    public void whenTheMethodStopProgressOfPlayerIsCalledTheToastMustBeViewed(){
        fragment.stopProgressOfPlayer();

        assertNotNull(fragment.getToastStop());
    }

    @Test
    public void whenTheMethodShowErrorIsCalledTheToastMustBeViwed(){
        fragment.showError(anyString());

        assertNotNull(fragment.getToastError());
    }

    @Test
    public void whenTheMethodReloadIsCalledTheTimeMustBeInvisible(){
        fragment.reload();

        assertEquals(fragment.time.getVisibility(), View.INVISIBLE);
    }

}
