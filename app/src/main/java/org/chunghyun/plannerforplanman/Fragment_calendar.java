package org.chunghyun.plannerforplanman;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class Fragment_calendar extends Fragment {

    //캘린더 관련
    MaterialCalendarView materialCalendarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_calendar, container, false);
        initUI(rootView);
        // 달력의 시작과 끝 지정
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(1990, 0, 1))
                .setMaximumDate(CalendarDay.from(2050, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        // 달력 효과 지정
        materialCalendarView.addDecorators(
                new Calendar_SundayDecorator(),
                new Calendar_SaturdayDecorator(),
                new Calendar_OneDayDecorator());
        return rootView;
    }
    private void initUI(ViewGroup rootView){
        materialCalendarView = rootView.findViewById(R.id.calendarView);
    }
}
