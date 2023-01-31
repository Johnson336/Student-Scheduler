package wgu.c196.c196scheduler_niermeyer.activities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wgu.c196.c196scheduler_niermeyer.R;
import wgu.c196.c196scheduler_niermeyer.components.Term;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private TermViewHolder(View itemView){
            super(itemView);
            termItemView=itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term cur = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                }
            });
        }
    }

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (mTerms != null) {
            Term cur = mTerms.get(position);
            String name = cur.getName();
            holder.termItemView.setText(name);
        } else {
            holder.termItemView.setText("Nothing found.");
        }
    }

    @Override
    public int getItemCount() {
        if (mTerms != null) {
            return mTerms.size();
        } else {
            return 0;
        }
    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }



}
