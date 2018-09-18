package com.technoface.app.talentscam.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;


import com.technoface.app.talentscam.R;


/**
 * Created by Kamlesh Mourya on 7/10/2015.
 */
public class Common {
    private static Toast toast;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static void showToast(Context context, String text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showErrorDialog(Context context, String msg) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getResources().getString(R.string.app_name));
            builder.setMessage(msg);
            builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } catch (Throwable t) {

        }
    }

    public static String getUniqueID(Context context) {
         String android_id = Settings.Secure.getString(context.getContentResolver(),
                 Settings.Secure.ANDROID_ID);
       return android_id;

    }



    public static void alert(Context context,String title, String msg) {
        AlertDialog alt = null;

        alt = new AlertDialog.Builder(context).create();

        alt.setTitle(title);
        alt.setMessage(msg);
        alt.setButton(AlertDialog.BUTTON_NEUTRAL, "Tamam",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });

        alt.show();
    }

    public static void alertWithFinish(Context context,String title, String msg) {
        AlertDialog alt = null;

        alt = new AlertDialog.Builder(context).create();

        alt.setTitle(title);
        alt.setMessage(msg);
        alt.setButton(AlertDialog.BUTTON_NEUTRAL, "Tamam",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();

                    }
                });

        alt.show();
    }

//    public static void customAlert (Context context, String title, String msg, String btnText) {
//        final Dialog dialog = new Dialog(context, R.style.MyDialog);
//        dialog.setContentView(R.layout.custom_alert_dialog);
//
//        // set the custom dialog components - text, image and button
//        TextView text = (TextView) dialog.findViewById(R.id.txtAlertText);
//        text.setText(msg);
//
//        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButton1);
//        dialogButton.setText(btnText);
//        // if button is clicked, close the custom dialog
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        dialog.show();
//    }

//    public static void customAlertCallCenter (final Context context, String title, String msg, String btn1Text, String btn2Text) {
//        final Dialog dialog = new Dialog(context);
//        dialog.setContentView(R.layout.custom_alert_dialog);
//
//        // set the custom dialog components - text, image and button
//        TextView text = (TextView) dialog.findViewById(R.id.txtAlertText);
//        text.setText(msg);
//
//        Button dialogButton1 = (Button) dialog.findViewById(R.id.dialogButton1);
//        dialogButton1.setText(btn1Text);
//        // if button is clicked, close the custom dialog
//        dialogButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//                int permissionCheck = ContextCompat.checkSelfPermission(context,
//                        Manifest.permission.CALL_PHONE);
//                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//                    Intent intent = new Intent(Intent.ACTION_CALL);
//                    intent.setData(Uri.parse("tel:" + "4444372"));
//                    context.startActivity(intent);
//                } else {
//
//                }
//            }
//        });
//
//        Button dialogButton2 = (Button) dialog.findViewById(R.id.dialogButton2);
//        dialogButton2.setVisibility(View.VISIBLE);
//        dialogButton2.setText(btn2Text);
//        // if button is clicked, close the custom dialog
//        dialogButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        dialog.show();
//    }



//    public static String getSH1(){
//        try {
//            return MD5.SHA1("technoface2012");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }


    public static String convertUTF8(String s) {
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int ch = s.charAt(i);
            if (ch == ' ') {
                sbuf.append("%20");
            } else {
                sbuf.append((char) ch);
            }
        }
        return sbuf.toString();
    }

}
