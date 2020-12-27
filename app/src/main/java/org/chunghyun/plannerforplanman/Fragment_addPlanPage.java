package org.chunghyun.plannerforplanman;

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

import java.util.List;

public class Fragment_addPlanPage extends Fragment implements View.OnClickListener {

    private Add_Plan_MyListAdapter adapter;
    private RecyclerView recyclerView;
    private Add_Plan_MyDatabase db;
    private ViewGroup rootView;
    private Paint p = new Paint();

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
    private void initUI() {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview2);
        initSwipe();
        db = Add_Plan_MyDatabase.getDatabase(getContext());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Add_Plan_MyListAdapter(db);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        // UI 갱신
        db.myDao().getAll().observe(getActivity(), new Observer<List<Add_Plan_MyEntity>>() {
            @Override
            public void onChanged(List<Add_Plan_MyEntity> add_plan_myEntities) {
                adapter.setItem(add_plan_myEntities);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Add_Plan_CustomDialog dialog = new Add_Plan_CustomDialog(rootView.getContext());
                dialog.setDialogListener(new Add_Plan_CustomDialog.CustomDialogListener() {
                    @Override
                    public void onPositiveClicked(String content) {
                        if (!content.equals("")) {
                            new Thread(() -> {
                                Add_Plan_MyEntity memo = new Add_Plan_MyEntity(content, 0);
                                db.myDao().insert(memo);
                            }).start();
                        } else
                            Toast.makeText(rootView.getContext(), "한 글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNegativeClicked() {
                    }
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