package com.google.firebase.quickstart.database.java.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.quickstart.database.R;

import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    GoogleMap mgoogleMap;
    private MapView mapView = null;
    static View layout;
    private GoogleApiClient mGoogleApiClient;
    Button button;
    EditText ed;
    String TAG;
    private Geocoder geocoder;

    //위치정보 얻는 객체
    private FusedLocationProviderClient mFusedLocationClient;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //권한  체크 요청 코드 정의
    public static final int REQUEST_CODE_PERMISSION = 1000;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        try {
            layout = inflater.inflate(R.layout.activity_mappage, container, false);
        } catch (InflateException e) {
            // 구글맵 View가 이미 inflate되어 있는 상태이므로, 에러를 무시합니다.
        }

        button=layout.findViewById(R.id.button);
        ed=layout.findViewById(R.id.editText);
        mapView = (MapView) layout.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSION);
        }

        return layout;
    }

   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//액티비티가 처음 생성될 때 실행되는 함수
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (layout != null) {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng SEOUL = new LatLng(37.56, 126.97);
        geocoder = new Geocoder(getContext());

        mgoogleMap=map;

       db.collection("체육시설데이터")
               //.whereEqualTo("FACI_POINT_Y",37.37593843)
                .whereGreaterThan("FACI_POINT_Y",  30)
             //  .whereLessThan("FACI_POINT_Y",40)
            //   .whereGreaterThan("FACI_POINT_X",  120)
             //  .whereLessThan("FACI_POINT_X",130)
               .orderBy("FACI_POINT_Y")

              .limit(100)
           //    .whereGreaterThan("FACI_POINT_X",  120)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                             //  String x=document.get("FACI_POINT_X").toString();
                             //   String y=document.get("FACI_POINT_Y").toString();

                               // if()
                                //double dd=Double.parseDouble(x);
                             //   Log.d("zzzzz",document.get("FACI_POINT_X").getClass().getName());
                            //    NumberFormat format=NumberFormat.getInstance();

                            //    format.setGroupingUsed(false);
                               // format.format(Double.parseDouble(y));
                               // format.format(Double.parseDouble(x));
                               MarkerOptions makerOptions = new MarkerOptions();
                                makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                                        .position(new LatLng((double)document.get("FACI_POINT_Y") , (double)document.get("FACI_POINT_X")))
                                        .title("마커" ); // 타이틀.

                                // 2. 마커 생성 (마커를 나타냄)
                                mgoogleMap.addMarker(makerOptions);
                                mgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng((double)document.get("FACI_POINT_Y") , (double)document.get("FACI_POINT_X"))));

                            }

                        } else {
                           // Log.d("asdf", "Error getting documents: ", task.getException());

                        }
                    }
                });



   /*     for (int idx = 0; idx < 10; idx++) {
            // 1. 마커 옵션 설정 (만드는 과정)
            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                    .position(new LatLng(37.50293647 + idx, 127.0794599))
                    .title("마커" + idx); // 타이틀.

            // 2. 마커 생성 (마커를 나타냄)
            mgoogleMap.addMarker(makerOptions);
        }

        // 카메라를 위치로 옮긴다.
        mgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.50293647, 127.0794599)));
*/


        /*final String [] arrayx,arrayy;
        db.collection("체육시설데이터")
                .whereLessThan("num", 10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String x=document.get("FACI_POINT_X").toString();
                                String y=document.get("FACI_POINT_Y").toString();

                                Log.d("ASD", x);

                       //      Number i=document.get("FACI_POINT_Y");
                              //  for (int idx = 0; idx < 10; idx++) {
                                    // 1. 마커 옵션 설정 (만드는 과정)
                                MarkerOptions makerOptions = new MarkerOptions();
                                makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                                        .position(new LatLng(Double.parseDouble(y),Double.parseDouble(x)))
                                        .title("마커"); // 타이틀.

                                // 2. 마커 생성 (마커를 나타냄)
                                mgoogleMap.addMarker(makerOptions);
                            //     }
                                String message = document.getId() + " => " + document.getData();
                               mgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(y),Double.parseDouble(x))));

                            }
                        } else {
                            Log.d("ASD", "Error getting documents: ", task.getException());

                        }
                    }
                });
*/














        //map.setMyLocationEnabled(true);
        UiSettings ui= map.getUiSettings();

        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_END, 0);
        rlp.addRule(RelativeLayout.ALIGN_END, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rlp.setMargins(30, 0, 0, 40);

        ui.setZoomControlsEnabled(true);
        ui.setCompassEnabled(true);
        ui.setMyLocationButtonEnabled(true);
        ui.setScrollGesturesEnabled(true);



        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String str=ed.getText().toString();
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(addressList.get(0).toString());
                // 콤마를 기준으로 split
                String []splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
                System.out.println(address);

                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                System.out.println(latitude);
                System.out.println(longitude);

                // 좌표(위도, 경도) 생성
                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                // 마커 생성
                MarkerOptions mOptions2 = new MarkerOptions();
                mOptions2.title("search result");
                mOptions2.snippet(address);
                mOptions2.position(point);
                // 마커 추가
                map.addMarker(mOptions2);
                // 해당 좌표로 화면 줌
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
            }
        });

    }
    public static Location addrToPoint(Context context){
        Location location=new Location("");
        Geocoder geocoder=new Geocoder(context);
        List<Address> addresses=null;

        try {
            addresses=geocoder.getFromLocationName("vhcjs",3);
        }
         catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses!=null){
            for(int i=0;i<addresses.size();i++){
                Address lating=addresses.get(i);
                location.setLatitude((lating.getLatitude()));
                location.setLongitude(lating.getLongitude());
            }
        }
        return location;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_CODE_PERMISSION:
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getActivity(), "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}
