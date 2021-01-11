package org.chunghyun.plannerforplanman.DelayPlan;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.chunghyun.plannerforplanman.R;
import org.chunghyun.plannerforplanman.dataBase.MyDatabase;

import java.util.ArrayList;
import java.util.List;

public class Fragment_delayplan extends Fragment {

    private ViewGroup rootView;
    private RecyclerView recyclerView;
    private MyDatabase db;
    //private Add_Plan_MyListAdapter adapter;
    private Delay_Adapter adapter;
    private Paint p = new Paint();
    List<String> dates = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_delayplan, container, false);
        initUI();
        return rootView;
    }

    // 레이아웃 변수
    private void initUI(){
        recyclerView = rootView.findViewById(R.id.delayDB);
        recyclerView.setHasFixedSize(true);
        //initSwipe();
        db = MyDatabase.getDatabase(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //adapter = new Add_Plan_MyListAdapter(db);
        adapter = new Delay_Adapter(getContext(), db);
        recyclerView.setAdapter(adapter);
        //UI 갱신
        db.myDao().getDate().observe(getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapter.setItem(strings);
            }
        });
    }
    // 스와이프 해서 삭제하기
//    private void initSwipe() {
//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                int position = viewHolder.getAdapterPosition();
//
//                if (direction == ItemTouchHelper.LEFT) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            db.myDao().delete(adapter.getItems().get(position));
//                        }
//                    }).start();
//
//                } else {
//                    //오른쪽으로 밀었을때.
//                }
//            }
//
//            @Override
//            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//
//                Bitmap icon;
//                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//
//                    View itemView = viewHolder.itemView;
//                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
//                    float width = height / 3;
//
//                    if (dX > 0) {
//                        //오른쪽으로 밀었을 때
//                    } else {
//                        p.setColor(Color.parseColor("#55D3D3D3"));
//                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
//                        c.drawRect(background, p);
//                        /*
//                         * icon 추가할 수 있음.
//                         */
//                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_trash_can); //vector 불가!
//                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
//                        c.drawBitmap(icon, null, icon_dest, p);
//                    }
//                }
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            }
//        };
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
//    }
}
