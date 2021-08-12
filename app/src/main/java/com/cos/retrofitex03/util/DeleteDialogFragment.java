package com.cos.retrofitex03.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.cos.retrofitex03.controller.CMRespDTO;
import com.cos.retrofitex03.controller.PostController;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteDialogFragment extends DialogFragment {

    private static final String TAG = "DeleteDialogFragment";

    private PostController postController = new PostController();



    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // builder 패턴(객체생성). 이렇게 new 시켰다는 건 xml로 만들었단게 아님.
        // inflate 안했으니까 xml 아녀
        MaterialAlertDialogBuilder b = new MaterialAlertDialogBuilder(getActivity());
        b.setMessage("글을 삭제 하시겠습니까?")
                .setNegativeButton("아니오",
                (DialogInterface dialog, int id) -> {
                    Log.d(TAG, "onCreateDialog: setNegativeButton");
                }).setPositiveButton("네",
                (DialogInterface dialog, int id) -> {
                    Log.d(TAG, "onCreateDialog: setPositiveButton");

                });
        // Create the AlertDialog object and return it
        return b.create();
    }


}
