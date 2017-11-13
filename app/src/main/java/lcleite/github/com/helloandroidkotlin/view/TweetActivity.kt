package lcleite.github.com.helloandroidkotlin.view

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import kotlinx.android.synthetic.main.activity_tweet.*
import lcleite.github.com.helloandroidkotlin.R

import lcleite.github.com.helloandroidkotlin.async.DownloadImageTask
import lcleite.github.com.helloandroidkotlin.async.SetDownloadedImageTask
import lcleite.github.com.helloandroidkotlin.model.Tweet
import lcleite.github.com.helloandroidkotlin.model.TweetMedia
import lcleite.github.com.helloandroidkotlin.utils.DateUtils
import lcleite.github.com.helloandroidkotlin.utils.StringUtils

class TweetActivity : AppCompatActivity(), View.OnClickListener, DownloadImageTask.Callback {

    private lateinit var tweet: Tweet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet)
        initAppBar()
        getExtras()
        setupViews()
    }

    private fun initAppBar() {
        val toolbar = appBar as Toolbar

        toolbar.setTitle(R.string.tweet_details_title)
        setSupportActionBar(toolbar)

        supportActionBar?.let{
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getExtras() {
        tweet = intent.getParcelableExtra(MainActivity.EXTRA_TWEET)
    }

    private fun setupViews() {
        setupTextViews()
        downloadUserProfileImage()
        downloadTweetMedia()
        setupLists()
        setupClickableLinks()
    }

    private fun setupTextViews() {
        tvUserName.text = tweet.user.userName
        tvUserScreenName.text = StringUtils.screenNameWithAt(tweet.user.userScreenName)
        tvCreatedAt.text = DateUtils.getStringFromDate(tweet.createdAt)
        tvText.text = tweet.content.text
    }

    private fun setupLists() {
        setupTextListValues(hashtagsContainer, tvHashtags, tweet.content.hashtags, "#")
        setupTextListValues(mentionsContainer, tvMentions, tweet.content.userMentions, "@")
        setupTextListValues(linksContainer, tvLinks, tweet.content.links, "")
    }

    private fun downloadUserProfileImage(){
        tweet.user.userProfileImageUrl?.let { imageUrl ->
            SetDownloadedImageTask(imageUrl, ivUserProfile).execute()
        }
    }

    private fun downloadTweetMedia(){
        if(tweet.media.type != TweetMedia.Type.NONE)
            tweet.media.photoUrl?.let { photoUrl ->
                DownloadImageTask(photoUrl, this).execute()
            }
        else
            mediaFrame.visibility = View.GONE
    }

    private fun setupTextListValues(container: LinearLayout, textView: TextView, list: List<String>, prefix: String){
        if(list.isEmpty()){
            container.visibility = View.GONE
            return
        }

        for(i in 0 until list.size){
            textView.append(prefix + list[i])
            if(i != list.size - 1)
                textView.append(", ")
        }
    }

    private fun setupClickableLinks() {
        Linkify.addLinks(tvLinks, Linkify.WEB_URLS)
        tvLinks.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onClick(view: View) {
        val videoUrl: String? = tweet.media.videoUrl
        val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))

        startActivity(intent)
    }

    override fun onImageDownloadComplete(downloadedBitmap: Bitmap) {
        ivMediaPhoto.setImageBitmap(downloadedBitmap)
        pbarLoading.visibility = View.GONE
        if(tweet.media.type == TweetMedia.Type.VIDEO)
            setupVideoThumbnail()
    }

    private fun setupVideoThumbnail() {
        ibMediaPlay.visibility = View.VISIBLE
        mediaFrame.setOnClickListener(this)
        ibMediaPlay.setOnClickListener(this)
    }
}
