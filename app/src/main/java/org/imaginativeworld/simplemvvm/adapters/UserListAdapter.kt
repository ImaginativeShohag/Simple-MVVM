package org.imaginativeworld.simplemvvm.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import kotlinx.android.synthetic.main.item_user.view.*
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.models.UserEntity
import org.imaginativeworld.simplemvvm.utils.GlideApp

class UserListAdapter(
    private var context: Context,
    listener: OnObjectListInteractionListener<UserEntity>
) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    private var mListener = listener

    private var dataList: MutableList<UserEntity> = arrayListOf()

    fun addAll(dataList: List<UserEntity>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()

        checkEmptiness()
    }

    fun add(obj: UserEntity) {
        this.dataList.add(obj)
        notifyItemInserted(this.dataList.size - 1)

        checkEmptiness()
    }

    fun empty() {
        this.dataList.clear()
        notifyDataSetChanged()

        checkEmptiness()
    }

    fun checkEmptiness() {
        if (this.dataList.size > 0) {
            mListener.hideEmptyView()
        } else {
            mListener.showEmptyView()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)

        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.dataList.size
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val obj: UserEntity = dataList[position]

        holder.tvName.text = obj.name
        holder.tvPhone.text = obj.phone

        GlideApp.with(context)
            .load(obj.image)
            .profilePhoto()
            .into(holder.imgProfile)

        // Init Listener
        holder.itemView.setOnClickListener {

            mListener.onClick(position, obj)

        }

        holder.itemView.setOnLongClickListener {

            mListener.onLongClick(position, obj)

            true

        }
    }


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgProfile: ImageView = itemView.img_profile
        val tvName: TextView = itemView.tv_name
        val tvPhone: TextView = itemView.tv_phone

    }

}