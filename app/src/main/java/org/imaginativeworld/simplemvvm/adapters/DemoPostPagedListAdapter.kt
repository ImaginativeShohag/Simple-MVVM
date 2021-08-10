package org.imaginativeworld.simplemvvm.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.imaginativeworld.simplemvvm.databinding.DemoItemPostBinding
import org.imaginativeworld.simplemvvm.interfaces.BindableAdapter
import org.imaginativeworld.simplemvvm.interfaces.OnObjectListInteractionListener
import org.imaginativeworld.simplemvvm.models.DemoPostResult
import org.imaginativeworld.simplemvvm.utils.calculatePaletteInImage
import org.imaginativeworld.simplemvvm.utils.extensions.dpToPx
import timber.log.Timber

class DemoPostPagedListAdapter(
    private val listener: OnObjectListInteractionListener<DemoPostResult>
) : PagedListAdapter<DemoPostResult, DemoPostPagedListAdapter.ListViewHolder>(DIFF_CALLBACK),
    BindableAdapter<PagedList<DemoPostResult>> {

    companion object {

        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<DemoPostResult>() {
            override fun areItemsTheSame(
                oldItem: DemoPostResult,
                newItem: DemoPostResult
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DemoPostResult,
                newItem: DemoPostResult
            ): Boolean {
                return oldItem == newItem
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun setItems(data: PagedList<DemoPostResult>?) {
        submitList(data) {
            data?.apply {
                checkEmptiness()
            }
        }
    }

    private fun checkEmptiness() {
        if (itemCount > 0) {
            listener.hideEmptyView()
        } else {
            listener.showEmptyView()
        }
    }


    class ListViewHolder private constructor(
        private val binding: DemoItemPostBinding,
        private val listener: OnObjectListInteractionListener<DemoPostResult>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DemoPostResult?) {
            item?.also { _item ->
                binding.post = _item
                binding.executePendingBindings()

                // Image
                val imageUrl = "https://picsum.photos/200?$bindingAdapterPosition"
                binding.img.load(imageUrl) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(8.dpToPx().toFloat()))
                }

                binding.root.setBackgroundColor(
                    Color.parseColor("#ffffff")
                )
                binding.tvTitle.setTextColor(
                    Color.parseColor("#000000")
                )
                binding.tvBody.setTextColor(
                    Color.parseColor("#000000")
                )

                CoroutineScope(Dispatchers.Main).launch {
                    val position = bindingAdapterPosition
                    calculatePaletteInImage(
                        context = binding.root.context,
                        imageUrl = imageUrl
                    )?.let { swatch ->
                        Timber.e("position: $position | bindingAdapterPosition: $bindingAdapterPosition")

                        if (position == bindingAdapterPosition) {
                            binding.root.setBackgroundColor(
                                swatch.rgb
                            )
                            binding.tvTitle.setTextColor(
                                swatch.titleTextColor
                            )
                            binding.tvBody.setTextColor(
                                swatch.bodyTextColor
                            )
                        }
                    }
                }

                binding.root.setOnClickListener {
                    listener.onClick(bindingAdapterPosition, _item)
                }

                binding.root.setOnLongClickListener {
                    listener.onLongClick(bindingAdapterPosition, _item)
                    true
                }
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                listener: OnObjectListInteractionListener<DemoPostResult>
            ): DemoPostPagedListAdapter.ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DemoItemPostBinding.inflate(layoutInflater, parent, false)
                return DemoPostPagedListAdapter.ListViewHolder(binding, listener)
            }
        }

    }

}