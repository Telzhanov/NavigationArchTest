package com.example.navigationarchtest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var stackSelectedTabs: ArrayList<String> = ArrayList()
    var fromBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                Log.d("fcm token", token.toString())
                Toast.makeText(baseContext, "fcm:token ${token.toString()}", Toast.LENGTH_SHORT).show()
            })
        stackSelectedTabs.add("nav1")
        val nav1 = NavHostFragment.create(R.navigation.nav_graph1)
        val nav2 = NavHostFragment.create(R.navigation.nav_grapg2)
        val nav3 = NavHostFragment.create(R.navigation.nav_graph3)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, nav1, "nav1")
            .add(R.id.container, nav2, "nav2")
            .add(R.id.container, nav3, "nav3")
            .attach(nav1)
            .detach(nav2)
            .detach(nav3)
            .commitNow()

        navigation_bar.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.blank_fragment -> {
                    if (fromBackPressed) {
                        stackSelectedTabs.remove(stackSelectedTabs.last())
                        fromBackPressed = false
                    }
                    else {
                        if (!stackSelectedTabs.contains("nav1")) {
                            stackSelectedTabs.add("nav1")
                        }
                        else {
                            if (stackSelectedTabs.last() == "nav1") {
                                ((supportFragmentManager.findFragmentByTag("nav1") as NavHostFragment).findNavController().popBackStack(R.id.blank_fragment, false))
                                return@setOnNavigationItemSelectedListener true
                            }
                            else {
                                stackSelectedTabs.remove("nav1")
                                stackSelectedTabs.add("nav1")
                            }
                        }
                    }
                    supportFragmentManager.beginTransaction()
                        .attach(nav1)
                        .detach(nav2)
                        .detach(nav3)
                        .commit()
                }
                R.id.profile_fragment -> {
                    if (fromBackPressed) {
                        stackSelectedTabs.remove(stackSelectedTabs.last())
                        fromBackPressed = false
                    }
                    else {
                        if (!stackSelectedTabs.contains("nav2")) {
                            stackSelectedTabs.add("nav2")
                        }
                        else {
                            if (stackSelectedTabs.last() == "nav2") {
                                ((supportFragmentManager.findFragmentByTag("nav2") as NavHostFragment).findNavController().popBackStack(R.id.profile_fragment, false))
                                return@setOnNavigationItemSelectedListener true
                            }
                            else {
                                stackSelectedTabs.remove("nav2")
                                stackSelectedTabs.add("nav2")
                            }
                        }
                    }
                    supportFragmentManager.beginTransaction()
                        .attach(nav2)
                        .detach(nav1)
                        .detach(nav3)
                        .commit()
                }
                R.id.setting_fragment -> {
                    if (fromBackPressed) {
                        stackSelectedTabs.remove(stackSelectedTabs.last())
                        fromBackPressed = false
                    } else {
                        if (!stackSelectedTabs.contains("nav3")) {
                            stackSelectedTabs.add("nav3")
                        }
                        else {
                            if (stackSelectedTabs.last() == "nav3") {
                                ((supportFragmentManager.findFragmentByTag("nav3") as NavHostFragment).findNavController().popBackStack(R.id.setting_fragment, false))
                                return@setOnNavigationItemSelectedListener true
                            }
                            else {
                                stackSelectedTabs.remove("nav3")
                                stackSelectedTabs.add("nav3")
                            }
                        }
                    }

                    supportFragmentManager.beginTransaction()
                        .attach(nav3)
                        .detach(nav2)
                        .detach(nav1)
                        .commit()
                }
                R.id.post_activity -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener false
                }
            }
            true
        }
        if (intent?.extras?.getString("deeplink") != null) {
            intent.data = Uri.parse(intent?.extras?.getString("deeplink"))
            if ((supportFragmentManager.findFragmentByTag("nav1") as NavHostFragment).findNavController().handleDeepLink(intent)) {
                stackSelectedTabs.clear()
                navigation_bar.selectedItemId = R.id.blank_fragment
            }

            if ((supportFragmentManager.findFragmentByTag("nav2") as NavHostFragment).findNavController().handleDeepLink(intent)) {
                stackSelectedTabs.clear()
                navigation_bar.selectedItemId = R.id.profile_fragment
            }

            if ((supportFragmentManager.findFragmentByTag("nav3") as NavHostFragment).findNavController().handleDeepLink(intent)) {
                stackSelectedTabs.clear()
                navigation_bar.selectedItemId = R.id.setting_fragment
            }
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if ((supportFragmentManager.findFragmentByTag("nav1") as NavHostFragment).findNavController().handleDeepLink(intent)) {
            navigation_bar.selectedItemId = R.id.blank_fragment
        }

        if ((supportFragmentManager.findFragmentByTag("nav2") as NavHostFragment).findNavController().handleDeepLink(intent)) {
            navigation_bar.selectedItemId = R.id.profile_fragment
        }

        if ((supportFragmentManager.findFragmentByTag("nav3") as NavHostFragment).findNavController().handleDeepLink(intent)) {
            navigation_bar.selectedItemId = R.id.setting_fragment
        }
    }

    override fun onBackPressed() {
        if (!(supportFragmentManager.findFragmentByTag(stackSelectedTabs.last()) as NavHostFragment).findNavController().navigateUp()) {
            if (stackSelectedTabs.size == 1) {
                super.onBackPressed()
            }
            else {
                fromBackPressed = true
                when(stackSelectedTabs[stackSelectedTabs.lastIndex - 1]) {
                    "nav1" -> {
                        navigation_bar.selectedItemId = R.id.blank_fragment
                    }
                    "nav2" -> {
                        navigation_bar.selectedItemId = R.id.profile_fragment
                    }
                    "nav3" -> {
                        navigation_bar.selectedItemId = R.id.setting_fragment
                    }
                }
            }
        }
    }
}
