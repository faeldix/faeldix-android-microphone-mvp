package rfreitas.com.br.record.record;

import android.media.MediaRecorder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import rfreitas.com.br.record.record.Record;
import rfreitas.com.br.record.record.RecordService;

import static org.mockito.Mockito.*;

/**
 * Created by rafael-iteris on 1/6/17.
 */

public class RecordServiceTest {

    private Record record = new Record("ABC", "ABC", 0);

    private RecordService service;
    private RecordService.RecordServiceListener listener;
    private MediaRecorder recorder;

    @Before
    public void setup(){
        service = new RecordService();

        listener = Mockito.mock(RecordService.RecordServiceListener.class);
        service.setRecordServiceListener(listener);

        recorder = Mockito.mock(MediaRecorder.class);
        service.setMediaRecord(recorder);
    }

    @Test(expected = IllegalStateException.class)
    public void recordServiceMustHasAMediaRecord(){
        RecordService service = new RecordService();
        service.setRecordServiceListener(listener);

        service.record(record);
    }

    @Test(expected = IllegalStateException.class)
    public void recordServiceMustHasAListener(){
        RecordService service = new RecordService();
        service.setMediaRecord(recorder);

        service.record(record);
    }

    @Test
    public void whenAnErrorIsThrowedAfterStartTheRecordTheListenerWithMethodOnErrorMustBeCalled(){
        MediaRecorder recorder = Mockito.mock(MediaRecorder.class);
        Mockito.doThrow(Exception.class).when(recorder).start();

        service.setMediaRecord(recorder);
        service.record(record);

        verify(listener).onError(any(String.class));
    }

    @Test
    public void whenStartTheRecordTheListenerWithMethodonStartRecordMustBeCalled(){
        service.record(record);
        verify(listener).onStartRecord();
    }

    @Test
    public void whenStopTheRecordTheListenerWithMethodOnStopRecordMustBeCalled(){
        service.stop();
        verify(listener).onStop();
    }

    @Test
    public void whenAnErrorIsThrowedTheListenerWithMethodOnStopMustBeCalled(){
        service.onError(recorder, 0, 0);
        verify(listener).onStop();
    }

    @Test
    public void whenAnErrorIsThrowedTheListenerWithMethodOnErrorMustBeCalled(){
        service.onError(recorder, 0, 0);
        verify(listener).onError(any(String.class));
    }

    @Test
    public void whenTimeOfRecordIsReachedTheListenerWithMethodOnStopMustBeCalled(){
        service.onInfo(recorder, MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED, 0);
        verify(listener).onStop();
    }

    @Test
    public void whenTimeOfRecordIsReachedTheListenerWithMethodOnMaxTimeIsReachedMustBeCalled(){
        service.onInfo(recorder, MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED, 0);
        verify(listener).onMaxTimeIsReached();
    }

}
