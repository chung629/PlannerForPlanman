package org.chunghyun.plannerforplanman;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Home_NoteAdapter extends RecyclerView.Adapter<Home_NoteAdapter.ViewHolder>
        implements Home_OnNoteItemClickListener {

    ArrayList<Home_Note> items = new ArrayList<Home_Note>();
    Home_OnNoteItemClickListener listener;

    int layoutType = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_home_fragment, viewGroup, false);

        return new ViewHolder(itemView, this, layoutType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Home_Note item = items.get(position);
        holder.setItem(item);
        holder.setLayoutType(layoutType);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Home_Note item){
        items.add(item);
    }

    public void setItems(ArrayList<Home_Note> items){
        this.items = items;
    }

    public Home_Note getItem(int position){
        return items.get(position);
    }

    public void setOnItemClickListener(Home_OnNoteItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null)
            listener.onItemClick(holder, view, position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout1;

        ProgressBar state_progress;

        TextView startDate;
        TextView content;
        TextView curDate;

        public ViewHolder(View itemView, final Home_OnNoteItemClickListener listener, int layoutType){
            super(itemView);

            layout1 = itemView.findViewById(R.id.layout1);

            //state_progress = itemView.findViewById(R.id.state_progress);

            startDate = itemView.findViewById(R.id.startDate);
            content = itemView.findViewById(R.id.contentsTextView);
            curDate = itemView.findViewById(R.id.curDay);

            // 메모 칸 press event
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Home_CustomDialog customDialog = new Home_CustomDialog(itemView.getContext());
                    customDialog.callFunction();
                }
            });
        }
        public void setItem(Home_Note item){
            // 프로그래스바 작성

            // 프로그래스바 작성 끝
            startDate.setText(item.getStartDate());
            content.setText(item.getContents());
            curDate.setText(item.getCurDate());
        }
        public void setLayoutType(int layoutType){
            if(layoutType == 0)
                layout1.setVisibility(View.VISIBLE);
        }
    }
}
