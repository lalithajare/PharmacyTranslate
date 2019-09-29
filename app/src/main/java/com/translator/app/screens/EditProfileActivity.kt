package com.translator.app.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.translator.app.utils.Prefs
import com.translator.app.utils.Utils
import android.provider.MediaStore
import android.widget.ImageView
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import com.translator.app.R
import com.translator.app.utils.FileManager


class EditProfileActivity : AppCompatActivity() {

    private val TAG = EditProfileActivity::class.java.simpleName

    companion object {
        val REQ_EDIT_PROFILE = 342
        fun beginActivityForResult(activity: AppCompatActivity) {
            activity.startActivityForResult(
                Intent(activity, EditProfileActivity::class.java),
                REQ_EDIT_PROFILE
            )
        }
    }

    private val REQ_IMAGE_PERMS = 788

    private lateinit var imgUser: ImageView
    private lateinit var edtName: EditText
    private lateinit var edtHeight: EditText
    private lateinit var edtWeight: EditText
    private lateinit var edtMedConds: EditText
    private lateinit var btnUpdate: Button

    private val user = Prefs.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        initViews()
        setViews()
        setData()
    }

    private fun initViews() {
        imgUser = findViewById(R.id.img_user)
        edtName = findViewById(R.id.edt_name)
        edtHeight = findViewById(R.id.edt_height)
        edtWeight = findViewById(R.id.edt_weight)
        edtMedConds = findViewById(R.id.edt_med_conds)
        btnUpdate = findViewById(R.id.btn_update)
    }

    private fun setViews() {
        btnUpdate.setOnClickListener {
            if (validInput()) {
                setValues()
                Prefs.user = user
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


    private fun setData() {
        if (user != null) {
            if (!user.image.isBlank()) {
                val uri = Uri.parse(user.image)
                imgUser.setImageURI(uri)
            }
            if (!TextUtils.isEmpty(user.name)) {
                edtName.setText(user.name)
            }
            edtHeight.setText(user.height.toString())
            edtWeight.setText(user.weight.toString())
            if (!TextUtils.isEmpty(user.medicalConditions)) {
                edtMedConds.setText(user.medicalConditions)
            }
        }
    }

    private fun setValues() {
        user?.name = edtName.text.toString().trim()
        user?.height = edtHeight.text.toString().trim().toDouble()
        user?.weight = edtWeight.text.toString().trim().toDouble()
        user?.medicalConditions = edtMedConds.text.toString().trim()
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

    private fun getCameraImage() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePicture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
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
        when (requestCode) {
            0 -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage = imageReturnedIntent!!.data
                imgUser.setImageURI(selectedImage)
                user?.image = selectedImage.toString()
            }
            1 -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage = imageReturnedIntent!!.data
                imgUser.setImageURI(selectedImage)
                user?.image = selectedImage.toString()
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
