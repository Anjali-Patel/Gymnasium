package com.factor.gymnasium.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Activities.BookingConfirmation;
import com.factor.gymnasium.Activities.MainActivity;
import com.factor.gymnasium.Activities.UserDashboardActivity;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.R;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class MyProfileFragment extends Fragment {
    Spinner blood_groupSpinner,SpinnergenderList;
    private int mYear, mMonth, mDay;
    ArrayList<String> bloodGroupList;
    ArrayList<String> GenderGroupList;

    View view;
    Dialog dialog;
//    String response;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
String member_id,str_bmi="",str_fat="",strOther="",strRenewelDate="",strSince="",strTypemembership="",strValidity="",strSetby;
    SharedPreferenceUtils preferances;
TextView name,dob,mobile,address,email,edit,edit1,gender,edit_gender;
TextView height_profile,edit_height,weight_profile,edit_weight_profile,bmi,edit_bmi,fat,edit_fat,other,edit_other,Emergency_Contact,edit_Emergency_Contact,strn_bloodgroup,edit_strn_bloodgroup,medical_history,edit_medical_history,type_membership,edit_type_membership,since,edit_since,renewel_date,edit_renewel_date,validity,edit_validity,set_by,edit_set_by;
private String mParam1;
    private String mParam2;
Button edit_profile,save_profile;
//EditText mobile,address,email;
String str_name,str_mobilenumber,str_dob="",str_address,str_email,strGender,strBloodGroup="";
String str_age="",str_emergencyContact="",str_gymcategory="",str_weight="",str_height="",str_medicalHistory="",str_password="",str_currentDateTime;
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
         view= inflater.inflate(R.layout.fragment_my_profile, container, false);
        GenderGroupList= new ArrayList<>();
        bloodGroupList= new ArrayList<>();
        bloodGroupList.add("Select Your Blood Group");
        bloodGroupList.add("AB+");
        bloodGroupList.add("AB-");
        bloodGroupList.add("A+");
        bloodGroupList.add("A-");
        bloodGroupList.add("B+");
        bloodGroupList.add("B-");
        bloodGroupList.add("O-");
        bloodGroupList.add("O+");
        GenderGroupList.add("Select gender");
        GenderGroupList.add("Male");
        GenderGroupList.add("Female");
        GenderGroupList.add("Other");


        preferances = SharedPreferenceUtils.getInstance(getContext());
        member_id= preferances.getStringValue("MEMBER_ID","");
        Log.i("Member Id",member_id);
        initValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.getDefault());
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
    private void initValue() {
        gender=view.findViewById(R.id.gender);
        edit_gender=view.findViewById(R.id.edit_gender);
        SpinnergenderList=view.findViewById(R.id.SpinnergenderList);
        blood_groupSpinner=view.findViewById(R.id.blood_groupSpinner);
        edit_other=view.findViewById(R.id.edit_other);
        height_profile=view.findViewById(R.id.height_profile);
        edit_height=view.findViewById(R.id.edit_height);
        weight_profile=view.findViewById(R.id.weight_profile);
        edit_weight_profile=view.findViewById(R.id.edit_weight_profile);
        bmi=view.findViewById(R.id.bmi);
        edit_bmi=view.findViewById(R.id.edit_bmi);
        fat=view.findViewById(R.id.fat);
        edit_fat=view.findViewById(R.id.edit_fat);
        other=view.findViewById(R.id.other);
        Emergency_Contact=view.findViewById(R.id.Emergency_Contact);
        edit_Emergency_Contact=view.findViewById(R.id.edit_Emergency_Contact);
        strn_bloodgroup=view.findViewById(R.id.strn_bloodgroup);
        edit_strn_bloodgroup=view.findViewById(R.id.edit_strn_bloodgroup);
        medical_history=view.findViewById(R.id.medical_history);
        edit_medical_history=view.findViewById(R.id.edit_medical_history);
        edit=view.findViewById(R.id.edit);
        edit1=view.findViewById(R.id.edit1);
        edit_profile=view.findViewById(R.id.edit_profile);
        progressBarHolder=view.findViewById(R.id.progressBarHolder);
        name=view.findViewById(R.id.name);
        save_profile=view.findViewById(R.id.save_profile);
        dob=view.findViewById(R.id.dob);
        pPhoto=view.findViewById(R.id.pPhoto);
        mobile=view.findViewById(R.id.mobile);
        address=view.findViewById(R.id.address);
        email=view.findViewById(R.id.email);
        mobile.setText(preferances.getStringValue("mobile_number",""));
        address.setText(preferances.getStringValue("address",""));
        email.setText(preferances.getStringValue("email",""));
        name.setText(preferances.getStringValue("name",""));
     edit_gender.setOnClickListener(view1 -> {
         SpinnergenderList.setVisibility(View.VISIBLE);
         ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, GenderGroupList);
         SpinnergenderList.setAdapter(arrayAdapter);
         SpinnergenderList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 strGender = GenderGroupList.get(position);
                 gender.setText(strGender);

             }
             @Override
             public void onNothingSelected(AdapterView<?> parent) {
             }
         });
     });
        edit_height.setOnClickListener(view1 -> {
            dialog = new Dialog(Objects.requireNonNull(getContext()), R.style.AlertDialogCustom);
            dialog.setContentView(R.layout.profile_dialog);
            EditText mobile_number = dialog.findViewById(R.id.mobile_number);
            mobile_number.setInputType(InputType.TYPE_CLASS_NUMBER);
            TextView enter_mobile_number = dialog.findViewById(R.id.enter_mobile_number);
            enter_mobile_number.setText("Enter Your Height");

            Button ok_button = dialog.findViewById(R.id.ok_button);
            Button cancel = dialog.findViewById(R.id.cancel);
            ok_button.setOnClickListener(v -> {
                if(mobile_number.getText().toString().trim().equalsIgnoreCase("")){
                    mobile_number.setError("Please enter your height");
                }else{
                    height_profile.setText(mobile_number.getText().toString().trim());
                    str_height= mobile_number.getText().toString().trim();
                    dialog.dismiss();

                }

            });
            cancel.setOnClickListener(v -> {
                mobile_number.setText("");
                dialog.dismiss();
            });
            try {
                dialog.setCancelable(false);
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        edit_weight_profile.setOnClickListener(view1 -> {
            dialog = new Dialog(Objects.requireNonNull(getContext()), R.style.AlertDialogCustom);
            dialog.setContentView(R.layout.profile_dialog);
            dialog.setCancelable(false);
            EditText mobile_number = dialog.findViewById(R.id.mobile_number);
            mobile_number.setInputType(InputType.TYPE_CLASS_NUMBER);
            TextView enter_mobile_number=dialog.findViewById(R.id.enter_mobile_number);
               enter_mobile_number.setText("Enter  your weight");
            Button ok_button = dialog.findViewById(R.id.ok_button);
            Button cancel = dialog.findViewById(R.id.cancel);
            ok_button.setOnClickListener(v -> {
                if(mobile_number.getText().toString().trim().equalsIgnoreCase("")){
                    mobile_number.setError("Please enter your weight");
                }else{
                    weight_profile.setText(mobile_number.getText().toString().trim());
                    str_weight= mobile_number.getText().toString().trim();
                    dialog.dismiss();

                }

            });
            cancel.setOnClickListener(v -> {
                mobile_number.setText("");
                dialog.dismiss();
            });
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        edit_bmi.setOnClickListener(view1 -> {
            dialog = new Dialog(Objects.requireNonNull(getContext()), R.style.AlertDialogCustom);
            dialog.setContentView(R.layout.profile_dialog);
            dialog.setCancelable(false);
            EditText mobile_number = dialog.findViewById(R.id.mobile_number);
            mobile_number.setInputType(InputType.TYPE_CLASS_NUMBER);

            TextView enter_mobile_number=dialog.findViewById(R.id.enter_mobile_number);
                enter_mobile_number.setText("Enter Your BMI");
            Button ok_button = dialog.findViewById(R.id.ok_button);
            Button cancel = dialog.findViewById(R.id.cancel);
            ok_button.setOnClickListener(v -> {
                if(mobile_number.getText().toString().trim().equalsIgnoreCase("")){
                    mobile_number.setError("Please enter your BMI");
                }else{
                    bmi.setText(mobile_number.getText().toString().trim());
                    str_bmi= mobile_number.getText().toString().trim();
                    dialog.dismiss();

                }

            });
            cancel.setOnClickListener(v -> {
                mobile_number.setText("");
                dialog.dismiss();
            });
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        edit_fat.setOnClickListener(view1 -> {
            dialog = new Dialog(Objects.requireNonNull(getContext()), R.style.AlertDialogCustom);
            dialog.setContentView(R.layout.profile_dialog);
            dialog.setCancelable(false);
            EditText mobile_number = dialog.findViewById(R.id.mobile_number);
            mobile_number.setInputType(InputType.TYPE_CLASS_NUMBER);

            TextView enter_mobile_number=dialog.findViewById(R.id.enter_mobile_number);
               enter_mobile_number.setText("Enter Your Fat  in percentage");
            Button ok_button = dialog.findViewById(R.id.ok_button);
            Button cancel = dialog.findViewById(R.id.cancel);
            ok_button.setOnClickListener(v -> {
                if(mobile_number.getText().toString().trim().equalsIgnoreCase("")){
                    mobile_number.setError("Please enter your fat percentage");
                }else{
                    fat.setText(mobile_number.getText().toString().trim());
                    str_fat= mobile_number.getText().toString().trim();
                    dialog.dismiss();
                }

            });
            cancel.setOnClickListener(v -> {
                mobile_number.setText("");
                dialog.dismiss();
            });
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        edit_other.setOnClickListener(view1 -> {
            dialog = new Dialog(Objects.requireNonNull(getContext()), R.style.AlertDialogCustom);
            dialog.setContentView(R.layout.profile_dialog);
            dialog.setCancelable(false);
            EditText mobile_number = dialog.findViewById(R.id.mobile_number);
            TextView enter_mobile_number=dialog.findViewById(R.id.enter_mobile_number);
            enter_mobile_number.setText("Enter Your Other information");
            Button ok_button = dialog.findViewById(R.id.ok_button);
            Button cancel = dialog.findViewById(R.id.cancel);
            ok_button.setOnClickListener(v -> {
                if(mobile_number.getText().toString().trim().equalsIgnoreCase("")){
                    mobile_number.setError("Please enter your other information");
                }else{
                    other.setText(mobile_number.getText().toString().trim());
                    strOther= mobile_number.getText().toString().trim();
                    dialog.dismiss();

                }

            });
            cancel.setOnClickListener(v -> {
                mobile_number.setText("");
                dialog.dismiss();
            });
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        edit_Emergency_Contact.setOnClickListener(view1 -> {
            dialog = new Dialog(Objects.requireNonNull(getContext()), R.style.AlertDialogCustom);
            dialog.setContentView(R.layout.profile_dialog);
            dialog.setCancelable(false);
            EditText mobile_number = dialog.findViewById(R.id.mobile_number);
            mobile_number.setInputType(InputType.TYPE_CLASS_NUMBER);

            TextView enter_mobile_number=dialog.findViewById(R.id.enter_mobile_number);
              enter_mobile_number.setText("Enter Your Emergency Contact");
            Button ok_button = dialog.findViewById(R.id.ok_button);
            Button cancel = dialog.findViewById(R.id.cancel);
            ok_button.setOnClickListener(v -> {
                if(mobile_number.getText().toString().trim().equalsIgnoreCase("")){
                    mobile_number.setError("Please enter your emergency contact");
                }else{
                    Emergency_Contact.setText(mobile_number.getText().toString().trim());
                    str_emergencyContact= mobile_number.getText().toString().trim();
                    dialog.dismiss();
                }
            });
            cancel.setOnClickListener(v -> {
                mobile_number.setText("");
                dialog.dismiss();
            });
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        edit_strn_bloodgroup.setOnClickListener(view1 -> {
            blood_groupSpinner.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, bloodGroupList);
                    blood_groupSpinner.setAdapter(arrayAdapter);
                    blood_groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            strBloodGroup = bloodGroupList.get(position);
                            strn_bloodgroup.setText(strBloodGroup);

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

        });

        edit_medical_history.setOnClickListener(view1 -> {
            dialog = new Dialog(Objects.requireNonNull(getContext()), R.style.AlertDialogCustom);
            dialog.setContentView(R.layout.profile_dialog);
            dialog.setCancelable(false);
            EditText mobile_number = dialog.findViewById(R.id.mobile_number);
            TextView enter_mobile_number=dialog.findViewById(R.id.enter_mobile_number);
             enter_mobile_number.setText("Enter Your Medical History");
            Button ok_button = dialog.findViewById(R.id.ok_button);
            Button cancel = dialog.findViewById(R.id.cancel);
            ok_button.setOnClickListener(v -> {
                if(mobile_number.getText().toString().trim().equalsIgnoreCase("")){
                    mobile_number.setError("Please enter your medical history");
                }else{
                    medical_history.setText(mobile_number.getText().toString().trim());
                    str_medicalHistory= mobile_number.getText().toString().trim();
                    dialog.dismiss();
                } });
            cancel.setOnClickListener(v -> {
                mobile_number.setText("");
                dialog.dismiss();
            });
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        edit.setOnClickListener(view1 -> {
            dialog = new Dialog(Objects.requireNonNull(getContext()), R.style.AlertDialogCustom);
            dialog.setContentView(R.layout.profile_dialog);
            dialog.setCancelable(false);
            EditText mobile_number = dialog.findViewById(R.id.mobile_number);
            mobile_number.setInputType(InputType.TYPE_CLASS_NUMBER);
            TextView enter_mobile_number=dialog.findViewById(R.id.enter_mobile_number);
              enter_mobile_number.setText("Enter Mobile Number");
            Button ok_button = dialog.findViewById(R.id.ok_button);
            Button cancel = dialog.findViewById(R.id.cancel);
            ok_button.setOnClickListener(v -> {
                if(mobile_number.getText().toString().trim().equalsIgnoreCase("")){
                    mobile_number.setError("Please enter mobile number");
                }else{
                    mobile.setText(mobile_number.getText().toString().trim());
                    str_mobilenumber= mobile_number.getText().toString().trim();
                    dialog.dismiss();

                }

            });
            cancel.setOnClickListener(v -> {
                mobile_number.setText("");
                dialog.dismiss();
            });
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        edit1.setOnClickListener(view1 -> {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                           str_dob = year+"/"+(monthOfYear +1)+"/"+dayOfMonth;
                           str_age=String.valueOf(mYear- year);
                            dob.setText(str_dob);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
    }
    private void updateProfile() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url ="http://printacheque.com/gymapp/api/member/update.php";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        JSONObject object = new JSONObject();
        try {
            object.put("member_id",member_id);
            object.put("emergency_contact_no",str_mobilenumber);
            object.put("gender",strGender);
            object.put("DOB",str_dob);
            object.put("age",str_age);
            object.put("weight",str_weight);
            object.put("height",str_height);
            object.put("bloodgroup",strBloodGroup);
            object.put("medical_history",str_medicalHistory);
            object.put("created",str_currentDateTime);
            object.put("BMI",str_bmi);
            object.put("fat ",str_fat);
            object.put("other",strOther);
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
                Toast.makeText(getContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private void getProfileInformation() {
        progressBarHolder.setVisibility(View.VISIBLE);
        String url = "http://printacheque.com/gymapp/api/member/read_one.php?member_id="+member_id;
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                  @Override
                    public void onResponse( String response) {
                      progressBarHolder.setVisibility(View.GONE);
                        try {
                            JSONObject   jsonObject = new JSONObject(response);
                            str_name=jsonObject.getString("full_name").replace("null","");
                            str_mobilenumber=jsonObject.getString("emergency_contact_no").replace("null","");
                            str_dob=jsonObject.getString("DOB").replace("null","");
                            str_address=jsonObject.getString("address").replace("null","");
                            str_email=jsonObject.getString("email").replace("null","");
                             strBloodGroup=jsonObject.getString("bloodgroup").replace("null","");
                            strGender=jsonObject.getString("gender").replace("null","");
                            str_medicalHistory=jsonObject.getString("medical_history").replace("null","");
                            str_height= jsonObject.getString("height").replace("null","");
                            str_bmi=jsonObject.getString("BMI").replace("null","");
                            str_weight=jsonObject.getString("weight").replace("null","");
                            strOther=jsonObject.getString("other").replace("null","");
                            str_fat=jsonObject.getString("fat").replace("null","");
 fat.setText(str_fat);
 other.setText(strOther);
 weight_profile.setText(str_weight);
 height_profile.setText(str_height);
  bmi.setText(str_bmi);
 gender.setText(strGender);
 medical_history.setText(str_medicalHistory);
strn_bloodgroup.setText(strBloodGroup);
name.setText(jsonObject.getString("full_name").replace("null",""));
                            dob.setText(jsonObject.getString("DOB").replace("null",""));
                            mobile.setText(jsonObject.getString("emergency_contact_no").replace("null",""));
                            address.setText(jsonObject.getString("address").replace("null",""));
                            email.setText(jsonObject.getString("email").replace("null",""));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                  }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            Toast.makeText(getContext(),"member Does not exist", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonRequest);
    }

    private void selectImage() {
        Intent intent = CropImage.activity()
                .setAspectRatio(16,16)
                .getIntent(getContext());
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);

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