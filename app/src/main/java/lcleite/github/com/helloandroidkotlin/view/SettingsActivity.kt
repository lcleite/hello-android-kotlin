package lcleite.github.com.helloandroidkotlin.view

import android.content.SharedPreferences
import lcleite.github.com.helloandroidkotlin.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_settings.*
import lcleite.github.com.helloandroidjava.utils.AndroidUtils

class SettingsActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initAppBar()
        initViews()
        getSavedSettings()
    }

    private fun initViews() {
        sbarTweetsSettings.setOnSeekBarChangeListener(this)
    }

    private fun initAppBar() {
        val toolbar = appBar as Toolbar

        toolbar.setTitle(R.string.settings_title)
        setSupportActionBar(toolbar)


        supportActionBar?.let{
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun getSavedSettings() {
        val sharedPrefs: SharedPreferences = AndroidUtils.getPreferences(this)
        val tweetsSettingsValue: Int = sharedPrefs.getInt(getString(R.string.preference_max_tweets), 10)
        val actualValue: Int = tweetsSettingsValue - 5

        tvTweetsSettingsValue.text = tweetsSettingsValue.toString()
        sbarTweetsSettings.progress = actualValue
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return true
    }

    override fun onProgressChanged(seekBar: SeekBar?, i: Int, b: Boolean) {
        val actualValue: Int = i + 5

        tvTweetsSettingsValue.text = actualValue.toString()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    override fun onStop() {
        saveSettings()
        super.onStop()
    }

    private fun saveSettings() {
        val sharedPrefs: SharedPreferences = AndroidUtils.getPreferences(this)
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
        editor.putInt(getString(R.string.preference_max_tweets), tvTweetsSettingsValue.text.toString().toInt())
        editor.commit()
    }
}
