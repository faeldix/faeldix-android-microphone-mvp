package rfreitas.com.br.record.player;

import android.media.MediaPlayer;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;

import rfreitas.com.br.record.record.Record;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by rafaelfreitas on 1/7/17.
 */
public class PlayerServiceTest {

    @Test(expected = IllegalStateException.class)
    public void whenPlayARecordWithANullInstanceOfMediaPlayerMustBeThrowedAnException(){
        PlayerService service = new PlayerService(null, null);
        service.play(Mockito.mock(Record.class));
    }

    @Test(expected = IllegalStateException.class)
    public void whenPlayARecordWithANullInstanceOfListenerMustBeThrowedAnException(){
        PlayerService service = new PlayerService(null, null);
        service.play(Mockito.mock(Record.class));
    }

    @Test
    public void whenTwoFilesThereAreonFilesFoldermustexistsTwoItensOnList(){
        File directory = mock(File.class);
        when(directory.listFiles()).thenReturn(new File[]{new File("a", "b"), new File("a", "b")});
        when(directory.getAbsolutePath()).thenReturn("whatever");

        PlayerService service = new PlayerService();
        service.setDirectory(directory);

        Assert.assertEquals(service.getRecords().size(), 2);
    }

    @Test
    public void whenPlayARecordTheListenerWithMethodOnStartPlayerMustBeCalled(){
        MediaPlayer mediaPlayer = Mockito.mock(MediaPlayer.class);
        PlayerService.PlayerServiceListener listener = Mockito.mock(PlayerService.PlayerServiceListener.class);

        PlayerService service = new PlayerService(null, listener);
        service.setPlayer(mediaPlayer);
        service.play(Mockito.mock(Record.class));

        Mockito.verify(listener).onStartPlayer();
    }

    @Test
    public void whenPlayARecordWithAnErrorTheListenerWithMethodOnErrorMustBeCalled(){
        MediaPlayer mediaPlayer = Mockito.mock(MediaPlayer.class);
        Mockito.doThrow(Exception.class).when(mediaPlayer).start();

        PlayerService.PlayerServiceListener listener = Mockito.mock(PlayerService.PlayerServiceListener.class);

        PlayerService service = new PlayerService(null, listener);
        service.setPlayer(mediaPlayer);

        service.play(Mockito.mock(Record.class));

        Mockito.verify(listener).onError(Mockito.any(String.class));
    }

    @Test
    public void whenStopARecordTheListenerWithMethodOnStopMustBeCalled(){
        MediaPlayer mediaPlayer = Mockito.mock(MediaPlayer.class);
        PlayerService.PlayerServiceListener listener = Mockito.mock(PlayerService.PlayerServiceListener.class);

        PlayerService service = new PlayerService(null, listener);
        service.setPlayer(mediaPlayer);

        service.stop();

        Mockito.verify(listener).onStopPlayer();
    }

    @Test
    public void whenStopARecordWithAnErrorTheListenerWithMethodOnErrorMustBeCalled(){
        MediaPlayer mediaPlayer = Mockito.mock(MediaPlayer.class);
        Mockito.doThrow(Exception.class).when(mediaPlayer).stop();

        PlayerService.PlayerServiceListener listener = Mockito.mock(PlayerService.PlayerServiceListener.class);

        PlayerService service = new PlayerService(null, listener);
        service.setPlayer(mediaPlayer);

        service.stop();

        Mockito.verify(listener).onError(Mockito.any(String.class));
    }

}