package rfreitas.com.br.record.player;

import android.app.Activity;

/**
 * Created by rafaelfreitas on 1/7/17.
 */

public interface PlayerView {

    public void enablePlayerButton();

    public void showProgressOfPlayer();
    public void stopProgressOfPlayer();

    public void showError(String error);
    public void reload();

    public Activity getContextActivity();

}
