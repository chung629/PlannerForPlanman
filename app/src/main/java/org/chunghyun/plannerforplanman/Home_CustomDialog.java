package org.chunghyun.plannerforplanman;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Home_CustomDialog extends Dialog implements View.OnClickListener{
    private EditText bookName;
    private EditText totalUnit;
    private EditText startyear;
    private EditText startmonth;
    private EditText startday;
    private EditText endyear;
    private EditText endmonth;
    private EditText endday;
    private Button positiveButton;
    private Button negativeButton;
    CustomDialogListener customDialogListener;

    public Home_CustomDialog(Context context){
        super(context);
    }

    interface CustomDialogListener{
        void onPositiveClicked(String bookName, int totalUnit, long dDay);
        void onNegativeClicked();
    }

    //호출할 리스너 초기화
    public void setDialogListener(CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fab_dialog);
        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        bookName = findViewById(R.id.projectname);
        totalUnit = findViewById(R.id.totalUnit);
        startyear = findViewById(R.id.startyear);
        startmonth = findViewById(R.id.startmonth);
        startday = findViewById(R.id.startday);
        endyear = findViewById(R.id.endyear);
        endmonth = findViewById(R.id.endmonth);
        endday = findViewById(R.id.endday);
        positiveButton = findViewById(R.id.ok);
        negativeButton = findViewById(R.id.cancel);
        // 버튼 클릭 리스너 등록
        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ok: // 확인 버튼
                //변수에 저장된 값 전달
                String startDate = startyear.getText().toString() + "-" + startmonth.getText().toString() + "-" + startday.getText().toString();
                String endDate = endyear.getText().toString() + "-" + endmonth.getText().toString() + "-" + endday.getText().toString();
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                    Date SecondDate = format.parse(endDate);
                    long curTime = format.parse(format.format(System.currentTimeMillis())).getTime();
                    long calDate = curTime - SecondDate.getTime();
                    long calDateDays = Math.abs(calDate / (24*60*60*1000)) + 1;
                    customDialogListener.onPositiveClicked(bookName.getText().toString(), Integer.parseInt(totalUnit.getText().toString()), calDateDays);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dismiss();
                break;
            case R.id.cancel:
                cancel();
                break;
        }
    }

}
