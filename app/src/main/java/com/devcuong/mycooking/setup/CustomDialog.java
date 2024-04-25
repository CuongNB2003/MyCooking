package com.devcuong.mycooking.setup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class CustomDialog {
    private final Context context;
    private final String title;
    private final String message;
    private final DialogInterface.OnClickListener positiveButtonListener;

    public CustomDialog(Context context, String title, String message, DialogInterface.OnClickListener positiveButtonListener) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.positiveButtonListener = positiveButtonListener;
    }

    public void show() {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, positiveButtonListener)
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
}

