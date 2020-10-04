package com.google.firebase.quickstart.database.java.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.quickstart.database.R;


import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    String TAG;
    static View layout_commu;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String[] ADDR_CP_NM ={"강원도", "경기도","경상남도","경상북도","광주광역시","대구광역시"
            ,"대전광역시","부산광역시","서울특별시","세종특별자치시","울산광역시","인천광역시"
            ,"전라남도","전라북도","제주특별자치도","충청남도","충청북도"}; //ADDR_CP_NM
    ArrayList<String> ADDR_CPB_NM = new ArrayList<String>();
    Spinner spinner1, spinner2;
    Button button1;

    EditText editText;
    TextView textView;

    // Access a Cloud Firestore instance from your Activity

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            layout_commu = inflater.inflate(R.layout.activity_searchpage, container, false);
        } catch (InflateException e) {
        }
        spinner1 = (Spinner) layout_commu.findViewById(R.id.spinner_place);
        spinner2 = (Spinner) layout_commu.findViewById(R.id.spinner_center);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item,ADDR_CP_NM);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // tvAddr.setText("주소 : "+city[position] + " " +
                // spinner2.getSelectedItem().toString();

                db.collection("체육시설데이터")
                        //.whereEqualTo("ADDR_CP_NM", spinner1.getSelectedItem().toString())
                        .whereLessThan("num",500)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                ADDR_CPB_NM.clear();
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getString("ADDR_CP_NM").equals(spinner1.getSelectedItem().toString()))
                                        {

                                            ADDR_CPB_NM.add(document.getString("ADDR_CPB_NM"));
                                        }
                                        String xx=document.get("ADDR_CP_NM").toString();
                                        // Log.d("ffff", xx);

                               /* if (document.exists()) {
                                    for (int i = 0; i < ADDR_CPB_NM.size(); i++) {


                                        if (!ADDR_CPB_NM.contains((String) document.get("ADDR_CPB_NM"))) {
                                            String xx=document.get("ADDR_CP_NM").toString();
                                            Log.d("ffff", xx);
                                            ADDR_CPB_NM.add((String) document.get("ADDR_CPB_NM"));
                                            Log.d("adsdad", (String) document.get("ADDR_CPB_NM"));
                                        }
                                    }
                                }*/


                                        String x=document.get("FACI_POINT_X").toString();
                                        String y=document.get("FACI_POINT_Y").toString();

                                        String message = document.getId() + " => " + document.getData();
//                                        editText.setText(y);
                                        // textView.setText(x);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                    //   editText.setText("4");
                                }
                            }
                        });

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        ArrayAdapter adapter2 = new ArrayAdapter( getActivity(), android.R.layout.simple_spinner_item,ADDR_CPB_NM);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter2.notifyDataSetChanged();
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {



            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });




        //   editText = layout_commu.findViewById(R.id.editText);
        //    textView = layout_commu.findViewById(R.id.xy);

//        editText.setText("1");
// Add a new document with a generated ID
       /* db.collection("체육시설데이터")
                .whereEqualTo("ADDR_CP_NM", spinner1.getSelectedItem().toString())

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String xx=document.get("ADDR_CP_NM").toString();
                                Log.d("ffff", xx);

                               *//* if (document.exists()) {
                                    for (int i = 0; i < ADDR_CPB_NM.size(); i++) {


                                        if (!ADDR_CPB_NM.contains((String) document.get("ADDR_CPB_NM"))) {
                                            String xx=document.get("ADDR_CP_NM").toString();
                                            Log.d("ffff", xx);
                                            ADDR_CPB_NM.add((String) document.get("ADDR_CPB_NM"));
                                            Log.d("adsdad", (String) document.get("ADDR_CPB_NM"));
                                        }
                                    }
                                }*//*


                                String x=document.get("FACI_POINT_X").toString();
                                String y=document.get("FACI_POINT_Y").toString();

                                String message = document.getId() + " => " + document.getData();
                                editText.setText(y);
                                textView.setText(x);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            editText.setText("4");
                        }
                    }
                });*/

        return layout_commu;


    }
}