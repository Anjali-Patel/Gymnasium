package com.factor.gymnasium.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {
EditText name,mobile_number,email,dob,address,password,confirm_password,weight,height;
String str_name,str_email,str_mobile_number,str_dob,str_address,str_password,str_confirm_password,str_gender,Selected_gymcategory,str_bloodGroup,str_medicalHistory;
Spinner blood_group,gender,gym_category,user_medical_history;
String atr_age,str_GymCategory="The Membership Gym";
String imageUrl="",  currentDateandTime="",random_UserId;
    SharedPreferenceUtils preferances;
    String myResponse;
    ImageView img_camera_pic;
    DatePickerDialog picker;
    String str_height=null,str_weight=null;
    FrameLayout progressBarHolder;
    int random_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        preferances = SharedPreferenceUtils.getInstance(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initViews();

     /*   final String[] MedicalHistoryList = { "Select Medical History", "Yes", "No"};
        final String[] bloodGroupList = { "Select Blood Group", "A+", "A-", "B+", "B-","AB+","AB-","O-","O+"};
        final String[] GenderList = { "Select Gender", "Male", "Female", "Transgender"};
        ArrayAdapter<String> medical_history = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,MedicalHistoryList);
        medical_history.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user_medical_history.setAdapter(medical_history);
        user_medical_history.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (MedicalHistoryList[position]) {
                    case "Yes":
                        str_medicalHistory = "Yes";
                        break;
                    case "No":
                        str_medicalHistory = "No";
                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,bloodGroupList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blood_group.setAdapter(aa);
        blood_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (bloodGroupList[position]) {
                    case "A+":
                        str_bloodGroup = "A+";
                        break;
                    case "A-":
                        str_bloodGroup = "A-";
                        break;
                    case "B+":
                        str_bloodGroup = "B+";

                        break;
                    case "B-":
                        str_bloodGroup = "B-";

                        break;
                    case "AB+":
                        str_bloodGroup = "AB+";

                        break;
                    case "AB-":
                        str_bloodGroup = "AB-";

                        break;
                    case "O-":
                        str_bloodGroup = "O-";
                        break;
                    case "O+":
                        str_bloodGroup = "O+";
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> aa1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,GenderList);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(aa1);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (GenderList[position]) {
                    case "Male":
                        str_gender = "Male";
                        break;
                    case "Female":
                        str_gender = "Female";
                        break;
                    case "Transgender":
                        str_gender = "Transgender";
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final String[] gymCategoryList = {"Select Gym Category", "The Membership Gym", "The 24 hour access gym", "CrossFit", "Boot Camps", "Training gyms"};
        ArrayAdapter<String> aa2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, gymCategoryList);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gym_category.setAdapter(aa2);
        gym_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (gymCategoryList[position]) {
                    case "The Membership Gym":
                        Selected_gymcategory = "The Membership Gym";
                        str_GymCategory=Selected_gymcategory;
                        break;
                    case "The 24 hour access Gym":
                        Selected_gymcategory = "The 24 hour access Gym";
                        str_GymCategory=Selected_gymcategory;
                        break;
                    case "CrossFit":
                        Selected_gymcategory = "CrossFit";
                        str_GymCategory=Selected_gymcategory;

                        break;
                    case "Boot Camps":
                        Selected_gymcategory ="Boot Camps";
                        str_GymCategory=Selected_gymcategory;

                        break;
                    case "Training  gyms":
                        Selected_gymcategory = "Training  gyms";
                        str_GymCategory=Selected_gymcategory;

                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }

    private void initViews() {
        progressBarHolder=findViewById(R.id.progressBarHolder);
        user_medical_history=findViewById(R.id.user_medical_history);
        weight=findViewById(R.id.weight);
        height=findViewById(R.id.height);
//        img_camera_pic=findViewById(R.id.img_camera_pic);
        name=findViewById(R.id.name);
        mobile_number=findViewById(R.id.mobile_number);
        email=findViewById(R.id.email);
        dob=findViewById(R.id.dob);
        address=findViewById(R.id.address);
        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);
        blood_group=findViewById(R.id.blood_group);
        gender=findViewById(R.id.gender);
        gym_category=findViewById(R.id.gym_category);
    }

    /*
        public  void pic_image(View view){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(RegistrationActivity.this);
        }
    */
    public void Submit(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.getDefault());
         currentDateandTime = sdf.format(new Date());
            str_name=name.getText().toString().trim();
            str_email=email.getText().toString().trim();
            str_mobile_number=mobile_number.getText().toString().trim();
//            str_dob=dob.getText().toString().trim();
            str_address=address.getText().toString().trim();
            str_password=password.getText().toString().trim();
            str_confirm_password=confirm_password.getText().toString().trim();
//            str_height=height.getText().toString().trim();
//            str_weight=weight.getText().toString().trim();
            if(str_name.equalsIgnoreCase("")){
                name.requestFocus();
                name.setError("Please enter your name");
            } else if(str_email.equalsIgnoreCase("")){
                email.requestFocus();
                email.setError("Please enter your email address");
            }else  if(str_mobile_number.equalsIgnoreCase("")){
                mobile_number.requestFocus();
                mobile_number.setError("Please enter your  mobile number");
            }else  if(str_mobile_number.length()!=10){
                mobile_number.requestFocus();
                mobile_number.setError("Mobile number length should be 10 digit");
            }/* else  if(str_dob.equalsIgnoreCase("")){
                dob.requestFocus();
                dob.setError("Please enter your date of birth");
            }*/else if(str_address.equalsIgnoreCase("")){
                address.requestFocus();
                address.setError("Please enter your address");
            }/*else if(str_weight.equalsIgnoreCase("")){
                weight.requestFocus();
                weight.setError("Please enter your weight");
            }else if(str_height.equalsIgnoreCase("")){
                height.requestFocus();
                height.setError("Please enter your height ");
            }*/ else if(str_password.equalsIgnoreCase("")){
                password.requestFocus();
                password.setError("Please enter your password");
            } else if(str_confirm_password.equalsIgnoreCase("")){
                confirm_password.requestFocus();
                confirm_password.setError("Please enter confirm password");
            }else if(!str_confirm_password.equalsIgnoreCase(str_password)){
                confirm_password.requestFocus();
                confirm_password.setError("password and confirm password not matching ");
            } else if(!isValidEmail(str_email)){
                email.requestFocus();
                email.setError("password and confirm password not matching");
            }
            /* else if(blood_group.getSelectedItem().toString().trim().equalsIgnoreCase("Select Blood Group")){
                Toast.makeText(this, "Please select blood group", Toast.LENGTH_LONG).show();
            } else if(gender.getSelectedItem().toString().trim().equalsIgnoreCase("Select Gender")){
                Toast.makeText(this, "Please Select Gender", Toast.LENGTH_LONG).show();
            }else if(gym_category.getSelectedItem().toString().trim().equalsIgnoreCase("Select Gym Category")){
                Toast.makeText(this, "Please select gym category", Toast.LENGTH_LONG).show();
            }else if(user_medical_history.getSelectedItem().toString().trim().equalsIgnoreCase("Select Medical History")){
                Toast.makeText(this, "Please select medical history", Toast.LENGTH_LONG).show();
            }*/
            else if(!GlobalItems.isInternetAvailable(RegistrationActivity.this)){
                Toast.makeText(this, R.string.check_internetConnection, Toast.LENGTH_LONG).show();
            } else{
                registerMember();
            } }
    private void registerMember() {
         random_Id=24;
        random_Id=random_Id+1;
        progressBarHolder.setVisibility(View.VISIBLE);
        String url ="http://www.printacheque.com/gymapp/api/user/update.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            object.put("gym_id","5");
            object.put("user_id",String.valueOf(random_Id));
            object.put("full_name",str_name);
            object.put("address", str_address);
            object.put("mobile_number",str_mobile_number);
            object.put("email",str_email);
            object.put("password",str_password);
            object.put("access_level","member");
            object.put("status","1");
            object.put("created",currentDateandTime);
//            object.put("gender",str_gender);
//            object.put("DOB",str_dob);
//            object.put("age",atr_age);
//            object.put("gym_category",str_GymCategory);
//            object.put("weight",str_weight);
//            object.put("height",str_height);
//            object.put("bloodgroup",str_bloodGroup);
//            object.put("medical_history",str_medicalHistory);
//            object.put("created",currentDateandTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            myResponse=response.getString("message");
                            preferances.setValue("name",str_name);
                            preferances.setValue("address",str_address);
                            preferances.setValue("mobile_number",str_mobile_number);
                            preferances.setValue("email",str_email);
                            Toast.makeText(RegistrationActivity.this, response.getString("message"),Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(intent);

                        } catch (JSONException e) {
                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarHolder.setVisibility(View.GONE);
                Toast.makeText(RegistrationActivity.this, "Member Already Exist",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri resultUri = result.getUri();
            img_camera_pic.setImageURI(resultUri);
            imageUrl = getPath(RegistrationActivity.this,resultUri).replaceAll(" ","%20");
        }
        }
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        Log.i("URI", uri + "");
        String result = uri + "";
        // DocumentProvider
        //  if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        if (isKitKat && (result.contains("media.documents"))) {
            String[] ary = result.split("/");
            int length = ary.length;
            String imgary = ary[length - 1];
            final String[] dat = imgary.split("%3A");
            final String docId = dat[1];
            final String type = dat[0];
            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
            }
            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{
                    dat[1]
            };
            return getDataColumn(context, contentUri, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);


                return cursor.getString(column_index);
            }


            *//*String[] mediaColumns = {MediaStore.Video.Media.SIZE};
            Cursor cursor = getContext().getContentResolver().query(videoUri, mediaColumns, null, null, null);
            cursor.moveToFirst();
            int sizeColInd = cursor.getColumnIndex(mediaColumns[0]);
            long fileSize = cursor.getLong(sizeColInd);
            cursor.close();*//*
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }*/
    public void date_click(View view) {
        final Calendar cldr = Calendar.getInstance();
        final Calendar today = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(RegistrationActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        str_dob=dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        int age = today.get(Calendar.YEAR) -year;
                        atr_age=String.valueOf(age);
                        dob.setText(str_dob);

                    }
                }, year, month, day);
        picker.show();
    }
}
