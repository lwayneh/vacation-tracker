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

import edu.wgu.awesomevacationtracker.Entities.Excursion;
import edu.wgu.awesomevacationtracker.R;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    static Excursion currentExcursion;

    class ExcursionViewHolder extends RecyclerView.ViewHolder{
        private final TextView excursionItemID;
        private final TextView excursionItemTitle;

        private ExcursionViewHolder(View itemView){
            super(itemView);
            excursionItemID=itemView.findViewById(R.id.itemID);
            excursionItemTitle=itemView.findViewById(R.id.itemTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Excursion current= mExcursions.get(position);
                    currentExcursion = current;
                    Intent intent=new Intent(context,ExcursionDetails.class);
                    intent.putExtra("id", current.getExcursionID());
                    intent.putExtra("title", current.getExcursionTitle());
                    intent.putExtra("price", current.getExcursionPrice());
                    intent.putExtra("date", current.getExcursionDate());
                    intent.putExtra("vacationID",current.getExcursionVID());
                    intent.putExtra("vacationTitle",current.getExcursionVacationTitle());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    public ExcursionAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.excursion_list_item,parent,false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
        try{
            Excursion current= mExcursions.get(position);
            String title=current.getExcursionTitle();
            String iD = String.valueOf(current.getExcursionID());
            holder.excursionItemID.setText(iD);
            holder.excursionItemTitle.setText(title);
        } catch(Exception e){
            System.out.println("DEBUG ~~~~~~~~~~~~~ + "+e);
            holder.excursionItemTitle.setText("No Excursion Title");
            holder.excursionItemID.setText("");

        }
    }

    public void setExcursions(List<Excursion> excursions){
        mExcursions=excursions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mExcursions.size();
    }
}
