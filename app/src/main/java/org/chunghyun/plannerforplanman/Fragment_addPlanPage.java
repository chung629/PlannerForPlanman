package org.chunghyun.plannerforplanman;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Fragment_addPlanPage extends Fragment {

    RecyclerView recyclerView2;
    Add_Plan_PageAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_add_plan_page, container, false);
        initUI(rootView);
        return rootView;
    }
    private void initUI(ViewGroup rootView){
        recyclerView2 = rootView.findViewById(R.id.recyclerview2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(layoutManager);
        adapter = new Add_Plan_PageAdapter();
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Plan_CustomDialog customDialog = new Add_Plan_CustomDialog(rootView.getContext());
                customDialog.callFunction();
            }
        });
        //test 메시지
        adapter.addItem(new Add_Plan_Note(false, "내용 부분입니다"));
        recyclerView2.setAdapter(adapter);
    }
}