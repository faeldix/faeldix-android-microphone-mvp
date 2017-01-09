package rfreitas.com.br.record.record;

import android.media.MediaRecorder;

import java.util.UUID;

/**
 * Created by rafael-iteris on 1/6/17.
 */

public class RecordPresenter {

    private RecordPresenterListener listener;
    private RecordService service;
    private RecordView view;

    public void setView(RecordView view){
        this.view = view;
    }

    public void setRecordPresenterListener(RecordPresenterListener listener){
        this.listener = listener;
        this.service = new RecordService();
    }

    public void setRecordService(RecordService service){
        this.service = service;
    }

    public void startRecord(String path, String filename) {

        if(listener == null)
            throw new IllegalStateException("O listener deve ser setado antes de iniciar a gravacao");

        if(view == null)
            throw new IllegalStateException("A view deve ser setada antes de iniciar a gravacao");

        service.setMediaRecord(new MediaRecorder());
        service.setRecordServiceListener(getRecordServiceListener());

        Record record = new Record(path, filename, 60 * 1000);
        service.record(record);
    }

    public void stopRecord() {
        service.stop();
    }

    public RecordService.RecordServiceListener getRecordServiceListener(){
        return new RecordService.RecordServiceListener() {

            @Override
            public void onStartRecord() {
                listener.onInitRecord();
            }

            @Override
            public void onStop() {
                listener.onStopRecord();

            }

            @Override
            public void onMaxTimeIsReached() {
                view.showTimeIsReached();
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }

        };
    }

    public interface RecordPresenterListener {

        public void onInitRecord();
        public void onStopRecord();

    }

}
