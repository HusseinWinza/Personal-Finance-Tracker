package www.mc.com.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import www.mc.com.R
import www.mc.com.main.MainActivity
import www.mc.com.tour.TourActivity
import www.mc.com.utils.AppConstants.PREF_VALUE
import www.mc.com.utils.AppConstants.TOUR_PREF_KEY

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        CoroutineScope(Dispatchers.Main).launch {
            delay(2600)
            val pref = getSharedPreferences(PREF_VALUE, Context.MODE_PRIVATE)
            if (pref.getBoolean(TOUR_PREF_KEY, false)) {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashScreenActivity, TourActivity::class.java))
            }
            finish()
        }
    }
}