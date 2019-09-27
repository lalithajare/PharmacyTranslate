package com.translator.app.models

import java.io.Serializable

class User : Serializable {
    var name: String? = ""
    var height: Double = 0.0
    var weight: Double = 0.0
    var medicalConditions: String? = ""
    var image: String = ""
}