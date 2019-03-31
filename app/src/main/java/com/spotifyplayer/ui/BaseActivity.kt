package com.spotifyplayer.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.spotifyplayer.api.RestApiFactory
import com.spotifyplayer.api.RestRequest
import com.spotifyplayer.utils.AppExecuter
import com.spotifyplayer.utils.BaseApp
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject


@Suppress("DEPRECATION")
abstract class BaseActivity : AppCompatActivity() {

    @Inject lateinit var restRequest: RestRequest

    @Inject lateinit var appExecuter: AppExecuter

    var isTestMode : Boolean = false

    companion object {
        val isTestModeKey = "TEST_MODE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = this@BaseActivity.application as BaseApp

        if (intent.hasExtra(isTestModeKey)){
            isTestMode = intent.getBooleanExtra(isTestModeKey,false)
        }

        val appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule( app, isTestMode))
            .appExecuter(AppExecuter())
            .build()

        appComponent.inject(this)
    }

    protected fun <T : ViewDataBinding> setContentView(activity: AppCompatActivity, layoutId: Int): T {
        return DataBindingUtil.setContentView(activity, layoutId)
    }

    fun AppCompatActivity.hideKeyboard() {
        hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

@Component(modules = [AppModule::class, AppExecuter::class])
interface AppComponent {
    fun inject(app: BaseActivity)
}

@Module
class AppModule constructor(val app: BaseApp, private val isTestMode : Boolean) {

    @Provides
    fun getRestRequest() : RestRequest {
        return RestApiFactory.create(app,isTestMode)
    }
}

