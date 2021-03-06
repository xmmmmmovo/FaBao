package com.example.lab.android.nuc.law_analysis.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.bean.LawEntry;

import java.util.List;
import java.util.zip.Inflater;

public class LawListAdapter extends RecyclerView.Adapter<LawListAdapter.ViewHolder> {
    private List<LawEntry> lawItems;
    private Inflater inflater;
    private OnItemClickLitener mOnItemClickLitener;

    public LawListAdapter(List<LawEntry> laws){
        this.lawItems = laws;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.law_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindHolder(lawItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return lawItems.size();
    }

    public interface OnItemClickLitener
    {
        void onItemClick(LawEntry lawEntry);
        void onItemLongClick(LawEntry lawEntry);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView law_line;
        private TextView law_content;
        private TextView fileNumber;
        private TextView law_from;

        public ViewHolder(View itemView) {
            super(itemView);
            law_line = itemView.findViewById(R.id.law_line_text_view);
            law_content = itemView.findViewById(R.id.law_content_text_view);
            law_from = itemView.findViewById(R.id.law_from_text_view);
            fileNumber = itemView.findViewById(R.id.item_number_text_view);
        }

        public void bindHolder(final LawEntry law, int position) {
            fileNumber.setText((position + 1) + "");
            law_line.setText(law.getLaw_line());
            law_content.setText(law.getLaw_content());
            law_from.setText(law.getLaw_from());
            if (mOnItemClickLitener != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mOnItemClickLitener.onItemLongClick(law);
                        return true;
                    }
                });
            }
        }
    }
}
