package hr.ferit.matijasokol.digitalplanner.ui.activities.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.ferit.matijasokol.digitalplanner.R

abstract class BaseActivity(private val layoutResourceId: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(layoutResourceId)
        setUpUi()
    }

    abstract fun setUpUi()

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}