package com.technoface.app.talentscam.webrequests;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;


import com.technoface.app.talentscam.helper.ApiAddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

public class CustomTaskWithoutDialog extends AsyncTask<Object, String, String> implements
		DialogInterface.OnCancelListener {

	private CustomTaskFinishedListener callback;
	private Context cnx = null;
//	public ProgressDialog progres = null;
	public Message msg = null;
	private boolean showProgressBar = true;
	private boolean cacheWithGSON = false;
	private int cacheCode = -1;

	public CustomTaskWithoutDialog(Context context, CustomTaskFinishedListener clb) {
		this.cnx = context;
		this.callback = clb;
		// TODO yeni constructor ve boolean saveSession ekle
	}

	public CustomTaskWithoutDialog(Context context, CustomTaskFinishedListener clb,
								   boolean cacheWithGSON, int cacheCode) {
		this.cnx = context;
		this.callback = clb;
		this.cacheWithGSON = cacheWithGSON;
		this.cacheCode = cacheCode;
	}

	public CustomTaskWithoutDialog(Context context, CustomTaskFinishedListener clb,
								   boolean showProgressBar, boolean cacheWithGSON) {
		this.cnx = context;
		this.callback = clb;
		this.showProgressBar = showProgressBar;

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		try {
			if (showProgressBar) {
//				progres = new ProgressDialog(cnx);
//				progres.setMessage(cnx.getString(R.string.loading_str));
//				progres.show();
				//progres = MyProgressDialog.show(cnx, "", cnx.getString(R.string.loading_str));
				setCommonProcess();
			}
		} catch (Exception ex) {
		}
	}


	@Override
	protected String doInBackground(Object... params) {

		CryptoManager cryptoManager = new CryptoManager();
		
		msg = new Message();
		msg.what = FCodes.STATUS_OK;
		try {
			msg.what = FCodes.STATUS_OK;
			if (params.length == 2) {
				// Post i≈ülemidir

				/*if (params[0].toString().contains( ApiAddress.urlAlisverisListeKaydet) ) {
					msg = callPostServiceAlisveris(params[0].toString(), params[1].toString()); 
				} else if (params[0].toString().contains( ApiAddress.urlAlisverislistesiguncelle) ) {
					msg = callPostServiceAlisveris(params[0].toString(), params[1].toString()); 
				}else {
					msg = callPostService(params[0].toString(), params[1].toString());

				}*/

			} else {
				// get i≈ülemidir
				if (params[0].toString().contains("tuzvebiber") || 
						params[0].toString().contains("HaftasonuIndirimi") ||
						params[0].toString().contains("gordugunuzeInanin")) { 
					msg = callGETService(params[0].toString());

				} else {
					String newPost;
					if (!params[0].toString().contains("logme")) {
						newPost = params[0].toString().split("\\?")[1]+ "&datetoken=" + cryptoManager.getDateToken();

					} else {
						newPost = params[0].toString().split("\\?")[1] + "&datetoken=" + cryptoManager.getDateToken()
						+ "&dmachinename=" + android.os.Build.MODEL + "&lang=" + Locale.getDefault().getLanguage()
						+ "&country=" + cnx.getResources().getConfiguration().locale.getCountry();
					}
					Log.d("CustomTask","Params :"+ApiAddress.hostDuello+newPost);
					msg = callPostService(ApiAddress.hostDuello, cryptoManager.doCipher(newPost, cnx));

				}
			}

		} catch (Exception ex) {
			msg.what = FCodes.STATUS_ERROR;
			msg.obj = ex.toString();
		}

		return "";
	}

	private Message callPostService(String urlAddress, String post) {
		Message msg = new Message();
		Log.d("CustomTask","Params :"+urlAddress+post);


		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(convertUTF8(urlAddress));
		httppost.setHeader("User-Agent",
				"Migros/1.2 CFNetwork/672.1.13 Darwin/14.0.0");

		try {
			
			StringEntity se = new StringEntity(post, HTTP.UTF_8);

			se.setContentType("application/json");
			httppost.setEntity(se);
			HttpResponse httpresponse = httpclient.execute(httppost);
			if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity resEntity = httpresponse.getEntity();
				
				String strResponse = EntityUtils.toString(resEntity);

				msg.what = FCodes.STATUS_OK;
				msg.obj = strResponse;
				Log.d("CustomTask","URL :"+strResponse);

			} else {
				msg = setMessage(FCodes.STATUS_ERROR,
						"Servis URL Cevap Vermedi. Error Code: "
								+ httpresponse.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException ex) {
			msg = setMessage(FCodes.STATUS_IOEXCEPTION, ex.toString());
		} catch (IOException ex) {
			msg = setMessage(FCodes.STATUS_IOEXCEPTION, ex.toString());
		}
		return msg;
	}

	private Message callGETService(String url) {

		Message msg = new Message();
		BufferedReader in = null;
		try {
			Log.v("new Response","hIT urL get");

			DefaultHttpClient client = new DefaultHttpClient();

			HttpGet request = new HttpGet();
			request.setHeader("User-Agent",
					"Migros/1.2 CFNetwork/672.1.13 Darwin/14.0.0");



			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			HttpEntity resEntity = response.getEntity();

			String json = EntityUtils.toString(resEntity);


			msg.what = FCodes.STATUS_OK;
			msg.obj = json.toString();

		} catch (Exception ex) {
			msg.what = FCodes.STATUS_ERROR;
			msg.obj = ex.toString();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return msg;
	}

	private Message setMessage(int what, Object obj) {
		Message msg = new Message();
		msg.what = what;
		msg.obj = obj;
		return msg;
	}

	private String convertUTF8(String s) {
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

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		callback.taskFinished(msg);
		try {
//			progres.dismiss();
		} catch (Exception ex) {

		}
	}

	private void setCommonProcess() {
		// progres.setCanceledOnTouchOutside(true);
//		progres.setOnCancelListener(this);
//		progres.setCancelable(false);
	}

	@Override
	public void onCancel(DialogInterface arg0) {
		this.cancel(true);
		if (msg == null) {
			msg = new Message();
		}
		msg.what = FCodes.STATUS_CANCELLED;
		callback.taskFinished(msg);
	}

	private Message callPostServiceAlisveris(String urlAddress, String post) {
		Message msg = new Message();
		Log.v("new Response","hIT urL poST");
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(convertUTF8(urlAddress));
		httppost.setHeader("User-Agent",
				"Migros/1.2 CFNetwork/672.1.13 Darwin/14.0.0");
		httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		
	

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("urunler", post));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse httpresponse = httpclient.execute(httppost);
			if (httpresponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity resEntity = httpresponse.getEntity();

				String strResponse = EntityUtils.toString(resEntity);
				Log.v("new Response",strResponse);
				msg.what = FCodes.STATUS_OK;
				msg.obj = strResponse;

			} else {
				Log.v("new Response","NO");
				msg = setMessage(FCodes.STATUS_ERROR,
						"Servis URL Cevap Vermedi. Error Code: "
								+ httpresponse.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException ex) {
			// TODO Auto-generated catch block
			msg = setMessage(FCodes.STATUS_IOEXCEPTION, ex.toString());
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			msg = setMessage(FCodes.STATUS_IOEXCEPTION, ex.toString());
		}

		// httppost.setEntity(se);
		// HttpResponse httpresponse = httpclient.execute(httppost);

		return msg;
	}
}
