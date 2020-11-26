package com.factor.gymnasium.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Activities.UserDashboardActivity;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.R;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class MyProfileFragment extends Fragment {
    SharedPreferenceUtils preferances;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
String member_id="";
TextView name,dob;
    private String mParam1;
    private String mParam2;
Button edit_profile,save_profile;
EditText mobile,address,email;
String str_name,str_mobilenumber,str_dob,str_address,str_email;
String str_age,str_gymcategory,str_weight,str_height,str_medicalHistory,str_password,str_currentDateTime;
ImageView pPhoto;
FrameLayout progressBarHolder;
    public MyProfileFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static MyProfileFragment newInstance(String param1, String param2) {
        MyProfileFragment fragment = new MyProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_profile, container, false);
        preferances = SharedPreferenceUtils.getInstance(getContext());
        member_id= preferances.getStringValue("MEMBER_ID","");
        edit_profile=view.findViewById(R.id.edit_profile);
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        name=view.findViewById(R.id.name);
        save_profile=view.findViewById(R.id.save_profile);
        dob=view.findViewById(R.id.dob);
        pPhoto=view.findViewById(R.id.pPhoto);
        mobile=view.findViewById(R.id.mobile);
        address=view.findViewById(R.id.address);
        email=view.findViewById(R.id.email);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss", Locale.getDefault());
        str_currentDateTime = sdf.format(new Date());
        if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(getContext()))){
            Toast.makeText(getContext(),R.string.check_internetConnection,Toast.LENGTH_SHORT).show();
        }else{
            getProfileInformation();
        }
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Now you can edit your profile successfully",Toast.LENGTH_SHORT).show();
                mobile.setFocusableInTouchMode(true);
                address.setFocusableInTouchMode(true);
                email.setFocusableInTouchMode(true);
                pPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectImage();
                    }
                });

            }
        });
        save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               str_mobilenumber=mobile.getText().toString().trim();
                str_address=address.getText().toString().trim();
                str_email=email.getText().toString().trim();
                if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(getContext()))){
                    Toast.makeText(getContext(),R.string.check_internetConnection,Toast.LENGTH_SHORT).show();
                }else{
                   updateProfile();
                }

            }
        });
        return  view;
    }

    private void updateProfile() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url =GlobalItems.MEMBER_BASE_URL+"update.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        JSONObject object = new JSONObject();
        try {
            object.put("gym_id","7");
            object.put("member_id",member_id);
            object.put("full_name",str_name);
            object.put("address", str_address);
            object.put("mobile_number",str_mobilenumber);
            object.put("email",str_email);
//            object.put("gender",str_gender);
            object.put("DOB",str_dob);
            object.put("age",str_age);
            object.put("gym_category",str_gymcategory);
            object.put("weight",str_weight);
            object.put("height",str_height);
//            object.put("bloodgroup",str_bloodgroup);
            object.put("medical_history",str_medicalHistory);
            object.put("password",str_password);
            object.put("created",str_currentDateTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            Toast.makeText(getContext(), response.getString("message"),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent (getContext(), UserDashboardActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarHolder.setVisibility(View.GONE);
                Toast.makeText(getContext(), (CharSequence) error,Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    private void getProfileInformation() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url = GlobalItems.MEMBER_BASE_URL+"read_one.php?member_id="+member_id;

        //Again creating the string request
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                               progressBarHolder.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);


//                            str_currentDateTime=jsonObject.getString("full_name").replace("null","");
                            str_password=jsonObject.getString("password").replace("null","");
                            str_medicalHistory=jsonObject.getString("medical_history").replace("null","");
                            str_height=jsonObject.getString("height").replace("null","");
                            str_weight=jsonObject.getString("weight").replace("null","");
                            str_gymcategory=jsonObject.getString("gym_category").replace("null","");
                            str_age=jsonObject.getString("age").replace("null","");

                            str_name=jsonObject.getString("full_name").replace("null","");
                            str_mobilenumber=jsonObject.getString("mobile_number").replace("null","");
                            str_dob=jsonObject.getString("DOB").replace("null","");
                            str_address=jsonObject.getString("address").replace("null","");
                            str_email=jsonObject.getString("email").replace("null","");
                          /*  str_gender=jsonObject.getString("gender").replace("null","");
                            str_bloodgroup= jsonObject.getString("bloodgroup").replace("null","");*/

                            name.setText(jsonObject.getString("full_name").replace("null",""));
                            dob.setText(jsonObject.getString("DOB").replace("null",""));
                            mobile.setText(jsonObject.getString("mobile_number").replace("null",""));
                            address.setText(jsonObject.getString("address").replace("null",""));
                            email.setText(jsonObject.getString("email").replace("null",""));
                         /*   gender.setText(jsonObject.getString("gender").replace("null",""));
                            blood_group.setText(jsonObject.getString("bloodgroup").replace("null",""));
                            age.setText(str_age+"Years");
                            gym_category.setText(str_gymcategory);
                            weight_profile.setText(str_weight);
                            height_profile.setText(str_height);
                            medical_history.setText(str_medicalHistory);*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //Toast.makeText(LoginActivity.this, userid, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarHolder.setVisibility(View.GONE);

                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

//                        isLoading(false);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //adding the string request to request queue
        requestQueue.add(jsonRequest);
    }

    private void selectImage() {
        Intent intent = CropImage.activity()
                .setAspectRatio(16,16)
                .getIntent(getContext());
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .start(getActivity());
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, @Nullable Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if (requestcode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultcode == Activity.RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri resultUri = result.getUri();
            pPhoto.setImageURI(resultUri);
        }
    }
}