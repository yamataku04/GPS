package com.example.gps;
import java.net.HttpURLConnection;   
import org.apache.http.HttpResponse; 
import org.apache.http.StatusLine; 
import org.apache.http.client.HttpClient; 
import org.apache.http.client.methods.HttpGet; 
import org.apache.http.impl.client.DefaultHttpClient; 
import org.apache.http.util.EntityUtils; 
import org.json.JSONArray; 
import org.json.JSONException; 
import org.json.JSONObject;  
import android.app.Activity; 
import android.content.Intent; 
import android.location.Location; 
import android.location.LocationListener; 
import android.location.LocationManager; 
import android.net.Uri; 
import android.os.AsyncTask; 
import android.os.Bundle; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.Button; 
import android.widget.Toast; 
public class MainActivity extends Activity { 

	 
private LocationManager locationManager = null;	 
 
	@Override 
	protected void onCreate(Bundle savedInstanceState) { 
 		super.onCreate(savedInstanceState); 
 		setContentView(R.layout.activity_main); 
 		Button button = (Button) findViewById(R.id.button1); 
 		button.setOnClickListener(mButton1Listener); 
 	} 
 
 
 	private OnClickListener mButton1Listener = new OnClickListener() { 
 		public void onClick(View v) { 
 	        if (locationManager != null) { 
 	        	// 取得処理を終了 
 	        	locationManager.removeUpdates(mLocationListener); 
 	        } 
         	locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); 
         	 
             // GPSから位置情報を取得する設定 
             boolean isGpsOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); 
         	// 3Gまたはwifiから位置情報を取得する設定 
             boolean isWifiOn =  locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); 
             String provider = null; 
 			if (isGpsOn) { 
 				provider = LocationManager.GPS_PROVIDER; 
 			} else if (isWifiOn) { 
             	provider = LocationManager.NETWORK_PROVIDER; 
             } else { 
             	Toast.makeText(getApplicationContext(), "Wi-FiかGPSをONにしてください", Toast.LENGTH_LONG).show(); 
             	return; 
             } 
 			Toast.makeText(getApplicationContext(), "Provider=" + provider, Toast.LENGTH_LONG).show(); 
 			 
 			// ロケーション取得を開始 
             locationManager.requestLocationUpdates(provider, 1000L, 0, mLocationListener); 
         } 
 	}; 
 
 
 	private LocationListener mLocationListener = new LocationListener() { 
         public void onStatusChanged(String provider, int status, Bundle extras) { 
         } 
         public void onProviderEnabled(String provider) { 
         } 
         public void onProviderDisabled(String provider) { 
         } 
         public void onLocationChanged(Location location) { 
         	String latitude = Double.toString(location.getLatitude()); 
         	String longitude = Double.toString(location.getLongitude()); 
         	String message = ""; 
             message += ("緯度："+latitude); 
             message += "\n"; 
             message += ("経度："+longitude); 
             message += "\n"; 
             message += ("Accuracy"+Float.toString(location.getAccuracy())); 
             Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show(); 
              
             // 位置情報の取得を1回しか行わないので取得をストップ 
             locationManager.removeUpdates(mLocationListener); 
 
 
 
            
 
         } 
     }; 
      
     @Override 
     protected void onPause() { 
         if (locationManager != null) { 
         	locationManager.removeUpdates(mLocationListener); 
         } 
         super.onPause(); 
     } 
 
 
 	private void showYahooMap(String latitude, String longitude) { 
 		String urlString = "http://map.yahoo.co.jp/maps?type=scroll&pointer=on&sc=2" 
 				+ "&lat=" + latitude 
 				+ "&lon=" + longitude; 
 		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString)); 
 		startActivity(intent); 
 	} 
 	 }
          
      