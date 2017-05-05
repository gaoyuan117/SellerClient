package com.kaichaohulian.baocms.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;

/**
 * Created by gaoyuan on 2017/4/30.
 */

public class PayDialog {
    Dialog dialog;
    ImageView closeImg;
    Button sureBt;
    TextView tv_message;
    public PayDialog(final Context context) {
        dialog = new Dialog(context, R.style.dialog_type);
        View view = View.inflate(context,R.layout.dialog_add_friend,null);
        closeImg = (ImageView) view.findViewById(R.id.img_close_dialog);
        sureBt = (Button) view.findViewById(R.id.bt_dialog_sure);
        tv_message = (TextView) view.findViewById(R.id.tv_dialog_add_friend_message);
        dialog.setContentView(view);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public PayDialog showDialog(){
        dialog.show();
        return this;
    }

    public PayDialog setSureClick(View.OnClickListener listener){
        sureBt.setOnClickListener(listener);
        return this;
    }

    public PayDialog setMessage(String message){
        tv_message.setText(message);
        return this;
    }

}
