package rfreitas.com.br.record.record;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.os.EnvironmentCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;
import org.robolectric.util.FragmentTestUtil;

import java.util.Timer;

import rfreitas.com.br.record.BuildConfig;
import rfreitas.com.br.record.MainActivity;
import rfreitas.com.br.record.R;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by rafaelfreitas on 1/8/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", constants = BuildConfig.class)
public class RecordViewTest {

    @Mock
    private RecordPresenter presenter;

    @Mock
    private Timer timer;

    @Mock
    private MotionEvent event;

    @Spy
    private RecordFragment fragment;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        when(fragment.getNewTimer()).thenReturn(timer);
        SupportFragmentTestUtil.startFragment(fragment);

        fragment.setPresenter(presenter);
        fragment.setTimer(timer);
    }

    @Test
    public void whenClickOnRecordButtonThePresenterWithMethodStartRecordMustBeCalled(){
        fragment.setPresenter(presenter);
        fragment.setTimer(timer);

        when(event.getAction()).thenReturn(MotionEvent.ACTION_DOWN);

        fragment.getOnTouchListener().onTouch(fragment.gravar, event);

        verify(presenter).startRecord(anyString(), anyString());

        assertEquals(fragment.status.getText().toString(), fragment.getString(R.string.record_status_message)); // verificando se o status foi modificado
        assertEquals(fragment.status.getCurrentTextColor(), Color.BLUE); // verificando se o
        assertEquals(fragment.tempo.getAnimation(), fragment.getPulse()); //verificando se o texto esta animado
    }

    @Test
    public void whenClickOnRecordButtonIsReleasedThePresenterWithStopRecordMustBeCalled(){
        when(event.getAction()).thenReturn(MotionEvent.ACTION_UP);

        fragment.getOnTouchListener().onTouch(fragment.gravar, event);

        verify(presenter).stopRecord();

        assertEquals(fragment.status.getText().toString(), fragment.getString(R.string.default_status_message)); // verificando se o status foi modificado
        assertEquals(fragment.status.getCurrentTextColor(), Color.BLACK); // verificando se o
        assertNull(fragment.tempo.getAnimation()); //verificando se ja nao é mais animado
    }

    @Test
    public void whenShowProgressOfRecordTheTimeMustBeViewed(){
        fragment.showProgressOfRecord();

        assertEquals(fragment.tempo.getVisibility(), View.VISIBLE);
    }

    @Test
    public void whenStopProgressOfRecordTheToastMustBeViewed(){
        fragment.stopProgressOfRecord();

        assertNotNull(fragment.getStopToast());
    }

    @Test
    public void whenTimeIsReachedTheToastMustBeViewed(){
        fragment.showTimeIsReached();

        assertNotNull(fragment.getTimeIsReachedToast());
    }

    @Test
    public void whenAnErrorTheToastMustBeViewed(){
        fragment.showError("");

        assertNotNull(fragment.getErrorToast());
    }

}
