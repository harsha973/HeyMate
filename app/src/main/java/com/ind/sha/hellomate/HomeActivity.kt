package com.ind.sha.hellomate

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Created by Sree on 10/28/2017.
 */
class HomeActivity: BaseActivity() {

    private var centerButtonClicked = false
    private var causeCrash = false
    private var logoutCrash = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottom_button.setOnClickListener({
            causeCrash = centerButtonClicked
            Crashlytics.log("Bottom button clicked")
        })

        center_button.setOnClickListener({
            centerButtonClicked = true
            causeCrash = false
            Crashlytics.log("Center button clicked")
        })

        top_button.setOnClickListener({
            Crashlytics.log("Top button clicked")
            if(causeCrash){
                throw RuntimeException("U can't find me")
            }
            centerButtonClicked = false
            causeCrash = false
        })
    }

    override fun onStop() {
        Crashlytics.log(1, "Life cycle", "On stop")
        logoutCrash = true
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
             R.id.menu_logout -> {
                 Crashlytics.log("Menu selected")
                 if(logoutCrash){
                     throw RuntimeException("U can't find me")
                 } else {
                     finish()
                 }
                 return true
             }
        }
        return super.onOptionsItemSelected(item)
    }
}