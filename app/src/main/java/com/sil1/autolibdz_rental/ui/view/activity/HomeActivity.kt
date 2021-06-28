package com.sil1.autolibdz_rental.ui.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.sil1.autolibdz_rental.R
import com.sil1.autolibdz_rental.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity(),MyDrawerController{

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private lateinit var  drawerLayout: DrawerLayout
    private lateinit var menuButton: FloatingActionButton
    protected val REQUEST_CHECK_SETTINGS = 0x1

    companion object {
        // The code that denotes the request for location permissions
        private const val REQUEST_CODE_LOCATION = 1
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //status bar
        val window: Window = this@HomeActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(
            this@HomeActivity,
            R.color.palette_yellow
        )

        //for stripe sdk, check location permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            // REQUEST_CODE_LOCATION should be defined on your app level
            ActivityCompat.requestPermissions(this, permissions,
                HomeActivity.REQUEST_CODE_LOCATION
            )
        }

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        menuButton = binding.appBarHome.menuButton
        drawerLayout = binding.drawerLayout

        if (!Places.isInitialized()) {
            Places.initialize( applicationContext, getString(R.string.api_key));
        }
        Places.createClient(this)

        menuButton.setOnClickListener{
            // If the navigation drawer is not open then open it, if its already open then close it.
            if(!drawerLayout.isDrawerOpen(Gravity.START)) drawerLayout.openDrawer(Gravity.START);
            else drawerLayout.closeDrawer(Gravity.END);
        }

        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_profil, R.id.nav_history, R.id.nav_transaction, R.id.nav_reclamation, R.id.nav_assistance
            ), drawerLayout
        )
        navView.setupWithNavController(navController)
    }


    fun createLocationRequest() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = locationRequest?.let {
            LocationSettingsRequest.Builder()
                .addLocationRequest(it)
        }
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder?.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(this@HomeActivity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setDrawer_Locked() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        menuButton.visibility = View.GONE
    }

    override fun setDrawer_UnLocked() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        menuButton.visibility = View.VISIBLE

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == HomeActivity.REQUEST_CODE_LOCATION && grantResults.isNotEmpty()
            && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            throw RuntimeException("Location services are required in order to " + "connect to a reader.")
        }
    }

}