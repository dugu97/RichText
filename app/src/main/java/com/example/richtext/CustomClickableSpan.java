package com.example.richtext;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class CustomClickableSpan extends ClickableSpan {

    private Context context;
    private String text;

    public CustomClickableSpan(Context context, String text) {
        this.context = context;
        this.text = text;
    }

    @Override
    public void onClick(@NonNull View widget) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
