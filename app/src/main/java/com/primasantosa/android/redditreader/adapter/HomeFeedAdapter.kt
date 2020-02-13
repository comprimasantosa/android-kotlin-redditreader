package com.primasantosa.android.redditreader.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.primasantosa.android.redditreader.R
import com.primasantosa.android.redditreader.model.Feed
import java.text.SimpleDateFormat
import java.util.*

class HomeFeedAdapter : PagedListAdapter<Feed, HomeFeedAdapter.ViewHolder>(FeedDiffCallback) {
    companion object FeedDiffCallback : DiffUtil.ItemCallback<Feed>() {
        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.title == newItem.title && oldItem.score == newItem.score && oldItem.numComments == newItem.numComments
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author: TextView = itemView.findViewById(R.id.feed_author)
        val title: TextView = itemView.findViewById(R.id.feed_title)
        val score: TextView = itemView.findViewById(R.id.feed_score)
        val comments: TextView = itemView.findViewById(R.id.feed_comments_number)
        val subreddit: TextView = itemView.findViewById(R.id.feed_subreddit)
        val time: TextView = itemView.findViewById(R.id.feed_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_home_feed, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feed = getItem(position)

        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        holder.author.text = feed?.author
        holder.score.text = feed?.score.toString()
        holder.title.text = feed?.title
        holder.subreddit.text = feed?.subreddit
        holder.comments.text = when (feed?.numComments) {
            0 or 1 -> "${feed.numComments} comment"
            else -> "${feed?.numComments} comments"
        }
        holder.time.text = formatter.format(feed?.time?.times(1000))
    }

}