package org.chunghyun.plannerforplanman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Home_NoteAdapter extends RecyclerView.Adapter<Home_NoteAdapter.CustomViewHolder> {

    private Context context;
    ArrayList<Home_Note> items;
    ViewGroup rootView;

    public Home_NoteAdapter(Context context, ArrayList<Home_Note> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Home_NoteAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        rootView = viewGroup;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_fragment, viewGroup, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        // 데이터셋에서 가져온 데이터를 뷰에 넣는 함수
        holder.name.setText(items.get(position).getName());
        holder.startDate.setText(items.get(position).getStartDate());
        holder.endDate.setText(items.get(position).getEndDate());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이템 클릭시 액션
                Home_CustomDialog dialog = new Home_CustomDialog(rootView.getContext());
                dialog.setDialogListener(new Home_CustomDialog.CustomDialogListener(){
                    @Override
                    public void onPositiveClicked(String name, String start, String end) {
                        Home_Note home_note = new Home_Note(name, start, end);
                        items.add(home_note);
                        notifyDataSetChanged();
                    }
                    @Override
                    public void onNegativeClicked() { }
                });
                dialog.show();
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

    // 아이템 삭제
    public void remove(int position){
        try{
            items.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }
    // 아이템 추가
    public void addItem(Home_Note item){
        items.add(item);
        notifyDataSetChanged();
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout1;
        TextView name;
        TextView startDate;
        TextView endDate;
//        ProgressBar state_progress;

        public CustomViewHolder(View itemView){
            super(itemView);
            layout1 = itemView.findViewById(R.id.layout1);
            name = itemView.findViewById(R.id.bookname);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.dday);
//            state_progress = itemView.findViewById(R.id.state_progress);

        }
    }
}
