package com.example.maheshbhattarai.sqlite_database_demo

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.maheshbhattarai.sqlite_database_demo.Utill.Apprefence
import com.example.maheshbhattarai.sqlite_database_demo.Utill.OnParsePath
import com.example.maheshbhattarai.sqlite_database_demo.Utill.OnReadPath
import com.example.maheshbhattarai.sqlite_database_demo.fragment.*
import com.google.android.gms.maps.model.PolylineOptions
import org.json.JSONObject

class JobSeekerActivity : AppCompatActivity(), OnParsePath, OnReadPath,NavigationView.OnNavigationItemSelectedListener,ReportFragment.OnFragmentInteractionListener,SeekerPostJobPageFragment.OnFragmentInteractionListener,CompeletedFragment.OnFragmentInteractionListener,HomeJobFragment.OnFragmentInteractionListener,HomeFragment.OnFragmentInteractionListener, AllFragment.OnFragmentInteractionListener, PostJobPageFragment.OnFragmentInteractionListener {
    override fun onGetParsePath(polyLineOptions: PolylineOptions?) {

    }

    override fun onGetPath(s: JSONObject) {
    }

    var toolbar : Toolbar ?=null

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_seeker)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        mContext = this
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        setDefaultFragment()
    }

    fun setDefaultFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.frame_jobSeeker_c, HomeJobFragment()).commit()
    }

    override fun onFragmentInteraction(uri: String) {
        toolbar?.setTitle(uri)
    }
    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount > 0) {

         exitApp()
        }else{ supportFragmentManager.popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.job_seeker, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        var fragmentManger = supportFragmentManager
        var ft = fragmentManger.beginTransaction()

        if (id == R.id.nav_home) {
            ft.addToBackStack(null).replace(R.id.frame_jobSeeker_c, HomeJobFragment()).commit()

            // Handle the camera action
        } else if (id == R.id.nav_PostedJob1) {
            ft.addToBackStack(null).replace(R.id.frame_jobSeeker_c, SeekerPostJobPageFragment()).commit()

        }
//        else if (id == R.id.nav_Applied1) {
//            ft.addToBackStack(null).replace(R.id.frame_jobSeeker_c, AllFragment()).commit()
//
//        }

        else if (id == R.id.nav_completed1) {
            ft.addToBackStack(null).replace(R.id.frame_jobSeeker_c, CompeletedFragment()).commit()

        } else if (id == R.id.nav_logout1) {
            logoutApp()
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    private fun logoutApp() {
        AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you want to logout ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()

                    var pDialog= ProgressDialog(mContext)
                    pDialog.setMessage("please wait..")
                    pDialog.setCancelable(false)
                    pDialog.setCanceledOnTouchOutside(false)
                    try {
                        pDialog.show()
                    } catch (e: Exception) {
                    }
                    var handler = Handler()
                    handler.postDelayed(Runnable {
                        pDialog.dismiss()
                        startActivity(Intent(mContext, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        finish()
                        Apprefence.setUserId(mContext, "")
                    }, 2000)

                })
                .setNegativeButton("No", null)
                .show()
    }
    private fun exitApp() {
        AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.app_name))
                .setMessage("Do you want to exit App?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which -> finish() })
                .setNegativeButton("No", null)
                .show()
    }

}
