package com.translator.app.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.translator.app.R
import com.translator.app.models.User
import com.translator.app.utils.FileManager
import com.translator.app.utils.Prefs
import com.translator.app.utils.Utils
import androidx.core.content.FileProvider
import java.io.File

class ProfileActivity : AppCompatActivity() {

    companion object {
        fun beginActivity(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, ProfileActivity::class.java))
        }
    }

    private lateinit var imgUser: ImageView
    private lateinit var edtName: EditText
    private lateinit var edtHeight: EditText
    private lateinit var edtWeight: EditText
    private lateinit var edtMedConds: EditText
    private lateinit var btnSave: Button
    private val REQ_IMAGE_PERMS = 788
    private val user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews()
        setViews()
    }

    private fun initViews() {
        imgUser = findViewById(R.id.img_user)
        edtName = findViewById(R.id.edt_name)
        edtHeight = findViewById(R.id.edt_height)
        edtWeight = findViewById(R.id.edt_weight)
        edtMedConds = findViewById(R.id.edt_med_conds)
        btnSave = findViewById(R.id.btn_save)
    }

    @SuppressLint("NewApi")
    private fun setViews() {
        btnSave.setOnClickListener {
            if (validInput()) {
                setValues()
                Prefs.user = user
                MainActivity.beginActivity(this)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
        imgUser.setOnClickListener {
            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ), REQ_IMAGE_PERMS
                    )
                } else {
                    showImageDialogue()
                }
            } else {
                showImageDialogue()
            }
        }
    }

    private fun setValues() {
        user.name = edtName.text.toString().trim()
        user.height = edtHeight.text.toString().trim().toDouble()
        user.weight = edtWeight.text.toString().trim().toDouble()
        user.medicalConditions = edtMedConds.text.toString().trim()
    }

    private fun validInput(): Boolean {
        if (!TextUtils.isEmpty(edtName.text.toString().trim()))
            if (!TextUtils.isEmpty(edtHeight.text.toString().trim()))
                if (!TextUtils.isEmpty(edtWeight.text.toString().trim()))
                    if (!TextUtils.isEmpty(edtMedConds.text.toString().trim()))
                        return true
                    else
                        Utils.showToast(getString(R.string.plz_enter_med_conds))
                else
                    Utils.showToast(getString(R.string.plz_enter_weight))
            else
                Utils.showToast(getString(R.string.plz_enter_height))
        else
            Utils.showToast(getString(R.string.plz_enter_name))
        return false
    }

    private fun showImageDialogue() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose From Gallery", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Option")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                getCameraImage()
                dialog.dismiss()
            } else if (options[item] == "Choose From Gallery") {
                getGalleryImage()
                dialog.dismiss()
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun getCameraImage() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = FileManager.getProfilePicFile()
        val photoURI = FileProvider.getUriForFile(
            this,
            applicationContext.packageName + ".fileprovider",
            file
        )
        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        takePicture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(takePicture, 0)

    }

    private fun getGalleryImage() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickPhoto.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(pickPhoto, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)

        val fileToWrite = FileManager.getProfilePicFile()

        when (requestCode) {
            //CAMERA
            0 -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage = imageReturnedIntent!!.data
                imgUser.setImageURI(selectedImage)
                user.image = selectedImage.toString()
            }
            //GALLERY
            1 -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage = imageReturnedIntent!!.data
                imgUser.setImageURI(selectedImage)
                val fileToCopy = File(selectedImage.toString())
                fileToCopy.copyTo(fileToWrite, true)
                user.image = fileToCopy.absolutePath.toString()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_IMAGE_PERMS && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            && grantResults[1] == PackageManager.PERMISSION_GRANTED
        ) {
            FileManager.createDirectory()
            showImageDialogue()
        } else {
            Utils.showToast(getString(R.string.cannot_select_image))
        }
    }

}
