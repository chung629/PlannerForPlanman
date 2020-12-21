package org.chunghyun.plannerforplanman;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Home_CustomDialog {
    private Context context;

    public Home_CustomDialog(Context context){
        this.context = context;
    }

    public void callFunction(){
        final Dialog dig = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dig.setContentView(R.layout.home_fab_dialog);

        dig.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText projectName = dig.findViewById(R.id.projectname);
        final EditText totalUnit = dig.findViewById(R.id.totalUnit);
        final TextView startdate = dig.findViewById(R.id.startDate);
        final TextView enddate = dig.findViewById(R.id.enddate);
        final Button okButton = dig.findViewById(R.id.ok);
        final Button cancelButton = dig.findViewById(R.id.cancel);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "저장되었습니다", Toast.LENGTH_SHORT).show();
                dig.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dig.dismiss();
            }
        });
    }
}
