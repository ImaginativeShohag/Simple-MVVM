package org.imaginativeworld.simplemvvm.interfaces

interface OnObjectListInteractionListener<T> {

    fun onClick(position: Int, dataObject: T)

    fun onLongClick(position: Int, dataObject: T)

    fun showEmptyView()

    fun hideEmptyView()

}