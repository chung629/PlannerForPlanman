package org.chunghyun.plannerforplanman.Home_Plan;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.chunghyun.plannerforplanman.R;

public class Home_CustomDialog extends Dialog implements View.OnClickListener{
    private EditText bookName;
    private EditText totalUnit;
    private EditText curUnit;
    private Button positiveButton;
    private Button negativeButton;
    CustomDialogListener customDialogListener;
    private String tempName = "";
    private String tempTotal = "";
    private String tempCur = "";

    public Home_CustomDialog(Context context){
        super(context);
    }

    interface CustomDialogListener{
        void onPositiveClicked(String bookName, int totalUnit, int curUnit);
        void onNegativeClicked();
    }

    //호출할 리스너 초기화
    public void setDialogListener(CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    public void setText(String bookName, String totalUnit, String curUnit){
        tempName = bookName;
        tempTotal = totalUnit;
        tempCur = curUnit;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fab_dialog);
        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        bookName = findViewById(R.id.projectName);
        totalUnit = findViewById(R.id.totalUnit);
        curUnit = findViewById(R.id.curUnit);
        positiveButton = findViewById(R.id.ok);
        negativeButton = findViewById(R.id.cancel);
        // 버튼 클릭 리스너 등록
        positiveButton.setOnClickListener(this); 
        negativeButton.setOnClickListener(this);
        // 텍스트 세팅하기
        if(tempName != ""){
            bookName.setText(tempName);
            totalUnit.setText(tempTotal);
            curUnit.setText(tempCur);
        }
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ok: // 확인 버튼
                String temp1 = bookName.getText().toString().trim();
                String temp2 = totalUnit.getText().toString().trim();
                String temp3 = curUnit.getText().toString().trim();

                if(temp1.getBytes().length <= 0){
                    Toast.makeText(getContext(), "책 이름을 입력 해주세요", Toast.LENGTH_SHORT).show();
                }else if(temp2.getBytes().length <= 0){
                    Toast.makeText(getContext(), "총 페이지 수를 입력 해주세요", Toast.LENGTH_SHORT).show();
                }else if(temp3.getBytes().length <= 0){
                    Toast.makeText(getContext(), "현재 페이지를 입력 해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    int num1 = Integer.parseInt(temp2);
                    int num2 = Integer.parseInt(temp3);
                    if(num1 < num2){
                        Toast.makeText(getContext(), "현재 페이지가 총 페이지 보다 큽니다", Toast.LENGTH_SHORT).show();
                    }else{
                        //변수에 저장된 값 전달
                        customDialogListener.onPositiveClicked(bookName.getText().toString(), Integer.parseInt(totalUnit.getText().toString()), Integer.parseInt(curUnit.getText().toString()));
                        dismiss();
                    }
                }
                break;
            case R.id.cancel:
                cancel();
                break;
        }
    }

}
