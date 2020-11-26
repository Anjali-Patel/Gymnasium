package com.factor.gymnasium.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.factor.gymnasium.Fragment.SchedulingFragment;
import com.factor.gymnasium.Fragment.SessionInfoFragment;
import com.factor.gymnasium.Fragment.SwitchFragment;
import com.factor.gymnasium.Fragment.ToolsFragment;
import com.factor.gymnasium.Fragment.UpgradeFragment;
import com.factor.gymnasium.Fragment.VitalDataFragment;
import com.factor.gymnasium.Globals.SharedPreferenceUtils;
import com.factor.gymnasium.R;
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

public class UserDashboardActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    Fragment fragment = null;
    boolean b_home,b_personal,b_membership,b_attandance,b_equipment,b_booking,b_scheduling,b_workout,b_tools,b_gymInfo,b_more,b_report,b_qrCode,b_myprofile,b_vitaldata,b_importantInfo,b_contactInfo,b_session_info,b_aboutUs,b_upgrade,b_backup,b_switchGym;
    Dialog dialog;
    NavigationView navigationView;
    DrawerLayout drawer;
    BottomNavigationView bottom_navigationView;
    BottomNavigationMenuView menuView;
    BottomNavigationItemView itemView;
    SharedPreferenceUtils preferances;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdashboard);
        preferances = SharedPreferenceUtils.getInstance(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("Gymnasium");
        loadHomeFragment();
          FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new SchedulingFragment();
                loadFragment(fragment);
                b_scheduling=true;
                toolbar.setTitle(R.string.New_Bookings);

            }
        });
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottom_navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigationView);
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
                toolbar.setTitle(R.string.personal_information);
                transaction.commit();
                drawer.closeDrawers();
                b_personal=true;

            }
        });
        Intent intent=getIntent();
        name.setText(intent.getStringExtra("name"));
        email.setText("anjalics14.academic@gmail.com");
//        Glide.with(UserDashboardActivity.this).load(R.drawable.anjali).into(UserImage);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(UserDashboardActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    private void loadHomeFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.dashboard_fragment_container, new HomeFragment());
        transaction.addToBackStack(null);
        toolbar.setTitle(R.string.app_name);
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
            toolbar.setTitle(R.string.app_name);
            b_myprofile=false;
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);
        } else if(b_vitaldata){
            loadHomeFragment();
            toolbar.setTitle("Gymnasium");
            b_vitaldata=false;
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);
        }else if(b_scheduling){
            loadHomeFragment();
            toolbar.setTitle("Gymnasium");
            b_scheduling=false;
        } else if(b_importantInfo) {
            loadHomeFragment();
            toolbar.setTitle("Gymnasium");
            b_importantInfo=false;
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);

        } else if(b_membership) {
            loadHomeFragment();
            toolbar.setTitle("Gymnasium");
            b_membership=false;
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);
        }  else if(b_gymInfo) {
            loadHomeFragment();
            b_gymInfo=false;
            toolbar.setTitle("Gymnasium");
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);

        } else if(b_contactInfo) {
            loadHomeFragment();
            b_contactInfo=false;
            toolbar.setTitle("Gymnasium");
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);

        } else if(b_session_info) {
            loadHomeFragment();
            b_session_info=false;
            toolbar.setTitle("Gymnasium");
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);

        }  else if(b_tools) {
            loadHomeFragment();
            b_tools=false;
            toolbar.setTitle("Gymnasium");
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);

        }  else if(b_aboutUs) {
            loadHomeFragment();
            b_aboutUs=false;
            toolbar.setTitle("Gymnasium");
//            bottom_navigationView.setSelectedItemId(R.id.nav_home);
        } else if(b_upgrade){
            loadHomeFragment();
            b_upgrade=false;
            toolbar.setTitle("Gymnasium");
        } else if(b_backup){
            loadHomeFragment();
            b_backup=false;
            toolbar.setTitle("Gymnasium");
        } else if(b_switchGym){
            loadHomeFragment();
            b_switchGym=false;
            toolbar.setTitle("Gymnasium");
        }else if(b_booking){
            loadHomeFragment();
            b_switchGym=false;
            toolbar.setTitle("Gymnasium");
        }else if(b_qrCode){
            loadHomeFragment();
            b_qrCode=false;
            toolbar.setTitle("Gymnasium");
        }else if(b_report){
            loadHomeFragment();
            b_report=false;
            toolbar.setTitle("Gymnasium");
        }else if(b_more){
            loadHomeFragment();
            b_more=false;
            toolbar.setTitle("Gymnasium");
        } else if(b_equipment){
            loadHomeFragment();
            b_equipment=false;
            toolbar.setTitle("Gymnasium");
        }
        else if(b_home) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.create();
            builder.setMessage(R.string.want_exit);
            builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                }
            });
            builder.show();
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                b_home=true;
                fragment = new HomeFragment();
                loadFragment(fragment);
                drawer.closeDrawers();
                toolbar.setTitle(R.string.app_name);
                return true;
            case R.id.my_profile:
                b_myprofile=true;
                fragment = new MyProfileFragment();
                loadFragment(fragment);
                drawer.closeDrawers();
                toolbar.setTitle(R.string.my_profile);
                return true;
            case R.id.vital_data:
                b_vitaldata=true;
                fragment = new VitalDataFragment();
                loadFragment(fragment);
                toolbar.setTitle(R.string.vital_data);
                drawer.closeDrawers();
                return true;
            case R.id.important_information:
                b_importantInfo=true;
                fragment = new ImportantInformationFragment();
                loadFragment(fragment);
                toolbar.setTitle(R.string.important_information);
                drawer.closeDrawers();
                return true;
                case R.id.membership_info:
                b_membership=true;
                fragment = new MembershipInformationFragment();
                loadFragment(fragment);
                toolbar.setTitle(R.string.membership_info);
                drawer.closeDrawers();
                return true;
                case R.id.gym_info:
                b_gymInfo=true;
                fragment = new Gym_informationFragment();
                    loadFragment(fragment);
                    toolbar.setTitle(R.string.gym_info);
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
                toolbar.setTitle(R.string.contact_info);
                   drawer.closeDrawers();
                  return true;
                  case R.id.session_info:
                     b_session_info=true;
                 fragment = new SessionInfoFragment();
                 loadFragment(fragment);
                toolbar.setTitle(R.string.session_info);
                drawer.closeDrawers();
                return true;

            case R.id.about_us:
                b_aboutUs=true;
                fragment = new AboutUsFragment();
                loadFragment(fragment);
                toolbar.setTitle(R.string.about_us);
                drawer.closeDrawers();
                return true;
            case R.id.upgrade:
                b_upgrade=true;
                fragment = new UpgradeFragment();
                loadFragment(fragment);
                toolbar.setTitle(R.string.upgrade);
                drawer.closeDrawers();
                return true;
            case R.id.backup:
                b_backup=true;
                fragment = new BackUpFragment();
                loadFragment(fragment);
                toolbar.setTitle(R.string.backup);
                drawer.closeDrawers();
                return true;
            case R.id.switch_gym:
                b_switchGym=true;
                fragment = new SwitchFragment();
                loadFragment(fragment);
                toolbar.setTitle(R.string.switch_gym);
                drawer.closeDrawers();
                return true;
            case R.id.equipment_facilities:
                b_equipment=true;
                fragment = new ToolsFragment();
                loadFragment(fragment);
                toolbar.setTitle(R.string.equipment_facilities);
                drawer.closeDrawers();
                return true;
                case R.id.log_out:
logoutDialog();
             /*   final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.logout);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!preferances.getStringValue("MEMBER_ID","").equalsIgnoreCase("")) {
                            preferances.setValue("MEMBER_ID", "");
                            Intent normalIntent = new Intent(UserDashboardActivity.this, MainActivity.class);
                            startActivity(normalIntent);
                            dialog.dismiss();
                        }
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setCancelable(false);
                dialog = builder.show();*/
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
                Intent normalIntent = new Intent(UserDashboardActivity.this, MainActivity.class);
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
                    b_home=true;
                    toolbar.setTitle(R.string.app_name);
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.booking:
                    b_booking=true;
                    toolbar.setTitle(R.string.booking);
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
                    toolbar.setTitle(R.string.reports);
                    fragment = new ReportFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.more:
                    b_more=true;
                    toolbar.setTitle(R.string.more);
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
        IntentResult result =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            String contents = result.getContents();
            if (contents != null) {
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
    private void showDialog(int title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fragment = new AttandanceFragment();
                        loadFragment(fragment);
                        dialog.dismiss();
                        toolbar.setTitle("Attandance");
                        b_attandance=true;
                    }
                });

        builder.show();
    }
}