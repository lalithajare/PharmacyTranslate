package com.translator.app.utils

import android.os.Environment
import java.io.File

object FileManager {

    val IMAGE_PATH = "${Environment.getExternalStorageDirectory()}/Pharma_Translator/Images"
    val PROFILE_FILE_NAME = "Profile.jpg"

    fun createDirectory() {
        if (!File(IMAGE_PATH).exists()) {
            File(IMAGE_PATH).mkdirs()
        }
    }

    fun getFile(): File {
        return File(File(IMAGE_PATH), PROFILE_FILE_NAME)
    }

}