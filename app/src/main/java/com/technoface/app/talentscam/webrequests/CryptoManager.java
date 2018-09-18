package com.technoface.app.talentscam.webrequests;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoManager {

	String encrypedPwd, encrypedPwdUtf8;

	/** Yeni Şifre **/
//	private char[] keyChar = new char[]{'2', '0', '1', '4', 't', 'e', 'c', 'h', 'n', 'o', 'f', 'a', 'c', 'e', 'm', 'i', 'g', 'r', 'o', 's', '7', '1', '9', '0'};
//	private char[] initializationVectorChar = new char[]{'m', 'i', 'g', 'r', '2', '0', '1', '9'};

	/** Eski Şifre **/
	private char[] keyChar = new char[]{'t', 'e', 'c', 'h', 'n', 'o', 'f', 'a', 'c', 'e', '2', '0', '1', '4', 'm', 'i', 'g', 'r', 'o', 's', '7', '8', '9', '0'};
	private char[] initializationVectorChar = new char[]{'m', 'i', 'g', 'r', '2', '0', '1', '4'};

	public String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}

	private String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

	// +(NSString*) doCipher:(NSString*)plainText
	// operation:(CCOperation)encryptOrDecrypt
	public String doCipher(String plainText, Context ctx) {
		DESKeySpec keySpec;
		try {
			keySpec = new DESKeySpec("tech2014".getBytes("UTF8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);
			// sun.misc.BASE64Encoder base64encoder = new BASE64Encoder();
			// sun.misc.BASE64Decoder base64decoder = new BASE64Decoder();

			// ENCODE plainTextPassword String
			byte[] cleartext = plainText.getBytes("UTF8");

			Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread
														// safe
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encrypedPwd = Base64.encodeToString(cipher.doFinal(cleartext),
					Base64.DEFAULT);

			encrypedPwdUtf8 = URLEncoder.encode(encrypedPwd, "utf-8");
			// now you can store it

			// DECODE encryptedPwd String
			byte[] encrypedPwdBytes = Base64
					.decode(encrypedPwd, Base64.DEFAULT);

			Cipher cipher2 = Cipher.getInstance("DES");// cipher is not thread
														// safe
			cipher2.init(Cipher.DECRYPT_MODE, key);
			byte[] plainTextPwdBytes = (cipher2.doFinal(encrypedPwdBytes));

			String decryptedPwd = new String(plainTextPwdBytes, "UTF8");

			System.out.println("decryptedPwd is .......>>>>>" + decryptedPwd);

		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/********************************************/
		String key = "technoface20131234567890";
		String initializationVector = "tech2013";


		/** Service new Key **/
//		String encKey = null;
//		String ivKey = null;
//		ReadWriteEncodedFile readWriteEncodedFile = new ReadWriteEncodedFile();
//		if (!readWriteEncodedFile.readFromFile(ctx).equals("")) {
//			String encodedBase64 = readWriteEncodedFile.readFromFile(ctx);
//
//			Base64EncodeDecode base64EncodeDecode = new Base64EncodeDecode();
//			String decryptedJpg = base64EncodeDecode.decodeString(encodedBase64);
//
//			CryptoManager cryptoManager = new CryptoManager();
//			String keyObj = cryptoManager.decryptIt(decryptedJpg, ctx);
//
//			JSONObject json = null;
//			try {
//				json = new JSONObject(keyObj);
//				encKey = json.optString("EncKey");
//				ivKey = json.optString("IvKey");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			key = encKey;
//			initializationVector = ivKey;
//		}
		/** Service new Key **/


		String password = null;
		SecretKeySpec myKey = null;

		byte[] plaintext = plainText.getBytes();
		byte[] tdesKeyData = key.getBytes();

		byte[] myIV = initializationVector.getBytes();

		Cipher c3des;
		try {
			c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			myKey = new SecretKeySpec(tdesKeyData, "DESede");
			IvParameterSpec ivspec = null;
			try {
				ivspec = new IvParameterSpec(myIV);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			c3des.init(Cipher.ENCRYPT_MODE, myKey, ivspec);
			byte[] cipherText = c3des.doFinal(plaintext);
			password = Base64.encodeToString(cipherText, Base64.DEFAULT);

			// / password = URLEncoder.encode(password, "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return password;
		/********************************************/

	}


	public String decryptIt(String value, Context ctx) {
		String key = String.valueOf(keyChar);
		String initializationVector = String.valueOf(initializationVectorChar);

		String password = null;
		SecretKeySpec myKey = null;

		byte[] plaintext = Base64.decode(value, Base64.DEFAULT);
		byte[] tdesKeyData = key.getBytes();

		byte[] myIV = initializationVector.getBytes();

		Cipher c3des;
		try {
			c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			myKey = new SecretKeySpec(tdesKeyData, "DESede");
			IvParameterSpec ivspec = null;
			try {
				ivspec = new IvParameterSpec(myIV);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			c3des.init(Cipher.DECRYPT_MODE, myKey, ivspec);
			byte[] cipherText = c3des.doFinal(plaintext);
//			password = Base64.encodeToString(cipherText, Base64.DEFAULT);
			password = new String(cipherText, "UTF-8");
			// / password = URLEncoder.encode(password, "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return password;

	}

	public JSONObject getJsonObjectFromMap(Map params) throws JSONException {

		// all the passed parameters from the post request
		// iterator used to loop through all the parameters
		// passed in the post request
		Iterator iter = params.entrySet().iterator();

		// Stores JSON
		JSONObject holder = new JSONObject();

		// using the earlier example your first entry would get email
		// and the inner while would get the value which would be 'foo@bar.com'
		// { fan: { email : 'foo@bar.com' } }

		// While there is another entry
		while (iter.hasNext()) {
			// gets an entry in the params
			Map.Entry pairs = (Map.Entry) iter.next();

			// creates a key for Map
			String key = (String) pairs.getKey();

			// Create a new map
			// Map m = (Map)pairs.getValue();

			// object for storing Json
			String data = (String) pairs.getValue();

			// gets the value
			// Iterator iter2 = m.entrySet().iterator();
			// while (iter2.hasNext())
			// {
			// Map.Entry pairs2 = (Map.Entry)iter2.next();
			// data.put((String)pairs2.getKey(), (String)pairs2.getValue());
			// }

			// puts email and 'foo@bar.com' together in map
			holder.put(key, data);
		}
		return holder;
	}

	public String getDateToken() {
		Calendar cal = Calendar.getInstance();
		Date myDate = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyhhmmssSSS");
		return dateFormat.format(myDate);
	}

	public String getDeviceID(Context ctx) {

		TelephonyManager tManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String serialNumber = Secure.getString(ctx.getContentResolver(),
				Secure.ANDROID_ID);
		String phoneNumber = tManager.getSimSerialNumber();

		Date dateNow = new Date();
		long longDate = dateNow.getTime();

		String m_szDevIDShort = "35"
				+ // we make this look like a valid IMEI
				Build.BOARD.length() % 10 + Build.BRAND.length() % 10
				+ Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10
				+ Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
				+ Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
				+ Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10
				+ Build.TAGS.length() % 10 + Build.TYPE.length() % 10
				+ Build.USER.length() % 10; // 13 digits
		String deviceId = m_szDevIDShort + "" + serialNumber + phoneNumber;
																// ";" +
																// longDate;

		return deviceId;
	}
}
