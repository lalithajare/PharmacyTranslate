package com.translator.app.models

import java.io.Serializable

/**
 * 1. This class encapsulates all the 'User' related data like : name,height,weight,medical conditions,image.
 * 2. This model is used primarily while saving data of User in SharedPreferences.
 */
class User : Serializable {
    var name: String? = ""
    var height: Double = 0.0
    var weight: Double = 0.0
    var medicalConditions: String? = ""
    var image: String = ""
}