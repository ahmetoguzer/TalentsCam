package com.technoface.app.talentscam.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.technoface.app.talentscam.Activities.MainMenuActivity;
import com.technoface.app.talentscam.Adapters.PrizesAdapter;
import com.technoface.app.talentscam.BuildConfig;
import com.technoface.app.talentscam.Constants.MySharedpreferences;
import com.technoface.app.talentscam.Controller.AppController;
import com.technoface.app.talentscam.Model.PrizesModel;
import com.technoface.app.talentscam.R;
import com.technoface.app.talentscam.Utils.Common;
import com.technoface.app.talentscam.Utils.ViewOnTouch;
import com.technoface.app.talentscam.Vo.PrizesVo;
import com.technoface.app.talentscam.helper.ServiceURLCreator;
import com.technoface.app.talentscam.webrequests.CustomTask;
import com.technoface.app.talentscam.webrequests.CustomTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.technoface.app.talentscam.Activities.MainMenuActivity.bottomNavigationView;

/**
 * Created by Ahmet Oguzer on 11.01.2018.
 */

public class PrizesDetayFragment extends BaseFragment {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";

    private ArrayList<PrizesVo> myList;
    private ImageView imgThumbnail;
    private TextView tvDetay,tvTitle;
    private EditText etDetay;
    private Button btnApply;
    private ImageButton btnBack;

    private String image,detail,title,PrizeId;

    public static PrizesDetayFragment newInstance() {
        PrizesDetayFragment fragment = new PrizesDetayFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_prizes_detay, container, false);
        btnApply = rootView.findViewById(R.id.btnApply);
        etDetay = rootView.findViewById(R.id.etDetay);
        tvDetay = rootView.findViewById(R.id.tvDetay);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        imgThumbnail = rootView.findViewById(R.id.imgThumbnail);
        btnBack = rootView.findViewById(R.id.back_btn);

        myList = new ArrayList<>();

        image = getArguments().getString("image");
        detail = getArguments().getString("detail");
        title = getArguments().getString("title");
        PrizeId = getArguments().getString("PrizeId");

        tvDetay.setText(detail);
        tvTitle.setText(title);

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(3*1024*2014)
                .discCacheSize(3*1024*1024)
                .build();

        ImageLoader.getInstance().init(config);
        final ImageLoader imageLoader=ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisc()
                .cacheInMemory()
                .build();

        imageLoader.displayImage(image, imgThumbnail, options);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainMenuActivity.screenBackCall();
            }
        });

        ViewOnTouch.onTouchEffect(btnApply);
        ViewOnTouch.onTouchEffect(btnBack);
        AppController.getInstance().context=getActivity();
        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        MainMenuActivity.rv.setVisibility(View.GONE);
        MySharedpreferences mySharedPreferences =  MySharedpreferences.getInstance(getActivity());

        super.onActivityCreated(savedInstanceState);
    }

    private void attemptLogin(){
        CustomTask customLoginController = new CustomTask(getActivity(), response);
        customLoginController.execute(ServiceURLCreator.UsePrize(Common.getUniqueID(getActivity()),
                AppController.getInstance().userId,PrizeId,etDetay.getText().toString()));
    }

    CustomTaskFinishedListener response = new CustomTaskFinishedListener() {
        @Override
        public void taskFinished(Message msg) {
            try {
                if( msg.obj.toString().length()>0) {
                    JSONObject obj = new JSONObject(msg.obj.toString());
                    if(!obj.optString("ResultMessage").equals("Thanks")){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("BasketCoin");
                        builder.setMessage(obj.optString("ResultMessage"));
                        builder.setPositiveButton("OKEY", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                bottomNavigationView.getMenu().findItem(R.id.action_item0).setIcon(R.drawable.coinikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item1).setIcon(R.drawable.videoikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item2).setIcon(R.drawable.legendsicontrue);
                                bottomNavigationView.getMenu().findItem(R.id.action_item3).setIcon(R.drawable.newsikonfalse);
                                bottomNavigationView.getMenu().findItem(R.id.action_item4).setIcon(R.drawable.ayarlarikonfalse);
                                MainMenuActivity.selectedFragment = RecordChooseFragment.newInstance();
                                Bundle bundle = new Bundle();
                                bundle.putString("state", "0");
                                Fragment fragment = RecordChooseFragment.newInstance();
                                fragment.setArguments(bundle);
                                FragmentTransaction transaction1 = getActivity()
                                        .getSupportFragmentManager().beginTransaction();
                                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                transaction1.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                                transaction1.replace(R.id.frame_layout, fragment,RecordChooseFragment.FRAGMENT_TAG);
                                transaction1.disallowAddToBackStack();
                                transaction1.commit();
                                MainMenuActivity.mStacks.get("Fragments").push(fragment);
                                MainMenuActivity.setTitle();
                                bottomNavigationView.setSelectedItemId(R.id.action_item2);

                            }
                        });
                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainMenuActivity.screenBackCall();
                            }
                        });
                        builder.show();
                    }else{
                        Common.alert(getActivity(), "BasketCoin",obj.optString("ResultMessage"));
                        MainMenuActivity.screenBackCall();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
               Common.alert(getActivity(), "Error","Try again later" );
            }
        }
    };


}
