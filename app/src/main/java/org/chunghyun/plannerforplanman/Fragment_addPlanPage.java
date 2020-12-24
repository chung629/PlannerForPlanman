package org.chunghyun.plannerforplanman;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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