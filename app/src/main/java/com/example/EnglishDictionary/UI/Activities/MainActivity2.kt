package com.example.EnglishDictionary.UI.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.EnglishDictionary.UI.Fragments.FavouriteMenuFragment
import com.example.EnglishDictionary.R
import com.example.EnglishDictionary.UI.Fragments.CachedWordDetailFragment
import com.example.EnglishDictionary.UI.Fragments.RecentMenuFragment
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(RecentFragToolBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val fragm = intent.getStringExtra("fragmentRecent")

        val activityTitle = this.getText(if (fragm=="RecFrag") R.string.recentString else R.string.FavoriteString)

        val selectedFragment = when(fragm){
            "RecFrag" -> RecentMenuFragment()
            "FavFrag" -> FavouriteMenuFragment()
            else -> RecentMenuFragment()
        }

        this.title = activityTitle
        supportFragmentManager.beginTransaction().replace(R.id.SelectedFragment, selectedFragment).commit()
    }


}