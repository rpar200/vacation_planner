package com.erde.erde_vacation_planner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erde.erde_vacation_planner.R;
import com.erde.erde_vacation_planner.entities.Excursion;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    //Encapsulation can be seen here. These are private fields only accessible within this class.
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;
        private final TextView excursionItemView2;

        public ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView3);
            excursionItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Excursion current = mExcursions.get(position);
                Intent intent = new Intent(context, ExcursionDetails.class);
                intent.putExtra("id", current.getExcursionID());
                intent.putExtra("name", current.getExcursionName());
                intent.putExtra("startdate", current.getStartDate());
                intent.putExtra("vacationid", current.getVacationID());
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
        // Display excursion name instead of the android:text for the TextView
        // The recycler view displays a report of the excursion name and the date of the excursion.
        if (mExcursions != null) {
            Excursion current = mExcursions.get(position);
            String name = current.getExcursionName();
            String startDate = current.getStartDate();
            holder.excursionItemView.setText(current.getExcursionName());
            holder.excursionItemView2.setText(current.getStartDate());
        } else {
            holder.excursionItemView.setText("N/A");
            holder.excursionItemView2.setText("N/A");
        }
    }

    @Override
    public int getItemCount() {
        //return the size of the excursions list. If list isn't initialized, return 0.
        if (mExcursions != null) {
            return mExcursions.size();
        } else return 0;
    }

    public void setExcursions(List<Excursion> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();
    }
}
