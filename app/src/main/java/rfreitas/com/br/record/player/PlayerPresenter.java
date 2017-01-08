package rfreitas.com.br.record.player;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.List;

import rfreitas.com.br.record.record.Record;

/**
 * Created by rafaelfreitas on 1/7/17.
 */

public class PlayerPresenter implements PlayerService.PlayerServiceListener {

    private Context context;

    private PlayerService service;
    private PlayerView view;

    public PlayerPresenter() {}

    public PlayerPresenter(Context context){
        this.context = context;
        this.service = new PlayerService(context.getFilesDir(), this);
    }

    public void setView(PlayerView view) {
        this.view = view;
    }

    public void setService(PlayerService service) {
        this.service = service;
    }

    public void play(Record record){
        service.play(record);
    }

    public void stop(){
        service.stop();
    }

    public List<Record> getRecords(){
        return service.getRecords();
    }

    @Override
    public void onStartPlayer() {
        view.showProgressOfPlayer();
    }

    @Override
    public void onStopPlayer() {
        view.stopProgressOfPlayer();
    }

    @Override
    public void onError(String erro) {
        view.showError(erro);
    }


}
