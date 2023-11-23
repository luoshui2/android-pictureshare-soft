package com.example.pictureshare.toast;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;
//子线程进行消息提醒的Toast
public class ToastHelp {
    static Toast toast = null;

    public static void show(Context context, String text) {
        try {
            if (toast != null) {
                toast.setText(text);
            } else {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            }
            toast.show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }
}
