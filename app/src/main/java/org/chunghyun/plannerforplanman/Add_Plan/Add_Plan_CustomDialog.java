package org.chunghyun.plannerforplanman.Add_Plan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.chunghyun.plannerforplanman.R;

public class Add_Plan_CustomDialog extends Dialog implements View.OnClickListener{
    private EditText content;
    private Button ok;
    private Button cancel;
    CustomDialogListener customDialogListener;

    public Add_Plan_CustomDialog(Context context){ super(context); }

    interface CustomDialogListener{
        void onPositiveClicked(String content);
        void onNegativeClicked();
    }
    //호출할 리스너 초기화
    public void setDialogListener(Add_Plan_CustomDialog.CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addplan_fab_dialog);
        // 위젯 정의
        content = findViewById(R.id.content);
        ok = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);
        //버튼 리스너 정의
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
                customDialogListener.onPositiveClicked(content.getText().toString());
                dismiss();
                break;
            case R.id.cancel:
                cancel();
                break;
        }
    }
}
