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
import com.erde.erde_vacation_planner.entities.Vacation;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {
    //Encapsulation can be seen here. These are private fields only accessible within this class.
    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflater;

    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;


        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationItemView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Vacation current = mVacations.get(position);
                Intent intent = new Intent(context, VacationDetails.class);
                intent.putExtra("id", current.getVacationID());
                intent.putExtra("name", current.getVacationName());
                intent.putExtra("housing", current.getHousing());
                intent.putExtra("startdate", current.getStartDate());
                intent.putExtra("enddate", current.getEndDate());
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.vacation_list_item, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        // Display vacation name instead of the android:text for the TextView
        if (mVacations != null) {
            Vacation current = mVacations.get(position);
            holder.vacationItemView.setText(current.getVacationName());
        } else {
            holder.vacationItemView.setText("N/A");
        }
    }

    @Override
    public int getItemCount() {
        //return the size of the vacations list. If list isn't initialized, return 0.
        if (mVacations != null) {
            return mVacations.size();
        } else return 0;
    }

    public void setVacations(List<Vacation> vacations) {
        mVacations = vacations;
        notifyDataSetChanged();
    }


}
