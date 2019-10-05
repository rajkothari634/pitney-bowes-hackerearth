package com.example.pitneybowes.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pitneybowes.Classes.Delivery;
import com.example.pitneybowes.Classes.DeliveryBoy;
import com.example.pitneybowes.R;
import com.example.pitneybowes.RecyclerAdapter.RecyclerAdapterTaskToDo;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.location.LocationListener {

    FirebaseAuth mAuth;
    String DID;
    ImageView imageViewsort;
    List<Delivery> deliveryList;
    List<Delivery> deliveryList2;
    Delivery delivery;
    DeliveryBoy deliveryBoy;
    DatabaseReference deliveryGivenRef;
    DatabaseReference deliveryRef;
    RecyclerView recyclerView;
    Delivery update1;
    DeliveryBoy deliveryBoy;
    DatabaseReference deliveryGivenRef;
    DatabaseReference deliveryRef;
    RecyclerView recyclerView;
    ArrayList<Place> places = new ArrayList<>();
    ArrayList<Place> places2 = new ArrayList<>();
    Button arrange_btn;
    ArrayList<Double> distances = new ArrayList<>();
    ListView listView;
    LatLng mylocation;
    LatLng update;
    String name;
    List<String> list2;
    Place place;
    private GoogleMap mMap;
    GoogleApiClient googleApiClient;
    public Location lastlocation;
    LocationRequest locationRequest;

    TextView currentDate;
    RecyclerView recyclerViewCurrentDelivery;

    public String getTime() {
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
        String time = simpleDateFormat.format(calander.getTime());
        return time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentDate = findViewById(R.id.textViewDate);
        recyclerViewCurrentDelivery = findViewById(R.id.recyclerViewRecords);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        imageViewsort = findViewById(R.id.imageViewSort);
        deliveryList2 = new ArrayList<>();
        deliveryBoy = new DeliveryBoy();
        update1 = new Delivery();

        deliveryList = new ArrayList<>();
        deliveryBoy = new DeliveryBoy();
        LinearLayoutManager mlayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCurrentDelivery.setLayoutManager(mlayoutmanager);
        Date currentTime = Calendar.getInstance().getTime();
        final String dateandtime = currentTime.toString();
        Log.i("dateandtime", dateandtime);
        currentDate.setText(dateandtime.substring(0, 9));


        mAuth = FirebaseAuth.getInstance();
        DID = mAuth.getCurrentUser().getUid();


        deliveryGivenRef = FirebaseDatabase.getInstance().getReference().child("deliveryboy").child(DID);

        deliveryGivenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.child("deliverygiven").getChildren().iterator();
                HashMap<String, String> deliveryGivenHashmap = new HashMap<>();
                while (items.hasNext()) {
                    DataSnapshot item = items.next();
                    deliveryGivenHashmap.put(item.getKey(), (String) item.getValue());
                }
                deliveryBoy.setDeliveryGiven(deliveryGivenHashmap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        for (final HashMap.Entry<String, String> entry : deliveryBoy.getDeliveryGiven().entrySet()) {

            deliveryRef = FirebaseDatabase.getInstance().getReference().child("delivery").child(entry.getKey());
            deliveryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Take the data from datasnapshot and put in delivery object and then add it to delivery list
                    deliveryRef = FirebaseDatabase.getInstance().getReference().child("delivery").child(entry.getKey());
                    deliveryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            delivery = new Delivery();
                            delivery.setAddress(dataSnapshot.child("address").getValue(String.class));
                            delivery.setId(dataSnapshot.child("id").getValue(String.class));
                            delivery.setDateOfOrder(dataSnapshot.child("dateoforder").getValue(String.class));
                            delivery.setReceiverEmailId(dataSnapshot.child("emailid").getValue(String.class));
                            delivery.setDeliveryBoyId(dataSnapshot.child("deliveryboyid").getValue(String.class));
                            delivery.setReceiverPhoneNumber(dataSnapshot.child("recieverphoneno").getValue(String.class));
                            delivery.setReceiverName(dataSnapshot.child("recievername").getValue(String.class));
                            delivery.setDeliveryStatus(dataSnapshot.child("deliverystatus").getValue(String.class));
                            delivery.setDeliveryPrice(dataSnapshot.child("deliveryprice").getValue(String.class));
                            delivery.setModeOfPayment(dataSnapshot.child("modeofpayment").getValue(String.class));
                            delivery.setPaymentStatus(dataSnapshot.child("paymentstatus").getValue(String.class));
                            delivery.setDeliveryCompanyName(dataSnapshot.child("companyname").getValue(String.class));
                            delivery.setLat(dataSnapshot.child("location").child("latitude").getValue(String.class));
                            delivery.setLng(dataSnapshot.child("location").child("longitude").getValue(String.class));

                            //  public String deliveryRating;
                            delivery.setDeliveryPriority("4");
                            deliveryList.add(delivery);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


        delivery.setDeliveryPriority("4");

        if (i == 0) {
            delivery.setLocation(new LatLng(25.6081765, 85.0801507));

        } else if (i == 1) {
            delivery.setLocation(new LatLng(25.6032982, 85.1348353));
        } else if (i == 2) {
            delivery.setLocation(new LatLng(25.620337, 85.1372593));

        }
        deliveryList.add(delivery);
    }
//        //now we have list that contain todays delivery of delivery boy
//

    //        //now we will set adaptere
    final Intent intent = new Intent(MainActivity.this, RecyclerAdapterTaskToDo.class);
    RecyclerAdapterTaskToDo recyclerAdapterTaskToDo = new RecyclerAdapterTaskToDo(deliveryList, intent, MainActivity.this);
        recyclerViewCurrentDelivery.setAdapter(recyclerAdapterTaskToDo);

//        //adapter setting completed
//

//        //on Click function


  imageViewsort.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){


        while (deliveryList.size() != 0) {
            double min = Integer.MAX_VALUE;
            for (int i = 0; i < deliveryList.size(); i++) {
                double d = distance(lastlocation.getLatitude(), lastlocation.getLongitude(), deliveryList.get(i).location.latitude, deliveryList.get(i).location.longitude);
                ;
                Log.i("dis", new String("" + d));
                if (min > d) {
                    min = d;
                    update1 = deliveryList.get(i);
//                    name=places.get(i).name;
                    //                  update = places.get(i).location;
                    Log.i("main", "prince");
                }

            }
            mylocation = update;
            //list2.add(name);
            deliveryList2.add(update1);
            deliveryList.remove(update1);
            Log.i("name", "app");
        }
        RecyclerAdapterTaskToDo recyclerAdapterTaskToDo1 = new RecyclerAdapterTaskToDo(deliveryList2, intent,MainActivity.this);
        recyclerViewCurrentDelivery.setAdapter(recyclerAdapterTaskToDo1);
    }
    });

    // String s=""+lastlocation.getLatitude()+lastlocation.getLongitude();
    Intent myIntent = new Intent(MainActivity.this, RecyclerAdapterTaskToDo.class);
    // myIntent.putExtra("lastlocation",s);





}
    public static double distance(double fromLat, double fromLon, double toLat, double toLon) {
        double radius = 6378137;   // approximate Earth radius, *in meters*
        double deltaLat = toLat - fromLat;
        double deltaLon = toLon - fromLon;
        double angle = 2 * Math.asin( Math.sqrt(
                Math.pow(Math.sin(deltaLat/2), 2) +
                        Math.cos(fromLat) * Math.cos(toLat) *
                                Math.pow(Math.sin(deltaLon/2), 2) ) );
        return radius * angle;
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest=LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        Log.i("map","shown");

        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastlocation=location;
        mylocation=new LatLng(lastlocation.getLatitude(),lastlocation.getLongitude());
        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.i("map1","shown");



        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkLocationPermission();
            return;
        }
        buildGoogleApiclient();
        mMap.setMyLocationEnabled(true);

    }
    protected synchronized void buildGoogleApiclient(){
        googleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (googleApiClient == null) {
                            buildGoogleApiclient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

int requestCode = 1;
    public String startScanning(String order_number){
        order_checking_number = order_number;
        Intent intent = new Intent(this,QRcodeScanner.class);
        startActivityForResult(intent,requestCode);

        return qr_result;
    }
    String order_checking_number;
    String qr_result;
    String returnedResult;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                returnedResult = data.getData().toString();
                if(order_checking_number.equals(returnedResult)){
                    qr_result="yes";
                }
                else{
                    qr_result="no";
                }
                // OR
                // String returnedResult = data.getDataString();
    }






}
