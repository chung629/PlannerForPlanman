package org.chunghyun.plannerforplanman;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Dictionary;

public class Fragment_Home extends Fragment implements View.OnClickListener{

    private ArrayList<Home_Note> noteItem;
    private Home_NoteAdapter adapter;
    private RecyclerView recyclerView;
    private ViewGroup rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment__home, container, false);
        initUI();

        return rootView;
    }
    // 레이아웃 변수 할당
    private void initUI(){
        recyclerView = rootView.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        noteItem = new ArrayList<Home_Note>();
        adapter = new Home_NoteAdapter(getActivity(), noteItem);
        //test 메시지
        noteItem.add(new Home_Note("정보처리기사",  "20201201", "20201218"));
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

        @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Home_CustomDialog dialog = new Home_CustomDialog(rootView.getContext());
                dialog.setDialogListener(new Home_CustomDialog.CustomDialogListener(){
                    @Override
                    public void onPositiveClicked(String name, String start, String end) {
                        noteItem.add(new Home_Note(name, start, end));
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