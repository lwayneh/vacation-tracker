package edu.wgu.awesomevacationtracker.Activities;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import edu.wgu.awesomevacationtracker.Entities.Vacation;
import edu.wgu.awesomevacationtracker.R;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {

    class VacationViewHolder extends RecyclerView.ViewHolder{
        private final TextView vacationItemID;
        private final TextView vacationItemTitle;

        private VacationViewHolder(View itemView){
            super(itemView);
            vacationItemID = itemView.findViewById(R.id.itemID);
            vacationItemTitle = itemView.findViewById(R.id.itemTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Vacation current=mVacations.get(position);
                    Intent intent=new Intent(context,VacationDetails.class);
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                    dateFormat.format(current.getVacationStartDate())
                    intent.putExtra("id", current.getVacationID());
                    intent.putExtra("title", current.getVacationTitle());
                    intent.putExtra("hotel", current.getVacationHotel());
                    intent.putExtra("startDate",  current.getVacationStartDate());
                    intent.putExtra("endDate",  current.getVacationEndDate());
                    intent.putExtra("price", current.getVacationPrice());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflater;

    public VacationAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }


    @NonNull
    @Override
    public VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.vacation_list_item,parent,false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationViewHolder holder, int position) {
        if(mVacations!=null){
            Vacation current=mVacations.get(position);
            String title=current.getVacationTitle();
            String id = String.valueOf(current.getVacationID());
            holder.vacationItemID.setText(id);
            holder.vacationItemTitle.setText(title);
        }
        else{
            holder.vacationItemTitle.setText("No Vacation Name");
            holder.vacationItemTitle.setText("");
        }
    }

    public void setVacations(List<Vacation> vacations){
        mVacations=vacations;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mVacations.size();
    }
}
