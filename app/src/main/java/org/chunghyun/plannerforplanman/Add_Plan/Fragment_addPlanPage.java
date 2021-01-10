 package org.chunghyun.plannerforplanman.Add_Plan;

 import android.app.AlertDialog;
 import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.graphics.Canvas;
 import android.graphics.Color;
 import android.graphics.Paint;
 import android.graphics.RectF;
 import android.os.Bundle;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.Spinner;
 import android.widget.Toast;

 import androidx.fragment.app.Fragment;
 import androidx.lifecycle.Observer;
 import androidx.recyclerview.widget.ItemTouchHelper;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import com.google.android.material.floatingactionbutton.FloatingActionButton;

 import org.chunghyun.plannerforplanman.R;
 import org.chunghyun.plannerforplanman.dataBase.Add_Plan_MyEntity;
 import org.chunghyun.plannerforplanman.dataBase.Home_Plan_Entity;
 import org.chunghyun.plannerforplanman.dataBase.MyDatabase;

 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import java.util.Locale;

 public class Fragment_addPlanPage extends Fragment implements View.OnClickListener {

    private Add_Plan_MyListAdapter adapter;
    private RecyclerView recyclerView;
    private MyDatabase db;
    private ViewGroup rootView;
    private Paint p = new Paint();
    private int tempPage = 10000;
    private int curPage = 10000;

    // 간편일정 관련
     private Easy_Plan_SpinnerAdapter spinnerAdapter;



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
        db = MyDatabase.getDatabase(getContext());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Add_Plan_MyListAdapter(db);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        // UI 갱신
        db.myDao().getAll(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())).observe(getActivity(), new Observer<List<Add_Plan_MyEntity>>() {
            @Override
            public void onChanged(List<Add_Plan_MyEntity> add_plan_myEntities) {
                adapter.setItem(add_plan_myEntities);
            }
        });
        // 간편 일정 추가
        Button easyPlan = (Button) rootView.findViewById(R.id.easy_add);
        easyPlan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Add_Plan_CustomDialog dialog = new Add_Plan_CustomDialog(rootView.getContext());
                dialog.setDialogListener(new Add_Plan_CustomDialog.CustomDialogListener() {
                    @Override
                    public void onPositiveClicked(String content) {
                        new Thread(() -> {
                            Add_Plan_MyEntity memo = new Add_Plan_MyEntity(content, 0, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()), 0);
                            db.myDao().insert(memo);
                        }).start();
                    }

                    @Override
                    public void onNegativeClicked() {
                    }
                });
                dialog.show();
                break;
            // 간편 일정
            case R.id.easy_add:
                List<String> bookList = new ArrayList<>();
                db.home_plan_dao().getAll().observe(getActivity(), new Observer<List<Home_Plan_Entity>>() {
                    @Override
                    public void onChanged(List<Home_Plan_Entity> home_plan_entities) {
                        for (int i = 0; i < home_plan_entities.size(); i++) {
                            bookList.add(home_plan_entities.get(i).getBookName());
                        }
                        spinnerAdapter.notifyDataSetChanged();
                    }
                });
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.easyaddplan_fab_dialog, null);
                Spinner spinner = (Spinner) mView.findViewById(R.id.spinner);
                Button ok = (Button) mView.findViewById(R.id.yes);
                Button no = (Button) mView.findViewById(R.id.no);
                EditText page = (EditText) mView.findViewById(R.id.unit);
                spinnerAdapter = new Easy_Plan_SpinnerAdapter(getActivity().getApplicationContext(), bookList);
                spinner.setAdapter(spinnerAdapter);
                ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.sub_planets_array, android.R.layout.simple_spinner_item);
                unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mBuilder.setView(mView);
                // 레이아웃 초기화
                AlertDialog alertDialog = mBuilder.create();

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(spinner.getSelectedItem() == null){
                            Toast.makeText(getContext(), "책 목록을 먼저 생성해주세요", Toast.LENGTH_SHORT).show();
                        }
                        else if (page.getText().toString().trim().getBytes().length <= 0) {
                            Toast.makeText(getContext(), "페이지 수를 입력해주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            new Thread(() -> {
                                tempPage = db.home_plan_dao().getPage(spinner.getSelectedItem().toString());
                                curPage = db.home_plan_dao().getCur(spinner.getSelectedItem().toString());
                            }).start();
                            int possiblePage = tempPage - curPage;
                            if (possiblePage < Integer.parseInt(page.getText().toString())) {
                                Toast.makeText(getContext(), "입력 가능한 페이지는 " + possiblePage + " 이하 입니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                String temp = spinner.getSelectedItem().toString() + " " + page.getText().toString() + " 페이지";
                                new Thread(() -> {
                                    Add_Plan_MyEntity memo2 = new Add_Plan_MyEntity(temp, 0, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()),
                                            Integer.parseInt(page.getText().toString()));
                                    db.myDao().insert(memo2);
                                }).start();
                                alertDialog.dismiss();
                            }
                        }
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
            }
//        }
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
                        p.setColor(Color.parseColor("#55D3D3D3"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        /*
                         * icon 추가할 수 있음.
                         */
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_trash_can); //vector 불가!
                         RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}