package org.chunghyun.plannerforplanman.Calendar;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.chunghyun.plannerforplanman.Add_Plan.Add_Plan_MyDatabase;
import org.chunghyun.plannerforplanman.Add_Plan.Add_Plan_MyEntity;
import org.chunghyun.plannerforplanman.Add_Plan.Add_Plan_MyListAdapter;
import org.chunghyun.plannerforplanman.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Fragment_calendar extends Fragment {

    MaterialCalendarView materialCalendarView;
    ViewGroup rootView;
    Add_Plan_MyListAdapter adapter;
    private Add_Plan_MyDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);
        initUI();
        return rootView;
    }

    // 레이아웃 변수
    private void initUI(){

        ScrollView scrollView = (ScrollView)rootView.findViewById(R.id.scrollMemo);
        materialCalendarView = rootView.findViewById(R.id.calendarView);
        // 달력의 시작과 끝 지정
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(1950, 0, 1))
                .setMaximumDate(CalendarDay.from(2050, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        // 달력 효과
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OneDayDecorator(),
                new WeekDayDecorator());
        // 사이즈 조절
        LinearLayout linearLayout = (LinearLayout)rootView.findViewById(R.id.calendarLayout);
        // 달력 및 메모
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.calendarDb);
        db = Add_Plan_MyDatabase.getDatabase(getContext());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Add_Plan_MyListAdapter(db);
        recyclerView.setAdapter(adapter);

        // 클릭 이벤트
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();
                if(selected){
                    materialCalendarView.addDecorators(new SelectDayDecorator(rootView.getContext(), date));
                    db.myDao().getAll(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date.getDate())).observe(getActivity(), new Observer<List<Add_Plan_MyEntity>>() {
                        @Override
                        public void onChanged(List<Add_Plan_MyEntity> add_plan_myEntities) {
                            adapter.setItem(add_plan_myEntities);
                        }
                    });
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                    params.height = 1000;
                    linearLayout.setLayoutParams(params);
                    scrollView.setVisibility(View.VISIBLE);
                }
                else{
                    materialCalendarView.clearSelection();
                }
            }
        });
        }
    // 특정 날짜에 효과 주기
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(String[] Time_Result){
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < Time_Result.length ; i ++){
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                dates.add(day);
                calendar.set(year,month-1,dayy);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isCancelled()) {
                return;
            }
            materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays, getActivity()));
        }
    }
}
