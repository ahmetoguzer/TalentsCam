package com.technoface.app.talentscam.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridView;

import com.crashlytics.android.Crashlytics;
import com.technoface.app.talentscam.Adapters.GrupListAdapter;
import com.technoface.app.talentscam.Constants.MySharedpreferences;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Model.GrupModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Vo.GrupVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Ahmet on 5.9.2017.
 */

public class GrupActivity extends AppCompatActivity {
    private static String TAG = "Grup Activity";

    private GridView gv;
    private EditText inputSearch;

    private ArrayList<HashMap<String, String>> productList;
    private MySharedpreferences mySharedPreferences;
    private ArrayList<GrupVo> myList;

    public GrupListAdapter adapter;
    private ProgressDialog progres = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Fabric.with(this, new Crashlytics());

        gv = (GridView) findViewById(R.id.gridView2);
        inputSearch = (EditText) findViewById(R.id.search);

        mySharedPreferences= MySharedpreferences.getInstance(GrupActivity.this);

        myList = new ArrayList<>();

        LoadingData loadingData= new LoadingData(this);
        AppController.getInstance().isAdmin = true;
        loadingData.execute();

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void attemptLogin(){
        String userid=mySharedPreferences.getData("userid");
        CustomTask customGetListdata = new CustomTask(getApplicationContext(), response);
        customGetListdata.execute(ServiceURLCreator.GetGroups(Common.getUniqueID(GrupActivity.this),
                userid));
    }

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());

                    if (obj.optString("HasError").equals("false")) {
                        JSONArray arry = obj.getJSONArray("ResultObjects");
                        GrupModel model = new GrupModel();
                        myList = model.getGrup(arry);

                        adapter=new GrupListAdapter(getApplicationContext(),myList);
                        gv.setAdapter(adapter);
                    } else {
                        Log.e(TAG, obj.optString("ResultMessage"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//               Common.alert(getActivity(), "", "");
            }

        }
    };


    private class LoadingData extends AsyncTask<Object, Object, String> {

        private Context ctx;

        public LoadingData(Context ctx) {
            this.ctx=ctx;
        }

        @Override
        protected void onPreExecute() {
            progres = new ProgressDialog(ctx);
            progres.setMessage(getString(R.string.loading_str));
            progres.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Object... voids) {
            attemptLogin();
            return "Executed";
        }

        @Override
        protected void onPostExecute(String compressed) {
            super.onPostExecute(compressed);
//            progressBar.setVisibility(View.GONE);
            progres.dismiss();

        }
    }

}
