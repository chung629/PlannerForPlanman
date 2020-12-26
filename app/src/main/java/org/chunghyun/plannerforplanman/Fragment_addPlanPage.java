package org.chunghyun.plannerforplanman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import static androidx.recyclerview.widget.ItemTouchHelper.*;

public class Fragment_addPlanPage extends Fragment implements View.OnClickListener{

    private ArrayList<Add_Plan_Note> noteItem;
    private Add_Plan_PageAdapter adapter;
    private RecyclerView recyclerView;
    private ViewGroup rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_add_plan_page, container, false);
        initUI();

        return rootView;
    }
    // 레이아웃 변수 할당
    private void initUI(){
        recyclerView = rootView.findViewById(R.id.recyclerview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        noteItem = new ArrayList<Add_Plan_Note>();
        adapter = new Add_Plan_PageAdapter(getActivity(), noteItem);
        //test 메시지
        adapter.addItem(new Add_Plan_Note(false, "내용 부분입니다"));
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

//        // 아이템 터치시 액션
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
//                new SimpleCallback(0, ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
//                    @Override
//                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                        return false;
//                    }
//
//                    @Override
//                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                        AlertDialog.Builder ad = new AlertDialog.Builder(rootView.getContext());
//                        ad.setTitle("삭제");
//                        ad.setMessage("일정을 삭제 하시겠습니까?");
//                        ad.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                adapter.notifyDataSetChanged();
//                                dialog.dismiss();
//                            }
//                        });
//                        ad.setNegativeButton("예", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                adapter.remove(viewHolder.getAdapterPosition());
//                                adapter.notifyDataSetChanged();
//                                dialog.dismiss();
//                            }
//                        });
//                        ad.show();
//                    }
//                });
//        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Add_Plan_CustomDialog dialog = new Add_Plan_CustomDialog(rootView.getContext());
                dialog.setDialogListener(new Add_Plan_CustomDialog.CustomDialogListener(){
                    @Override
                    public void onPositiveClicked(String content) {
                        noteItem.add(new Add_Plan_Note(false, content));
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onNegativeClicked() { }
                });
                dialog.show();
                break;
        }
    }
}