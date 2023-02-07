package wgu.c196.c196scheduler_niermeyer.ui;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wgu.c196.c196scheduler_niermeyer.R;
import wgu.c196.c196scheduler_niermeyer.activities.CourseList;
import wgu.c196.c196scheduler_niermeyer.components.Term;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private final Button termDetailButton;
        // private final Button termDeleteButton;

        public TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.term_list_item);
            termDetailButton = itemView.findViewById(R.id.term_item_info_graphic);
            // termDeleteButton = itemView.findViewById(R.id.term_item_delete_graphic);

            termDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term cur = mTerms.get(position);
                    /**
                     *  Update Term Data in Preferences
                     */
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor prefEdit = pref.edit();
                    prefEdit.putInt("termId", cur.getId());
                    prefEdit.putString("termName", cur.getName());
                    prefEdit.putString("termStart", cur.getStartDate());
                    prefEdit.putString("termEnd", cur.getEndDate());
                    prefEdit.apply();
                    Intent intent = new Intent(context, CourseList.class);
                    context.startActivity(intent);

                }
            });
        }

        public void bind(String text) {
            termItemView.setText(text);
        }
    }

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view holder, which defines the UI of the list item
        View view = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (mTerms != null) {
            Term cur = mTerms.get(position);
            holder.bind(cur.getName());
        } else {
            holder.bind("No terms found.");
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

    public Term getTermAtPosition(int position) {
        return mTerms.get(position);
    }

}
