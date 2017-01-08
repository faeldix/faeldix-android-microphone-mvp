package rfreitas.com.br.record.record;

import android.media.MediaRecorder;

/**
 * Created by rafael-iteris on 1/6/17.
 */

public class RecordService implements MediaRecorder.OnInfoListener, MediaRecorder.OnErrorListener {

    private MediaRecorder mRecorder;
    private RecordServiceListener listener;

    public void setMediaRecord(MediaRecorder recorder){
        this.mRecorder = recorder;
    }

    public void setRecordServiceListener(RecordServiceListener listener){
        this.listener = listener;
    }

    public void record(Record record) {

        if(mRecorder == null)
            throw new IllegalStateException("O media record deve ser setado antes de comecar a gravar.");

        if(listener == null)
            throw new IllegalStateException("O listener deve ser setado antes de comecar a gravar");

        mRecorder.setOutputFile(record.getFullPath());
        mRecorder.setMaxDuration(record.getMax());

        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        mRecorder.setOnInfoListener(this);
        mRecorder.setOnErrorListener(this);

        try {
            mRecorder.prepare();
            mRecorder.start();

            listener.onStartRecord();
        } catch (Exception e) {
            e.printStackTrace();

            listener.onError("Ocorreu um erro desconhecido, tente novamente");
            stop();
        }

    }

    public void stop(){

        try {
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();

            listener.onStop();
        } catch (Exception e) {
            e.printStackTrace();

            listener.onError("Ocorreu um erro");
        }

    }

    @Override
    public void onInfo(MediaRecorder mediaRecorder, int what, int extra) {

        if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED){
            listener.onMaxTimeIsReached();
            listener.onStop();
        }

    }

    @Override
    public void onError(MediaRecorder mediaRecorder, int i, int i1) {
        stop();

        listener.onError("Ocorreu um erro durante a gravacao. Tente novamente.");
    }

    public interface RecordServiceListener {

        public void onStartRecord();
        public void onMaxTimeIsReached();
        public void onStop();

        public void onError(String message);

    }

}
