package lcleite.github.com.helloandroidkotlin.view

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import lcleite.github.com.helloandroidjava.utils.AndroidUtils
import lcleite.github.com.helloandroidkotlin.R
import lcleite.github.com.helloandroidkotlin.async.GetTweetsTask
import lcleite.github.com.helloandroidkotlin.model.Tweet
import lcleite.github.com.helloandroidkotlin.utils.StringUtils
import lcleite.github.com.helloandroidkotlin.view.list.SearchResultAdapter
import lcleite.github.com.helloandroidkotlin.view.list.SearchResultItemDivider


class MainActivity : AppCompatActivity(), View.OnClickListener, GetTweetsTask.Callback,
                     SearchResultAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener,
                     TextView.OnEditorActionListener {

    companion object {
        val EXTRA_TWEET: String = "lcleite.github.com.MainActivity.EXTRA_TWEET"
    }

    private var lastQuery: String? = null
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var searchResults: MutableList<Tweet>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAppBar()
        initViews()
    }

    private fun initAppBar() {
        val toolbar = appBar as Toolbar

        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    private fun initViews() {
        initSearchViews()
        initRefreshContainer()
        initRecyclerView()
    }

    private fun initSearchViews() {
        etSearch.setOnEditorActionListener(this)
        ibSearch.setOnClickListener(this)
    }

    private fun initRefreshContainer() {
        refreshContainer.setOnRefreshListener(this)
        refreshContainer.setColorSchemeColors(getColor(R.color.colorPrimary), getColor(R.color.colorAccent))
    }

    private fun initRecyclerView() {
        searchResults = ArrayList<Tweet>()
        searchResultAdapter = SearchResultAdapter(searchResults)
        searchResultAdapter.setOnItemClickListener(this)
        rvSearchResult.layoutManager = LinearLayoutManager(this)
        rvSearchResult.adapter = searchResultAdapter
        rvSearchResult.addItemDecoration(SearchResultItemDivider(ContextCompat.getDrawable(this, R.drawable.search_result_divider)))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menuSettings -> goToSettings()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun goToSettings() {
        val intent: Intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(view: View) {
        searchTweet()
    }

    override fun onEditorAction(textView: TextView?, actionId: Int,keyEvent: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchTweet()
            return true
        }

        return false
    }

    private fun searchTweet() {
        val query: String = createSearchQuery()

        setLoading(true)
        saveSearchTermForRefreshAction(query)
        executeSearchTask(query)
    }

    private fun createSearchQuery(): String {
        val searchQuery: String = etSearch.text.toString()

        return StringUtils.encodeSearchTerm(searchQuery)
    }

    private fun saveSearchTermForRefreshAction(query: String){
        lastQuery = query
    }

    override fun setSearchResult(results: List<Tweet>) {
        setRefreshing(false)
        updateResultList(results)
        setLoading(false)
        AndroidUtils.hideSoftKeyboard(this, ibSearch)
    }

    private fun setRefreshing(refreshing: Boolean){
        if(refreshContainer.isRefreshing)
            refreshContainer.isRefreshing = refreshing

    }

    private fun executeSearchTask(query: String){
        GetTweetsTask(this).execute(query)
    }

    private fun updateResultList(results: List<Tweet> ){
        searchResults.clear()
        searchResults.addAll(results)
        searchResultAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int) {
        val tweet: Tweet = searchResults.get(position)
        val intent: Intent = Intent(this, TweetActivity::class.java)

        intent.putExtra(EXTRA_TWEET, tweet)
        startActivity(intent)
    }

    override fun onRefresh() {
        if(lastQuery != null)
            executeSearchTask(lastQuery!!)
        else
            setRefreshing(false)
    }

    private fun setLoading(loading: Boolean) {
        if(loading)
            pbarLoading.visibility = View.VISIBLE
        else
            pbarLoading.visibility = View.GONE
    }
}


