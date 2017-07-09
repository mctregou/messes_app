package com.tregouet.messesapp.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tregouet.messesapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by GEOKS on 28/09/2016.
 */

public class GlobalAlertDialog extends Dialog {

    @BindView(R.id.title)
    protected TextView title;
    @BindView(R.id.description)
    protected TextView description;
    @BindView(R.id.two_buttons_layout)
    protected LinearLayout twoButtonsLayout;
    @BindView(R.id.negative_button)
    protected TextView negativeButton;
    @BindView(R.id.positive_button)
    protected TextView positiveButton;
    @BindView(R.id.single_button_positive_btn)
    protected TextView singlePositiveButton;

    private boolean isSingleButtonMode = true;
    private boolean isCancelable = true;

    public GlobalAlertDialog(Context context) {
        super(context);
        setup();
    }

    public GlobalAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        setup();
    }

    protected GlobalAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setup();
    }

    private void setup() {
        if(getWindow() == null)
            return;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.global_alert_dialog);
        ButterKnife.bind(this);
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        isCancelable = cancelable;
    }

    public GlobalAlertDialog setMessage(int messageRes) {
        description.setText(messageRes);
        return this;
    }

    public GlobalAlertDialog setMessage(String messageRes) {
        description.setText(messageRes);
        return this;
    }

    public GlobalAlertDialog setTitle(String message) {
        title.setText(message);
        return this;
    }

    public GlobalAlertDialog setMessage(String message, int gravity) {
        description.setText(message);
        description.setGravity(gravity);
        return this;
    }

    public void setTextSize(int size){
        description.setTextSize(COMPLEX_UNIT_SP, size);
    }

    public GlobalAlertDialog setNegativeButton(int labelRes, View.OnClickListener listener) {
        negativeButton.setText(labelRes);

        if(listener == null) {
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isCancelable)
                        dismiss();
                }
            };
        }
        negativeButton.setOnClickListener(listener);

        isSingleButtonMode = false;

        return this;
    }

    public GlobalAlertDialog setPositiveButton(int labelRes, View.OnClickListener listener) {
        positiveButton.setText(labelRes);
        positiveButton.setOnClickListener(listener);
        singlePositiveButton.setText(labelRes);
        singlePositiveButton.setOnClickListener(listener);
        return this;
    }

    @Override
    public void show() {
        if (title.getText().toString().equals("")) {
            title.setVisibility(View.GONE);
        } else {
            title.setVisibility(View.VISIBLE);
        }

        if(isSingleButtonMode) {
            singlePositiveButton.setVisibility(View.VISIBLE);
            twoButtonsLayout.setVisibility(View.GONE);

            if(!singlePositiveButton.hasOnClickListeners()) {
                singlePositiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isCancelable)
                            dismiss();
                    }
                });
            }
        } else {
            singlePositiveButton.setVisibility(View.GONE);
            twoButtonsLayout.setVisibility(View.VISIBLE);
        }
        super.show();
    }
}
