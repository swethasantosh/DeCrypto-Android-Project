package com.currency.decrypto;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View.OnClickListener;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.AlertDialog.Builder;

/**
 * Created by swetha on 1/25/18.
 */

public class CustomAlertDialog extends DialogFragment
{
    public static String contentText = "null";
    TextView contentTextView;
    Button gotItButton;

    class C08451 implements OnClickListener
    {
        C08451()
        {
        }

        public void onClick(View view)
        {
            CustomAlertDialog.this.dismiss();
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.alertdialog, null);
        builder.setView(dialogView);
        init(dialogView);
        this.gotItButton.setOnClickListener(new C08451());
        return builder.create();
    }

    private void init(View dialogView) {
        this.gotItButton = (Button) dialogView.findViewById(R.id.alertDialog_gotItBtn);
        this.contentTextView = (TextView) dialogView.findViewById(R.id.alertDialog_contentTv);
        this.contentTextView.setText(contentText);
        this.gotItButton.setText("GOT IT");
    }
}


