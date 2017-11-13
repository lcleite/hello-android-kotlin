package lcleite.github.com.helloandroidkotlin.view.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.search_result_view.view.*
import lcleite.github.com.helloandroidkotlin.R
import lcleite.github.com.helloandroidkotlin.async.SetDownloadedImageTask
import lcleite.github.com.helloandroidkotlin.model.Tweet
import lcleite.github.com.helloandroidkotlin.model.TweetMedia
import lcleite.github.com.helloandroidkotlin.utils.DateUtils
import lcleite.github.com.helloandroidkotlin.utils.StringUtils


class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private var tweets: List<Tweet>
    private lateinit var onItemClickListener: OnItemClickListener

    constructor(tweets: List<Tweet> ) {
        this.tweets = tweets
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.search_result_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet: Tweet = tweets.get(position)

        downloadUserProfileImage(tweet, holder)
        setViews(tweet, holder)
        setMedias(tweet, holder)
    }

    private fun downloadUserProfileImage(tweet: Tweet, holder: ViewHolder) {
        tweet.user.userProfileImageUrl?.let { imageUrl ->
            SetDownloadedImageTask(imageUrl, holder.ivUserProfile).execute()
        }
    }

    private fun setViews(tweet: Tweet, holder: ViewHolder){
        holder.tvUserName.text = tweet.user.userName
        holder.tvUserScreenName.text = StringUtils.screenNameWithAt(tweet.user.userScreenName)
        holder.tvText.text = tweet.content.text
        holder.tvHashtags.text = tweet.content.hashtags.size.toString()
        holder.tvMentions.text = tweet.content.userMentions.size.toString()
        holder.tvCreatedAt.text = DateUtils.getStringFromDate(tweet.createdAt)
    }

    private fun setMedias(tweet: Tweet, holder: ViewHolder) {
        when(tweet.media.type){
            TweetMedia.Type.PHOTO -> {
                holder.ivMediaPhoto.visibility = View.VISIBLE
                holder.ivMediaVideo.visibility = View.GONE
            }
            TweetMedia.Type.VIDEO -> {
                holder.ivMediaPhoto.visibility = View.GONE
                holder.ivMediaVideo.visibility = View.VISIBLE
            }
            TweetMedia.Type.NONE -> {
                holder.ivMediaPhoto.visibility = View.GONE
                holder.ivMediaVideo.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var ivUserProfile: ImageView
        var tvUserName: TextView
        var tvUserScreenName: TextView
        var tvText: TextView
        var ivMediaPhoto: ImageView
        var ivMediaVideo: ImageView
        var tvHashtags: TextView
        var tvMentions: TextView
        var tvCreatedAt: TextView

        init {
            itemView.setOnClickListener(this)
            ivUserProfile = itemView.ivUserProfile
            tvUserName = itemView.tvUserName
            tvUserScreenName = itemView.tvUserScreenName
            tvText = itemView.tvText
            ivMediaPhoto = itemView.ivMediaPhoto
            ivMediaVideo = itemView.ivMediaVideo
            tvHashtags = itemView.tvHashtags
            tvMentions = itemView.tvMentions
            tvCreatedAt = itemView.tvCreatedAt
        }

        override fun onClick(view: View) {
            onItemClickListener.onItemClick(adapterPosition)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}
