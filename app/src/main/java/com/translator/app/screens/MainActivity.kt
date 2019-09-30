package com.translator.app.screens

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.translator.app.R
import com.translator.app.utils.CircleTransform
import com.translator.app.utils.FileManager

/**
 * This Activity is Landing screen for User.
 * This screen consists of three section :
 *  1. User Profile Flow
 *  2. Translate / Add Medicine Flow
 *  3. Medicine List / Edit Medicine flow
 */
class MainActivity : AppCompatActivity() {

    companion object {
        fun beginActivity(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    private lateinit var linTranslate: LinearLayout
    private lateinit var linMyMedicines: LinearLayout
    private lateinit var relProfile: RelativeLayout
    private lateinit var imgUser: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setViews()
        loadUserImage()
    }

    /**
     * This function initializes all the views in screen
     */
    private fun initViews() {
        linTranslate = findViewById(R.id.lin_translate)
        linMyMedicines = findViewById(R.id.lin_my_medicines)
        relProfile = findViewById(R.id.rel_profile)
        imgUser = findViewById(R.id.img_user)
    }


    /**
     * This function set the listeners to all the views in screen.
     */
    private fun setViews() {
        linTranslate.setOnClickListener {
            TranslateActivity.beginActivity(this)
        }
        linMyMedicines.setOnClickListener {
            MedicineListActivity.beginActivity(this)
        }
        relProfile.setOnClickListener {
            EditProfileActivity.beginActivityForResult(this)
        }
    }


    /**
     * It loads the image from App's directory to ImageView
     */
    private fun loadUserImage() {
        //Load image from memory
        Picasso.get().load(FileManager.getProfilePicFile())
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .transform(CircleTransform())
            .into(imgUser)
    }

    /**
     * This is a callback function to check the result from further opened activity,i.e, Edit Profile Activity
     * If the result is 
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EditProfileActivity.REQ_EDIT_PROFILE && resultCode == Activity.RESULT_OK) {
            loadUserImage()
        }
    }

}
