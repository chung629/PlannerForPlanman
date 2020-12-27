package org.chunghyun.plannerforplanman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Home_Plan_homeAdapter extends RecyclerView.Adapter<Home_Plan_homeAdapter.ViewHolder>{

    private List<Home_Plan_Entity> items = new ArrayList<>();
    private Context mContext;
    private Home_Plan_homeDatabase db;

    public Home_Plan_homeAdapter(Home_Plan_homeDatabase db){
        this.db = db;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public List<Home_Plan_Entity> getItems(){
        return items;
    }

    @NonNull
    @Override
    public Home_Plan_homeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_fragment, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Home_Plan_homeAdapter.ViewHolder holder, int position) {
        holder.onBind(items.get(position),position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView bookName;
        private TextView totalUnit;
        private TextView dDay;
        private int index;

        public ViewHolder(View itemView){
            super(itemView);
            bookName = (TextView)itemView.findViewById(R.id.bookname);
            totalUnit = (TextView)itemView.findViewById(R.id.curUnit);
            dDay = (TextView)itemView.findViewById(R.id.dday);
        }
        // 리사이클뷰 UI
        public void onBind(Home_Plan_Entity memo, int position){
            index = position;
            bookName.setText(memo.getBookName());
            totalUnit.setText(memo.getTotalUnit() + "");
            dDay.setText("D-day " + memo.getdDay());
        }
        // 일정 수정
        public void editData(String bookName, int totalUnit, long dDay){
            new Thread(()->{
                items.get(index).setBookName(bookName);
                items.get(index).setTotalUnit(totalUnit);
                items.get(index).setdDay(dDay);
                db.myDao().update(items.get(index));
            }).start();
        }
    }

    public void setItem(List<Home_Plan_Entity> data){
        items = data;
        notifyDataSetChanged();
    }
}
