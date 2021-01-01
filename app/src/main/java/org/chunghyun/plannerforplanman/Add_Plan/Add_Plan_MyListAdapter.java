package org.chunghyun.plannerforplanman.Add_Plan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.chunghyun.plannerforplanman.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Add_Plan_MyListAdapter extends RecyclerView.Adapter<Add_Plan_MyListAdapter.ViewHolder> {

    private List<Add_Plan_MyEntity> items = new ArrayList<>();
    private Context mContext;
    private Add_Plan_MyDatabase db;
    private ViewGroup rootView;

    public Add_Plan_MyListAdapter(Add_Plan_MyDatabase db){
        this.db = db;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public List<Add_Plan_MyEntity> getItems(){
        return items;
    }

    @NonNull
    @Override
    public Add_Plan_MyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rootView = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_plan, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Add_Plan_MyListAdapter.ViewHolder holder, int position) {
        holder.onBind(items.get(position),position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView content;
        private CheckBox complete;
        private int index;

        public ViewHolder(View itemView){
            super(itemView);
            content = (TextView)itemView.findViewById(R.id.todaycontent);
            complete = (CheckBox)itemView.findViewById(R.id.isComplete);
            complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        editData(content.getText().toString(), 1);
                        content.setTextColor(rootView.getResources().getColor(R.color.checked));
                    }else{
                        editData(content.getText().toString(), 0);
                        content.setTextColor(rootView.getResources().getColor(R.color.black));
                    }
                }
            });
            // 카드뷰 클릭시 편집 기능
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Add_Plan_CustomDialog dialog = new Add_Plan_CustomDialog(rootView.getContext());
                    dialog.setDialogListener(new Add_Plan_CustomDialog.CustomDialogListener() {
                        @Override
                        public void onPositiveClicked(String content) {
                            if (!content.equals("")) {
                                editData(content, 0);
                            } else
                                Toast.makeText(rootView.getContext(), "한 글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onNegativeClicked() { }
                    });
                    dialog.show();
                }
            });
        }
        // 데이터 화면 표시
        public void onBind(Add_Plan_MyEntity memo, int position){
            index = position;
            content.setText(memo.getContent());
            if(memo.isChecked == 0)
                complete.setChecked(false);
            else
                complete.setChecked(true);
        }

        // 일정 수정
        public void editData(String contents, int isChecked){
            new Thread(()->{
                items.get(index).setContent(contents);
                items.get(index).setIsChecked(isChecked);
                items.get(index).setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
                db.myDao().update(items.get(index));
            }).start();
        }
    }

    public void setItem(List<Add_Plan_MyEntity> data){
        items = data;
        notifyDataSetChanged();
    }
}
