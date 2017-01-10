package rfreitas.com.br.record.player;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rfreitas.com.br.record.R;
import rfreitas.com.br.record.record.Record;

/**
 * Created by rafaelfreitas on 1/7/17.
 */

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.Holder> {

    private RecordAdapterListener listener;
    private List<Record> records;
    private Context context;

    private int selected = -1;

    public RecordsAdapter(Context context, RecordAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void addItens(List<Record> records){
        this.records = records;
    }

    public void setListener(RecordAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);
        Holder holder = new Holder(row);

        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final Record record = records.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(selected != -1){
                    notifyItemChanged(selected);
                }

                listener.onSelectItemListener(position, record);
                notifyItemChanged(selected = position);
            }

        });

        holder.v1.setText(record.getFilename());
        holder.setSelected(selected == position);
    }

    @Override
    public int getItemCount() {

        if(records == null)
            return 0;

        return records.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        @BindView(android.R.id.text1)
        TextView v1;

        private int defaultTextColor;

        public Holder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            defaultTextColor = v1.getCurrentTextColor();
        }

        public void setSelected(boolean selected){
            itemView.setSelected(selected);
            v1.setTextColor(selected ? Color.WHITE : defaultTextColor);
        }

    }

    public interface RecordAdapterListener {

        public void onSelectItemListener(int position, Record record);

    }

}
