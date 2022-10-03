package ui.dendi.fcm.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ui.dendi.fcm.databinding.MessageItemBinding
import ui.dendi.fcm.data.db.MessageEntity

class MessagesAdapter :
    ListAdapter<MessageEntity, MessagesAdapter.MessageViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder =
        MessageItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).let(::MessageViewHolder)

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    inner class MessageViewHolder(
        private val binding: MessageItemBinding
    ) : ViewHolder(binding.root) {
        fun onBind(message: MessageEntity) = with(binding) {
            tvTitle.text = message.title
            tvBody.text = message.body
            tvDate.text = message.date
        }
    }

    companion object ItemCallback : DiffUtil.ItemCallback<MessageEntity>() {
        override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean =
            oldItem == newItem
    }
}