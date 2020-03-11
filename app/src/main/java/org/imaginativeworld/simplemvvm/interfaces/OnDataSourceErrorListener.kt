package org.imaginativeworld.simplemvvm.interfaces

import java.lang.Exception

interface OnDataSourceErrorListener {

    fun onError(exception: Exception)

}