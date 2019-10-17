package com.example.navigationarchtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var stackSelectedTabs: ArrayList<String> = ArrayList()
    var fromBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            .commit()


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
                    val intent = Intent(this, PostActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener false
                }
            }
            true
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
