package com.cos.retrofitex03.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class JoinDialogFragment extends DialogFragment {

    private static final String TAG = "MyDialogFragment";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // builder 패턴(객체생성). 이렇게 new 시켰다는 건 xml로 만들었단게 아님.
        // inflate 안했으니까 xml 아녀
        MaterialAlertDialogBuilder b = new MaterialAlertDialogBuilder(getActivity());
        b.setMessage("회원가입 하시겠습니까?")
                .setNegativeButton("아니오",
                (DialogInterface dialog, int id) -> {
                    Log.d(TAG, "onCreateDialog: setNegativeButton");
                }).setPositiveButton("네",
                (DialogInterface dialog, int id) -> {
                    Log.d(TAG, "onCreateDialog: setPositiveButton");
                    Context context = getActivity();
                    CharSequence text = "가입 되었습니다!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                });
        // Create the AlertDialog object and return it
        return b.create();
    }


}
