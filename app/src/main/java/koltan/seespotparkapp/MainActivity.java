package koltan.seespotparkapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    //double latitude, longitude;
    //private Location mLocation;
    //private GPStracker gpsTracker;
    public int lotOneFilled;
    public int lotOneTotleSpots;
    public int lotTwoFilled;
    public int lotTwoTotleSpots;
    public int lotThreeFilled;
    public int lotThreeTotleSpots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gpsTracker = new GPStracker(getApplicationContext());
        //mLocation = gpsTracker.getLocation();
        //latitude = mLocation.getLatitude();
        //longitude = mLocation.getLongitude();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
       // new JSONTask().execute("http://54.187.124.2:8000/app/get/");
    }

    public void mainMethod(int one, int two, int three, int four, int five, int six) {

        lotOneFilled = one;
        lotTwoFilled = two;
        lotThreeFilled = three;
        lotOneTotleSpots = four;
        lotTwoTotleSpots = five;
        lotThreeTotleSpots = six;

        LatLng lotOne = new LatLng(27.526794, -97.878555);
        LatLng lotTwo = new LatLng(27.525230, -97.878214);
        LatLng lotThree = new LatLng(27.527342, -97.882922);

        if (getPercent(lotOneFilled, lotOneTotleSpots) != 100) {
            Marker lotOneMarker = mMap.addMarker(new MarkerOptions()
                    .position(lotOne)
                    .title(String.valueOf(getPercent(lotOneFilled, lotOneTotleSpots)))
                    .snippet(String.valueOf(getPercent(lotOneFilled, lotOneTotleSpots)))
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_lot_d))

            );
        } else {
            Marker lotOneMarker = mMap.addMarker(new MarkerOptions()
                    .position(lotOne)
                    .title(String.valueOf(getPercent(lotOneFilled, lotOneTotleSpots)))
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_lot_d_red))
            );
        }


        Marker lotTwoMarker = mMap.addMarker(new MarkerOptions()
                .position(lotTwo)
                .title("Engineering Lot \n" + getPercent(lotTwoFilled, lotTwoTotleSpots)));
        Marker lotThreeMarker = mMap.addMarker(new MarkerOptions()
                .position(lotThree)
                .title("Engineering Lot \n" + getPercent(lotThreeFilled, lotThreeTotleSpots)));
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lotOne, 18));

    }


    public int getPercent(double fill, double totle) {

        double percent = (fill / totle) * 100;
        int percentInt = (int) percent;
        return percentInt;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng LotF = new LatLng(27.526794, -97.878555);
        LatLng lotTwo = new LatLng(27.525230, -97.878214);
        LatLng engi = new LatLng(27.526107, -97.878728);
       // LatLng myLocation = new LatLng(latitude, longitude);
       // mMap.addMarker(new MarkerOptions()
        //        .position(myLocation)
         //       .title("I'm here"));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(LotF));
        mMap.setMinZoomPreference(15);
        mMap.setMaxZoomPreference(18);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        LatLng lotOne = new LatLng(27.526794, -97.878555);

        Marker lotOneMarker = mMap.addMarker(new MarkerOptions()
                .position(lotOne)
                .title("78% full")
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.parking_lot_d)));
        Marker yourLocation = mMap.addMarker(new MarkerOptions()
                .position(engi)
                .draggable(false)
                .title("your location"));
        Marker lotTwoMarker = mMap.addMarker(new MarkerOptions()
                .position(lotTwo)
                .draggable(false)
                .title("%89 full")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.lot_7_yellow)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(engi, 18));
    }

    public void voiceReturn(View view) {
        LatLng engiLot = new LatLng(27.526794, -97.878555);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(engiLot, 18));

    }

    public class JSONTask extends AsyncTask<String,String,String > {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            try {
                String finalJson = result;
                JSONObject finalObject = null;
                finalObject = new JSONObject(finalJson);
                JSONObject lotOneObject = finalObject.getJSONObject("0");
                JSONObject lotTwoObject = finalObject.getJSONObject("1");
                JSONObject lotThreeObject = finalObject.getJSONObject("2");

                lotOneFilled = lotOneObject.getInt("number_of_filled_spots");
                lotTwoFilled = lotTwoObject.getInt("number_of_filled_spots");
                lotThreeFilled = lotThreeObject.getInt("number_of_filled_spots");
                lotOneTotleSpots = lotOneObject.getInt("total_capacity");
                lotTwoTotleSpots = lotTwoObject.getInt("total_capacity");
                lotThreeTotleSpots = lotThreeObject.getInt("total_capacity");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mainMethod(lotOneFilled, lotTwoFilled, lotThreeFilled, lotOneTotleSpots, lotTwoTotleSpots, lotThreeTotleSpots);
        }
    }
}
