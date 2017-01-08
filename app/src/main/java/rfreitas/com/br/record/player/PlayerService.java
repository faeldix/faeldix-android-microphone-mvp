package rfreitas.com.br.record.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.TimedText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rfreitas.com.br.record.BuildConfig;
import rfreitas.com.br.record.record.Record;

/**
 * Created by rafaelfreitas on 1/7/17.
 */

public class PlayerService {

    private File directory;
    private MediaPlayer player;
    private PlayerServiceListener listener;

    public PlayerService() {}

    public PlayerService(File directoryOfRecords, PlayerServiceListener listener){
        this.directory = directoryOfRecords;
        this.listener = listener;
        this.player = new MediaPlayer();
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    public void setListener(PlayerServiceListener listener) {
        this.listener = listener;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public void play(Record record) {

        if(listener == null)
            throw new IllegalStateException("O listener nao pode ser nulo");

        if(player == null)
            throw new IllegalStateException("O player nao pode ser nulo");

        try {
            player.setDataSource(record.getFullPath());
            player.prepare();
            player.start();

            listener.onStartPlayer();
        } catch (Exception e){
            e.printStackTrace();

            listener.onError("Ocorreu um erro no momento de iniciar a reprodução.");
        }

    }

    public void stop(){

        try {
            player.stop();
            player.reset();

            listener.onStopPlayer();
        } catch (Exception e){
            e.printStackTrace();

            listener.onError("Ocorreu um erro no momento de parar a reproducão.");
        }

    }

    public List<Record> getRecords(){
        List<Record> records = new ArrayList<Record>();
        File[] files = directory.listFiles();

        for(File file : files){
            if(file.getName().contains("instant") || file.isDirectory())
                 continue;

            Record record = new Record(directory.getAbsolutePath(), file.getName(), 60 * 1000);
            records.add(record);
        }

        return records;
    }

    public interface PlayerServiceListener{

        public void onStartPlayer();
        public void onStopPlayer();
        public void onError(String erro);

    }

}
