package org.chunghyun.plannerforplanman;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Add_Plan_PageAdapter extends RecyclerView.Adapter<Add_Plan_PageAdapter.CustomViewHolder> {

    private Context context;
    ArrayList<Add_Plan_Note> items;
    ViewGroup rootView;

    public Add_Plan_PageAdapter(Context context, ArrayList<Add_Plan_Note> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Add_Plan_PageAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        rootView = viewGroup;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_add_plan, viewGroup, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        // 데이터셋에서 가져온 데이터를 뷰에 넣는 함수
        holder.content.setText(items.get(position).getContent());
        // 체크 박스 이벤트
        holder.complete.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(holder.complete.isChecked()){
                    holder.content.setTextColor(ContextCompat.getColor(context, R.color.checked));
                }else{
                    holder.content.setTextColor(Color.BLACK);
                }
            }
        });
        holder.itemView.setTag(position);
        // 내용 수정
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 아이템 길게 눌렀을때 액션
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    // 아이템 추가
    public void addItem(Add_Plan_Note item){
        items.add(item);
        notifyDataSetChanged();
    }

    // 아이템 삭제
    public void remove(int position){
        try{
            items.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
        TextView content;
        CheckBox complete;

        public CustomViewHolder(View itemView){
            super(itemView);
            layout = itemView.findViewById(R.id.layout2);
            content = (TextView)itemView.findViewById(R.id.todaycontent);
            complete = (CheckBox)itemView.findViewById(R.id.isComplete);
        }
    }
}
