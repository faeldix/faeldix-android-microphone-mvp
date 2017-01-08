package rfreitas.com.br.record.record;

import android.app.Activity;

/**
 * Created by rafael-iteris on 1/6/17.
 */

public interface RecordView {

    public Activity getContextActivity();

    public void showProgressOfRecord();
    public void stopProgressOfRecord();

    public void showError(String error);
    public void showTimeIsReached();

    public void reload();

}
