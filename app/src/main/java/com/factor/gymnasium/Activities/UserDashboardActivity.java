package com.factor.gymnasium.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.factor.gymnasium.Fragment.AboutUsFragment;
import com.factor.gymnasium.Fragment.AttandanceFragment;
import com.factor.gymnasium.Fragment.BackUpFragment;
import com.factor.gymnasium.Fragment.BookingFragment;
import com.factor.gymnasium.Fragment.ContactInfoFragment;
import com.factor.gymnasium.Fragment.Gym_informationFragment;
import com.factor.gymnasium.Fragment.HomeFragment;
import com.factor.gymnasium.Fragment.ImportantInformationFragment;
import com.factor.gymnasium.Fragment.MembershipInformationFragment;
import com.factor.gymnasium.Fragment.MoreFragment;
import com.factor.gymnasium.Fragment.MyProfileFragment;
import com.factor.gymnasium.Fragment.ReportFragment;
import com.factor.gymnasium.Fragment.Scanning;
import com.factor.gymnasium.Fragment.SchedulingFragment;
import com.factor.gymnasium.Fragment.SessionInfoFragment;
import com.factor.gymnasium.Fragment.SwitchFragment;
import com.factor.gymnasium.Fragment.ToolsFragment;
import com.factor.gymnasium.Fragment.UpgradeFragment;
import com.factor.gymnasium.Fragment.VitalDataFragment;
import com.factor.gymnasium.Globals.GlobalItems;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.factor.gymnasium.Globals.GlobalItems.MEMBER_BASE_URL;

public class UserDashboardActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    String currentVersion, latestVersion;

    String str_inTime="",str_outTime="",strDate="",member_id="",gym_id;
    String contents,str_gymName;
    ImageView notification;
    Fragment fragment = null;
    boolean b_home,b_personal,b_membership,b_attandance,b_equipment,b_booking,b_scheduling,b_workout,b_tools,b_gymInfo,b_more,b_report,b_qrCode,b_myprofile,b_vitaldata,b_importantInfo,b_contactInfo,b_session_info,b_aboutUs,b_upgrade,b_backup,b_switchGym;
    Dialog dialog;
    NavigationView navigationView;
    DrawerLayout drawer;
    BottomNavigationView bottom_navigationView;
    BottomNavigationMenuView menuView;
    BottomNavigationItemView itemView;
    SharedPreferenceUtils preferances;
   public static   TextView toolbar_title;

    @Override
    protected void onStart() {
        super.onStart();

        getVersion();

        if(!GlobalItems.isInternetAvailable(Objects.requireNonNull(UserDashboardActivity.this))){
            Toast.makeText(UserDashboardActivity.this,R.string.check_internetConnection,Toast.LENGTH_SHORT).show();
        }else{
            getGymTimeSessionInformation();
        }
    }

    FloatingActionButton fab;
    int a;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdashboard);
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo =  pm.getPackageInfo(this.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        currentVersion = pInfo.versionName;
        gym_id="5";
        preferances = SharedPreferenceUtils.getInstance(this);
        member_id=preferances.getStringValue("MEMBER_ID","");
        str_gymName=preferances.getStringValue("GYM_NAME","");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM", Locale.getDefault());
        strDate = sdf.format(new Date());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        notification=findViewById(R.id.notification);
        toolbar_title=(TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar_title.setText(R.string.app_name);
        loadHomeFragment();
           fab = (FloatingActionButton) findViewById(R.id.fab);
        notification.setOnClickListener(view -> {
            Intent intent=new Intent(UserDashboardActivity.this,NotificationActivity.class);
            startActivity(intent);
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new SchedulingFragment();
                loadFragment(fragment);
                b_scheduling=true;
                toolbar_title.setText(R.string.New_Bookings);

            }
        });

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottom_navigationView = findViewById(R.id.bottom_navigationView);
        bottom_navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        menuView = (BottomNavigationMenuView) bottom_navigationView.getChildAt(0);
        itemView = (BottomNavigationItemView) menuView.getChildAt(4);
        navigationView.setNavigationItemSelectedListener(this);
        bottom_navigationView.setItemIconTintList(null);
        navigationView.setItemIconTintList(null);
        View header=navigationView.getHeaderView(0);
        ImageView UserImage=header.findViewById(R.id.UserImage);
        TextView name = (TextView)header.findViewById(R.id.name);
        TextView email = (TextView)header.findViewById(R.id.email);
        RelativeLayout nav_relative=header.findViewById(R.id.nav_relative);
        nav_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.dashboard_fragment_container, new MyProfileFragment());
                transaction.addToBackStack(null);
                toolbar_title.setText(R.string.my_profile);
                transaction.commit();
                drawer.closeDrawers();
                b_personal=true;
            }
        });
        name.setText(preferances.getStringValue(GlobalItems.FULL_NAME,""));
        email.setText(preferances.getStringValue(GlobalItems.EMAIL_ID,""));
//        Glide.with(UserDashboardActivity.this).load(R.drawable.anjali).into(UserImage);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(UserDashboardActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    private void loadHomeFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.dashboard_fragment_container, new HomeFragment());
        transaction.addToBackStack(null);
        toolbar_title.setText(R.string.app_name);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else if(b_myprofile) {
            loadHomeFragment();
            toolbar_title.setText(R.string.app_name);
            b_myprofile=false;
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);
        } else if(b_vitaldata){
            loadHomeFragment();
            toolbar_title.setText(R.string.app_name);
            b_vitaldata=false;
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);
        }else if(b_scheduling){
            loadHomeFragment();
            toolbar_title.setText(R.string.app_name);
            b_scheduling=false;
        } else if(b_importantInfo) {
            loadHomeFragment();
            toolbar_title.setText(R.string.app_name);
            b_importantInfo=false;
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);

        } else if(b_membership) {
            loadHomeFragment();
            toolbar_title.setText(R.string.app_name);
            b_membership=false;
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);
        }  else if(b_gymInfo) {
            loadHomeFragment();
            b_gymInfo=false;
            toolbar_title.setText(R.string.app_name);
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);

        } else if(b_contactInfo) {
            loadHomeFragment();
            b_contactInfo=false;
            toolbar_title.setText(R.string.app_name);
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);

        } else if(b_session_info) {
            loadHomeFragment();
            b_session_info=false;
            toolbar_title.setText(R.string.app_name);
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);

        }  else if(b_tools) {
            loadHomeFragment();
            b_tools=false;
            toolbar_title.setText(R.string.app_name);
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);

        }  else if(b_aboutUs) {
            loadHomeFragment();
            b_aboutUs=false;
            toolbar_title.setText(R.string.app_name);
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);
        } else if(b_upgrade){
            loadHomeFragment();
            b_upgrade=false;
            toolbar_title.setText(R.string.app_name);
        } else if(b_backup){
            loadHomeFragment();
            b_backup=false;
            toolbar_title.setText(R.string.app_name);
        } else if(b_switchGym){
            loadHomeFragment();
            b_switchGym=false;
            toolbar_title.setText(R.string.app_name);
        }else if(b_booking){
            loadHomeFragment();
            b_switchGym=false;
            toolbar_title.setText(R.string.app_name);
        }else if(b_qrCode){
            loadHomeFragment();
            b_qrCode=false;
            toolbar_title.setText(R.string.app_name);
        }else if(b_report){
            loadHomeFragment();
            b_report=false;
            toolbar_title.setText(R.string.app_name);
        }else if(b_more){
            loadHomeFragment();
            b_more=false;
            toolbar_title.setText(R.string.app_name);
        } else if(b_equipment){
            loadHomeFragment();
            b_equipment=false;
            toolbar_title.setText(R.string.app_name);
        } else if(a==R.id.navmenu_home||bottom_navigationView.getSelectedItemId()==R.id.menu_home) {
            exitDialog();
        }
    }
    private void getGymTimeSessionInformation() {
        String url = MEMBER_BASE_URL+"Gym/read_one.php?gym_id="+gym_id;
        //Again creating the string request
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String a=jsonObject.getString("gym_name").replace("null","");
                            preferances.setValue("GYM_NAME",a);
                            String time_slot=jsonObject.getString("timing").replace("null","");
                            preferances.setValue("TIMESLOT",time_slot);
                            preferances.setValue("SESSION_DURATION",jsonObject.getString("session_duration").replace("null",""));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UserDashboardActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }


                        //Toast.makeText(LoginActivity.this, userid, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserDashboardActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        //  isLoading(false);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(UserDashboardActivity.this);

        requestQueue.add(jsonRequest);
    }
    private void getVersion() {
        String url = " https://castercrew.com/mobile_app/version";
        StringRequest jsonRequest = new StringRequest(com.android.volley.Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json = new JSONArray(response);
                    JSONObject jsonObject=json.getJSONObject(0);
                    latestVersion= jsonObject.getString("version");
                    String[] res = latestVersion.split("[.]", 1);

                    Log.i("version", String.valueOf(res));

                    if(!currentVersion.equalsIgnoreCase(latestVersion)) {
//                       showUpdateDialog();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("uid",uid);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonRequest);
    }
    private void showUpdateDialog(){
        dialog = new Dialog(UserDashboardActivity.this, R.style.AlertDialogCustom);
        dialog.setContentView(R.layout.custom_alert);
        dialog.setCancelable(false);
        TextView tv1 = dialog.findViewById(R.id.tv1);
        TextView tv2 = dialog.findViewById(R.id.tv2);
        tv2.setVisibility(View.VISIBLE);
        tv1.setTextSize(12f);
        Button b1 = dialog.findViewById(R.id.b1);
        b1.setText(getString(R.string.cancel));
        Button b2 = dialog.findViewById(R.id.b2);
        b2.setText(getString(R.string.Update));
        tv1.setText(R.string.want_update);
        tv2.setText(R.string.new_versionstatement);
        b1.setOnClickListener(v -> {
            dialog.dismiss();
        });
        b2.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.castercrewapp.castercrew&hl=en_IN")));
            dialog.dismiss();
            dialog.dismiss();

        });
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

















       /* final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.new_versionstatement);
        builder.setMessage(R.string.want_update);
        builder.setPositiveButton(R.string.Update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.castercrewapp.castercrew&hl=en_IN")));
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);
        dialog = builder.show();*/
    }

    private void exitDialog() {
        dialog = new Dialog(UserDashboardActivity.this, R.style.AlertDialogCustom);
        dialog.setContentView(R.layout.custom_alert);
        dialog.setCancelable(false);
        TextView tv1 = dialog.findViewById(R.id.tv1);
        tv1.setTextSize(12f);
        Button b1 = dialog.findViewById(R.id.b1);
        b1.setText(getString(R.string.cancel));
        Button b2 = dialog.findViewById(R.id.b2);
        b2.setText(getString(R.string.exit));
        tv1.setText(R.string.want_exit);
        b1.setOnClickListener(v -> {
            dialog.dismiss();
        });
        b2.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            dialog.dismiss();

        });
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navmenu_home:
                fragment = new HomeFragment();
                loadFragment(fragment);
                drawer.closeDrawers();
                toolbar_title.setText(R.string.app_name);
                return true;
            case R.id.my_profile:
                b_myprofile=true;
                fragment = new MyProfileFragment();
                loadFragment(fragment);
                drawer.closeDrawers();
                toolbar_title.setText(R.string.my_profile);
                return true;
            case R.id.vital_data:
                b_vitaldata=true;
                fragment = new VitalDataFragment();
                loadFragment(fragment);
                toolbar_title.setText(R.string.vital_data);
                drawer.closeDrawers();
                return true;
            case R.id.important_information:
                b_importantInfo=true;
                fragment = new ImportantInformationFragment();
                loadFragment(fragment);
                toolbar_title.setText(R.string.important_information);
                drawer.closeDrawers();
                return true;
                case R.id.membership_info:
                b_membership=true;
                fragment = new MembershipInformationFragment();
                loadFragment(fragment);
                    toolbar_title.setText(R.string.membership_info);
                drawer.closeDrawers();
                return true;
                case R.id.gym_info:
                b_gymInfo=true;
                fragment = new Gym_informationFragment();
                    loadFragment(fragment);
                    toolbar_title.setText(R.string.gym_info);
                drawer.closeDrawers();
                return true;
             /*   case R.id.scanning:
                b_scanning=true;
                fragment = new Scanning();
                    loadFragment(fragment);
                    toolbar.setTitle("Scanning");
                drawer.closeDrawers();
                return true;*/
                case R.id.contact_info:
                  b_contactInfo=true;
                fragment = new ContactInfoFragment();
                loadFragment(fragment);
                    toolbar_title.setText(R.string.contact_info);
                   drawer.closeDrawers();
                  return true;
                  case R.id.session_info:
                     b_session_info=true;
                 fragment = new SessionInfoFragment();
                 loadFragment(fragment);
                      toolbar_title.setText(R.string.session_info);
                drawer.closeDrawers();
                return true;

            case R.id.about_us:
                b_aboutUs=true;
                fragment = new AboutUsFragment();
                loadFragment(fragment);
                toolbar_title.setText(R.string.about_us);
                drawer.closeDrawers();
                return true;
            case R.id.upgrade:
                b_upgrade=true;
                fragment = new UpgradeFragment();
                loadFragment(fragment);
                toolbar_title.setText(R.string.upgrade);
                drawer.closeDrawers();
                return true;
            case R.id.backup:
                b_backup=true;
                fragment = new Scanning();
//                fragment = new BackUpFragment();
                loadFragment(fragment);
                toolbar_title.setText(R.string.backup);
                drawer.closeDrawers();
                return true;
            case R.id.switch_gym:
                b_switchGym=true;
                fragment = new SwitchFragment();
                loadFragment(fragment);
                toolbar_title.setText(R.string.switch_gym);
                drawer.closeDrawers();
                return true;
            case R.id.equipment_facilities:
                b_equipment=true;
                fragment = new ToolsFragment();
                loadFragment(fragment);
                toolbar_title.setText(R.string.equipment_facilities);
                drawer.closeDrawers();
                return true;
                case R.id.log_out:
                logoutDialog();
            default:
                break;
        }
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutDialog() {
        dialog = new Dialog(UserDashboardActivity.this, R.style.AlertDialogCustom);
        dialog.setContentView(R.layout.custom_alert);
        dialog.setCancelable(false);
        TextView tv1 = dialog.findViewById(R.id.tv1);
        tv1.setTextSize(12f);
        Button b1 = dialog.findViewById(R.id.b1);
        b1.setText(getString(R.string.yes));
        Button b2 = dialog.findViewById(R.id.b2);
        b2.setText(getString(R.string.no));
        tv1.setText(R.string.logout);
        b1.setOnClickListener(v -> {
            if (!preferances.getStringValue("MEMBER_ID","").equalsIgnoreCase("")) {
                preferances.setValue("MEMBER_ID", "");
                Intent normalIntent = new Intent(UserDashboardActivity.this, SliderActivity.class);
                startActivity(normalIntent);
                dialog.dismiss();
            }
        });
        b2.setOnClickListener(v -> {
            dialog.dismiss();
        });
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.menu_home:
                    toolbar_title.setText(R.string.app_name);
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.booking:
                    b_booking=true;
                    toolbar_title.setText(R.string.booking);
                    fragment = new BookingFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.qr_code:
                    scanCode();
                    b_qrCode=true;
                    return true;
                 /*   b_workout=true;
                    toolbar.setTitle(R.string.qr_code);
                    fragment = new WorkoutFragment();
                    loadFragment(fragment);
                    return true;*/
                case R.id.reports:
                    b_report=true;
                    toolbar_title.setText(R.string.reports);
                    fragment = new ReportFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.more:
                    b_more=true;
                    toolbar_title.setText(R.string.more);
                    fragment = new MoreFragment();
                    loadFragment(fragment);
                    return true;

            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.dashboard_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
private void  scanCode(){
    IntentIntegrator integrator = new IntentIntegrator(UserDashboardActivity.this);
    integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
}
@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
             contents = result.getContents();
            if (contents != null && contents.equalsIgnoreCase(str_gymName)) {
                showDialog(R.string.result_succeeded, result.toString());
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.create();
                builder.setMessage(R.string.result_failed);
                builder.setMessage(R.string.result_failed_why);
                builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        }
    }

    private void attandanceInTimeVerification(String str_inTime) {
//        progressBarHolder.setVisibility(View.VISIBLE);
        String url =MEMBER_BASE_URL+"attendance/create.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            object.put("gym_id",gym_id);
            object.put("user_id",member_id);
            object.put("attendance_date",strDate);
            object.put("in_time",str_inTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            Toast.makeText(UserDashboardActivity.this, response.getString("message"),Toast.LENGTH_LONG).show();
                            fragment = new AttandanceFragment();
                            loadFragment(fragment);
//                            dialog.dismiss();
                            toolbar_title.setText("Attandance");
                            b_attandance=true;
                            preferances.setValue("PrefInTime",strDate+ " " +str_inTime);
                        } catch (JSONException e) {
//                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(UserDashboardActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressBarHolder.setVisibility(View.GONE);
                Toast.makeText(UserDashboardActivity.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(jsonObjectRequest);

    }
    private void showDialog(int title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
   if(str_inTime.equals("")){
       SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
       str_inTime = sdf.format(new Date());
       attandanceInTimeVerification(str_inTime);
   }else{
       SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
       str_outTime = sdf.format(new Date());
       attandanceOutTimeVerification(str_outTime);
   }
   dialog.dismiss();
                    }
                });

        builder.show();
    }

    private void attandanceOutTimeVerification(String str_outTime) {
        String url =MEMBER_BASE_URL+"attendance/update.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            object.put("user_id",member_id);
            object.put("attendance_date",strDate);
            object.put("out_time",str_outTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        progressBarHolder.setVisibility(View.GONE);
                        try {
                            Toast.makeText(UserDashboardActivity.this, response.getString("message"),Toast.LENGTH_LONG).show();
                            fragment = new AttandanceFragment();
                            loadFragment(fragment);
//                            dialog.dismiss();
                            toolbar_title.setText("Attandance");
                            b_attandance=true;
                            preferances.setValue("PrefOutTime",strDate + " " +str_outTime);

                        } catch (JSONException e) {
//                            progressBarHolder.setVisibility(View.GONE);
                            Toast.makeText(UserDashboardActivity.this, e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressBarHolder.setVisibility(View.GONE);
                Toast.makeText(UserDashboardActivity.this,  error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(jsonObjectRequest);


    }
}