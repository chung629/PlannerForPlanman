package org.chunghyun.plannerforplanman.Add_Plan;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converters {

    @TypeConverter
    public static String DateTimestamp(Date value) {
        return value == null ? null : new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(value);
    }
//    @TypeConverter
//    public static Long dateToTimestamp(Date date) {
//        return date == null ? null : date.getTime();
//    }
}
