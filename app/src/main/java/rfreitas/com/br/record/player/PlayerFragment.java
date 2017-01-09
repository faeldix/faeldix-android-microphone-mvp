package rfreitas.com.br.record.player;

import android.app.Activity;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import rfreitas.com.br.record.R;
import rfreitas.com.br.record.record.Record;

/**
 * Created by rafaelfreitas on 1/7/17.
 */

public class PlayerFragment extends Fragment implements PlayerView, RecordsAdapter.RecordAdapterListener {

    @BindView(R.id.list_record)
    RecyclerView recycler;

    @BindView(R.id.btn_player)
    Button button;

    @BindView(R.id.time)
    TextView time;

    private Toast toastError, toastStop;

    private RecordsAdapter adapter;
    private PlayerPresenter presenter;

    private Timer timer;

    private Record selected;
    private int seconds = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_fragment, container, false);
        ButterKnife.bind(this, view);

        button.setOnTouchListener(getOnTouchListener());
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PlayerPresenter presenter = new PlayerPresenter(getActivity());
        presenter.setView(this);

        setPresenter(presenter);

        adapter = new RecordsAdapter(getActivity(), this);
        recycler.setAdapter(adapter);
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void setPresenter(PlayerPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();

        List<Record> records = presenter.getRecords();
        adapter.addItens(records);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void enablePlayerButton() {
        button.setEnabled(true);
    }

    @Override
    public void showProgressOfPlayer() {
        timer = getNewTimer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        time.setText(DateUtils.formatElapsedTime(seconds--));
                    }

                });

            }

        }, 0, 1000);

        time.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgressOfPlayer() {
        if(toastStop == null || toastStop.getView().isShown()){
            toastStop = Toast.makeText(getActivity(), "Reprodução finalizada", Toast.LENGTH_SHORT);
            toastStop.show();

            reload();
        }
    }

    @Override
    public void showError(String error) {
        if(toastError == null || toastError.getView().isShown()){
            toastError = Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT);
            toastError.show();

            reload();
        }
    }

    @Override
    public void reload() {
        time.setVisibility(View.INVISIBLE);
        time.setText("00:00");

        seconds = 0;
        timer.cancel();
        timer.purge();
    }

    @Override
    public Activity getContextActivity() {
        return getActivity();
    }

    @Override
    public void onSelectItemListener(int position, Record record) {
        enablePlayerButton();

        this.selected = record;
        this.seconds = getSecondsOfARecord(record);
    }

    // TODO remover esse método da view
    public int getSecondsOfARecord(Record record){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(record.getFullPath());

        return Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 1000;
    }

    public View.OnTouchListener getOnTouchListener(){
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        presenter.play(selected);

                        break;
                    case MotionEvent.ACTION_UP:
                        presenter.stop();

                        break;
                }

                return false;
            }
        };
    }

    public Timer getNewTimer(){
        return new Timer();
    }

    public Toast getToastError() {
        return toastError;
    }

    public Toast getToastStop() {
        return toastStop;
    }

    public RecordsAdapter getAdapter() {
        return adapter;
    }
}
