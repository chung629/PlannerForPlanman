package org.chunghyun.plannerforplanman.Home_Plan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinuscxj.progressbar.CircleProgressBar;

import org.chunghyun.plannerforplanman.R;
import org.chunghyun.plannerforplanman.dataBase.Home_Plan_Entity;
import org.chunghyun.plannerforplanman.dataBase.MyDatabase;

import java.util.ArrayList;
import java.util.List;

public class Home_Plan_homeAdapter extends RecyclerView.Adapter<Home_Plan_homeAdapter.ViewHolder>{

    private List<Home_Plan_Entity> items = new ArrayList<>();
    private MyDatabase db;
    private ViewGroup rootView;


    public Home_Plan_homeAdapter(MyDatabase db){
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
        rootView = parent;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Home_Plan_homeAdapter.ViewHolder holder, int position) {
        holder.onBind(items.get(position),position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView bookName;
        private TextView totalPage;
        private TextView curPage;
        private LinearLayout editBookName;
        private int index;
        //프로그래스바 관련
        private CircleProgressBar progressBar;

        public ViewHolder(View itemView){
            super(itemView);
            bookName = (TextView)itemView.findViewById(R.id.bookname);
            totalPage = (TextView)itemView.findViewById(R.id.totalPage);
            curPage = (TextView)itemView.findViewById(R.id.curPage);
            editBookName = (LinearLayout)itemView.findViewById(R.id.editBookName);
            bookName.setSelected(true);
            // 프로그래스바
            progressBar = itemView.findViewById(R.id.state_progress);
            // 클릭시 편집 및 변경
            editBookName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Home_CustomDialog dialog = new Home_CustomDialog(rootView.getContext());
                    dialog.setText(bookName.getText().toString(), totalPage.getText().toString(), curPage.getText().toString());
                    dialog.setDialogListener(new Home_CustomDialog.CustomDialogListener(){
                        @Override
                        public void onPositiveClicked(String bookName, int totalUnit, int curUnit) {
                            if (!bookName.equals("")) {
                                editData(bookName, totalUnit, curUnit);
                            } else
                                Toast.makeText(rootView.getContext(), "책 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onNegativeClicked() { }
                    });
                    dialog.show();
                }
            });

        }

        // 리사이클뷰 UI
        public void onBind(Home_Plan_Entity memo, int position){
            index = position;
            bookName.setText(memo.getBookName());
            curPage.setText(memo.getCurUnit() + "");
            totalPage.setText(memo.getTotalUnit() + "");
            // 프로그래바 UI
            progressBar.setMax(memo.getTotalUnit());
            progressBar.setProgress(memo.getCurUnit());
        }
        // 총괄 수정
        public void editData(String bookName, int totalUnit, int curUnit){
            new Thread(()->{
                items.get(index).setBookName(bookName);
                items.get(index).setTotalUnit(totalUnit);
                items.get(index).setCurUnit(curUnit);
                db.home_plan_dao().update(items.get(index));
                db.home_plan_dao().deleteAddPlan(bookName);
            }).start();
        }
    }
    public void setItem(List<Home_Plan_Entity> data){
        items = data;
        notifyDataSetChanged();
    }
}
