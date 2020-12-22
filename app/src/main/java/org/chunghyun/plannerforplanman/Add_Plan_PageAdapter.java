package org.chunghyun.plannerforplanman;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Add_Plan_PageAdapter extends RecyclerView.Adapter<Add_Plan_PageAdapter.ViewHolder>
    implements Add_Plan_OnNoteItemClickListener {

    ArrayList<Add_Plan_Note> items = new ArrayList<Add_Plan_Note>();
    int layoutType = 0;
    Add_Plan_OnNoteItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_add_plan, viewGroup, false);

        return new ViewHolder(itemView, listener, layoutType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Add_Plan_Note item = items.get(position);
        holder.setItem(item);
        holder.setLayoutType(layoutType);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Add_Plan_Note item){
        items.add(item);
    }

    public void setItems(ArrayList<Add_Plan_Note> items){
        this.items = items;
    }

    public Add_Plan_Note getItem(int position){
        return items.get(position);
    }

    public void setOnItemClickListener(Add_Plan_OnNoteItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(Add_Plan_PageAdapter.ViewHolder holder, View view, int position) {
        if(listener != null)
            listener.onItemClick(holder, view, position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout2;

        CheckBox isComplete;
        TextView content;

        public ViewHolder(View itemView, final Add_Plan_OnNoteItemClickListener listener, int layoutType){
            super(itemView);
            layout2 = itemView.findViewById(R.id.layout2);
            isComplete = itemView.findViewById(R.id.isComplete);
            content = itemView.findViewById(R.id.todaycontent);

            // 메모 칸 press event
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Add_Plan_CustomDialog customDialog = new Add_Plan_CustomDialog(itemView.getContext());
                    customDialog.callFunction();
                }
            });
        }

        public void setItem(Add_Plan_Note item){
            isComplete.setChecked(item.isComplete());
            content.setText(item.getContent());
        }
        public void setLayoutType(int layoutType){
            if(layoutType == 0)
                layout2.setVisibility(View.VISIBLE);
        }
    }

}
