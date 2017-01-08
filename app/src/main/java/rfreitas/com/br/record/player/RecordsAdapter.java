package rfreitas.com.br.record.player;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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

    private Context context;
    private List<Record> records;

    private RecordAdapterListener listener;

    private int selected = -1;
    private ColorStateList defaultColorValues;

    public RecordsAdapter(Context context, RecordAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void addItens(List<Record> records){
        this.records = records;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);
        Holder holder = new Holder(row);

        defaultColorValues = holder.v1.getTextColors();

        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final Record record = records.get(position);

        holder.v1.setText(record.getFilename());
        holder.v1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                holder.row.setSelected(true);
                holder.v1.setTextColor(Color.WHITE);

                if(selected != -1){
                    notifyItemChanged(selected);
                }

                selected = position;
                listener.onSelectItemListener(position, record);
            }

        });

        holder.row.setSelected(false);
        holder.v1.setTextColor(defaultColorValues);
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

        View row;

        public Holder(View itemView) {
            super(itemView);

            row = itemView;
            ButterKnife.bind(this, itemView);
        }

    }

    public interface RecordAdapterListener {

        public void onSelectItemListener(int position, Record record);

    }

}
