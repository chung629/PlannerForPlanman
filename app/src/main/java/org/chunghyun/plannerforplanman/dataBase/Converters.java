package org.chunghyun.plannerforplanman.dataBase;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converters {

    @TypeConverter
    public static String DateTimestamp(Date value) {
        return value == null ? null : new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(value);
    }
}
