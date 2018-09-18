package com.technoface.app.talentscam.webrequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;

public class MyProgressDialog extends ProgressDialog {

	public static MyProgressDialog show(Context context, CharSequence title,
			CharSequence message) {
		return show(context, title, message, false);
	}

	static MyProgressDialog dialog = null;

	public static MyProgressDialog show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate) {
		return show(context, title, message, indeterminate, false, null);
	}

//	public static MyProgressDialog show(Context context, CharSequence title,
//			CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener) {
//		return show(context, title, message, indeterminate, cancelable, cancelListener);
//	}

	public static MyProgressDialog show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate, boolean cancelable,
			OnCancelListener cancelListener) {
		dialog = new MyProgressDialog(context);
		dialog.setTitle(title);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);
		/* The next line will add the ProgressBar to the dialog. */
		ProgressBar bar = new ProgressBar(context);
		/*bar.setIndeterminateDrawable(context.getResources().getDrawable(
				R.drawable.my_progress_indeterminate));*/
		/*dialog.addContentView(bar, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));*/
		dialog.show();

		return dialog;
	}
	
	public static MyProgressDialog show(Context context, CharSequence title,
			CharSequence message, boolean indeterminate, boolean cancelable,
			OnCancelListener cancelListener,Drawable draw) {
		dialog = new MyProgressDialog(context);
		dialog.setTitle(title);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);
		/* The next line will add the ProgressBar to the dialog. */
		ProgressBar bar = new ProgressBar(context);
		/*Drawable drawable = context.getResources().getDrawable(
				R.drawable.my_progress_indeterminate);*/

		dialog.addContentView(bar, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		dialog.show();

		return dialog;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (dialog.isShowing()) {
			try {
				dialog.dismiss();
			} catch (Exception ex) {

			}
		}
	}

	public MyProgressDialog(Context context) {
		super(context);
	}
}