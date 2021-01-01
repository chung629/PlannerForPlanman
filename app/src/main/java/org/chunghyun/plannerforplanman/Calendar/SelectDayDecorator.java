package org.chunghyun.plannerforplanman.Calendar;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.chunghyun.plannerforplanman.R;

import java.util.Date;

public class SelectDayDecorator implements DayViewDecorator {
    private CalendarDay date;
    private Context context;

    public SelectDayDecorator(Context context, CalendarDay date){
        this.date = date;
        this.context = context;
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(ContextCompat.getDrawable(context, R.drawable.calendar_day_selector));

    }
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
