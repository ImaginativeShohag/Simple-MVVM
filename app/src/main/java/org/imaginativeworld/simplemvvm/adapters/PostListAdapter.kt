package org.imaginativeworld.simplemvvm.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import kotlinx.android.synthetic.main.item_user.view.*
import org.imaginativeworld.simplemvvm.R
import org.imaginativeworld.simplemvvm.models.PostResponse

class PostListAdapter(
    private var context: Context,
    listener: OnObjectListInteractionListener<PostResponse>
) :
    RecyclerView.Adapter<PostListAdapter.ListViewHolder>() {

    private var mListener = listener

    private var dataList: MutableList<PostResponse> = arrayListOf()

    fun addAll(dataList: List<PostResponse>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()

        checkEmptiness()
    }

    fun add(obj: PostResponse) {
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
            .inflate(R.layout.item_post, parent, false)

        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.dataList.size
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val obj: PostResponse = dataList[position]

        holder.tvTitle.text = obj.title
        holder.tvBody.text = obj.body


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

        val tvTitle: TextView = itemView.tv_title
        val tvBody: TextView = itemView.tv_body

    }

}