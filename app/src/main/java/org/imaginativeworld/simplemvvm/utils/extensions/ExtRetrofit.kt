/*
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * MVVM Pattern Source: https://github.com/ImaginativeShohag/Simple-MVVM
 */

package org.imaginativeworld.simplemvvm.utils.extensions

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun String?.toFormRequestBody() = this?.toRequestBody(MultipartBody.FORM)

/**
 * @return [MultipartBody.Part] from the file's real path or null if the path is null.
 */
fun String?.createFormDataFromPath(fieldName: String): MultipartBody.Part? {
    return this?.let { imagePath ->
        val pictureFile = File(imagePath)

        val requestBody = pictureFile.asRequestBody(imagePath.toMediaTypeOrNull())

        MultipartBody.Part.createFormData(
            fieldName,
            pictureFile.name,
            requestBody
        )
    }
}