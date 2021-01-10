package org.chunghyun.plannerforplanman.Add_Plan;

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
import org.chunghyun.plannerforplanman.dataBase.Add_Plan_MyEntity;
import org.chunghyun.plannerforplanman.dataBase.MyDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;


public class Add_Plan_MyListAdapter extends RecyclerView.Adapter<Add_Plan_MyListAdapter.ViewHolder> {

    private List<Add_Plan_MyEntity> items = new ArrayList<>();
    private MyDatabase db;
    private ViewGroup rootView;

    public Add_Plan_MyListAdapter(MyDatabase db){
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Add_Plan_MyListAdapter.ViewHolder holder, int position) {
        holder.onBind(items.get(position),position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView content;
        private CheckBox complete;
        private TextView page;
        private int index;

        public ViewHolder(View itemView){
            super(itemView);
            content = (TextView)itemView.findViewById(R.id.todayContent);
            complete = (CheckBox)itemView.findViewById(R.id.isComplete);
            page = (TextView)itemView.findViewById(R.id.page);
            complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // 현재 페이지 받아오기
                    String tmp = content.getText().toString().split(" ")[0];
                    AtomicInteger curPage = new AtomicInteger();
                    AtomicInteger totalPage = new AtomicInteger();
                    if(isChecked){
                        editData(content.getText().toString(), 1);
                        content.setTextColor(rootView.getResources().getColor(R.color.checked));
                        //체크시 홈페이지 업데이트
                        if(Integer.parseInt(page.getText().toString())>0){
                            new Thread(()->{
                                curPage.set(db.home_plan_dao().getCur(tmp));
                                totalPage.set(db.home_plan_dao().getPage(tmp));
                                if(totalPage.get() < curPage.get() + Integer.parseInt(page.getText().toString())){
                                    db.home_plan_dao().curUnitUpdate(tmp, totalPage.get());
                                }else{
                                    db.home_plan_dao().curUnitUpdate(tmp, curPage.get() + Integer.parseInt(page.getText().toString()));
                                }

                            }).start();
                        }
                    }else{
                        editData(content.getText().toString(), 0);
                        content.setTextColor(rootView.getResources().getColor(R.color.black));
                        if(Integer.parseInt(page.getText().toString())>0){
                            new Thread(()->{
                                curPage.set(db.home_plan_dao().getCur(tmp));
                                if(curPage.get() - Integer.parseInt(page.getText().toString() )< 0){
                                    db.home_plan_dao().curUnitUpdate(tmp, 0);
                                }else{
                                    db.home_plan_dao().curUnitUpdate(tmp, curPage.get() - Integer.parseInt(page.getText().toString()));
                                }
                            }).start();
                        }
                    }
                }
            });
            // 카드뷰 클릭시 편집 기능
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.parseInt(page.getText().toString()) == 0) {
                        Add_Plan_CustomDialog dialog = new Add_Plan_CustomDialog(rootView.getContext());
                        dialog.setText(content.getText().toString());
                        dialog.setDialogListener(new Add_Plan_CustomDialog.CustomDialogListener() {
                            @Override
                            public void onPositiveClicked(String content) {
                                if (!content.equals("")) {
                                    editData(content, 0);
                                } else
                                    Toast.makeText(rootView.getContext(), "한 글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNegativeClicked() {
                            }
                        });
                        dialog.show();
                    }else{
                        Toast.makeText(rootView.getContext(), "간편 일정은 수정이 불가합니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        // 데이터 화면 표시
        public void onBind(Add_Plan_MyEntity memo, int position){
            index = position;
            content.setText(memo.getContent());
            if(memo.getIsChecked() == 0)
                complete.setChecked(false);
            else
                complete.setChecked(true);
            page.setText(memo.getPage() + "");
        }

        // 일정 데이터 베이스 수정
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
