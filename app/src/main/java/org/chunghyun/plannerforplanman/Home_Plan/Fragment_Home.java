package org.chunghyun.plannerforplanman.Home_Plan;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.chunghyun.plannerforplanman.R;

import java.util.List;

public class Fragment_Home extends Fragment implements View.OnClickListener{

    private Home_Plan_homeAdapter adapter;
    private RecyclerView recyclerView;
    private Home_Plan_homeDatabase db;
    private ViewGroup rootView;
    private Paint p = new Paint();

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
        initSwipe();
        db = Home_Plan_homeDatabase.getDatabase(getContext());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Home_Plan_homeAdapter(db);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        // UI 갱신
        db.myDao().getAll().observe(getActivity(), new Observer<List<Home_Plan_Entity>>() {
            @Override
            public void onChanged(List<Home_Plan_Entity> home_plan_entities) {
                adapter.setItem(home_plan_entities);
            }
        });
        // 프래그먼트간 데이터 이동
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Home_CustomDialog dialog = new Home_CustomDialog(rootView.getContext());
                dialog.setDialogListener(new Home_CustomDialog.CustomDialogListener(){
                    @Override
                    public void onPositiveClicked(String bookName, int totalUnit, int dDay) {
                        if (!bookName.equals("")) {
                            new Thread(() -> {
                                Home_Plan_Entity memo = new Home_Plan_Entity(bookName, totalUnit, dDay);
                                db.myDao().insert(memo);
                            }).start();
                        } else
                            Toast.makeText(rootView.getContext(), "책 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNegativeClicked() { }
                });
                dialog.show();
                break;
        }
    }
    // 스와이프 해서 삭제하기
    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db.myDao().delete(adapter.getItems().get(position));
                        }
                    }).start();

                } else {
                    //오른쪽으로 밀었을때.
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        //오른쪽으로 밀었을 때
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        /*
                         * icon 추가할 수 있음.
                         */
                        //icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon_png); //vector 불가!
                        // RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        //c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}