package org.chunghyun.plannerforplanman.DelayPlan;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.chunghyun.plannerforplanman.Add_Plan.Add_Plan_MyListAdapter;
import org.chunghyun.plannerforplanman.R;
import org.chunghyun.plannerforplanman.dataBase.Add_Plan_MyEntity;
import org.chunghyun.plannerforplanman.dataBase.MyDatabase;

import java.util.ArrayList;
import java.util.List;

public class Delay_Adapter extends RecyclerView.Adapter<Delay_Adapter.ViewHolder>{

    private Context context;
    private ArrayList<String> dates = new ArrayList<>();
    private LayoutInflater inflater;
    private MyDatabase db;
    private Paint p = new Paint();
    private Add_Plan_MyListAdapter adapter;
    private RecyclerView recyclerView;

    public Delay_Adapter(Context context,  MyDatabase db){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.item_delay, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] temp = dates.get(position).split("-");
        holder.planDate.setText(temp[0] + "년 " + temp[1] + "월 " + temp[2] + "일");
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        // UI 갱신
        db.myDao().getData(dates.get(position)).observe((LifecycleOwner) context, new Observer<List<Add_Plan_MyEntity>>() {
            @Override
            public void onChanged(List<Add_Plan_MyEntity> add_plan_myEntities) {
                adapter.setItem(add_plan_myEntities);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public void setItem(List<String> dates){
        this.dates = new ArrayList<>(dates);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView planDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.delayView);
            planDate = itemView.findViewById(R.id.planDate);
            adapter = new Add_Plan_MyListAdapter(db);
        }
    }
}
