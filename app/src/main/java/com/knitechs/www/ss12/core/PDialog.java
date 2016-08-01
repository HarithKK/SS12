package com.knitechs.www.ss12.core;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Koli on
 */
public class PDialog extends ProgressDialog {

    public PDialog(Context context) {
        super(context);
    }

    public PDialog(Context context, String message){
        super(context);
        super.setMessage(message);
        super.setIndeterminate(false);
    }
}
