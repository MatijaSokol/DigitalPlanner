package hr.ferit.matijasokol.digitalplanner.app

import android.app.Application
import android.content.Context
import android.os.Build
import hr.ferit.matijasokol.digitalplanner.notifications.notification.NotificationHelperImpl
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    companion object {
        lateinit var instance: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        setRealm()
        setNotificationsChannels()
    }

    private fun setNotificationsChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationHelperImpl.createChannels()
        }
    }

    private fun setRealm() {
        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .schemaVersion(1L)
                .deleteRealmIfMigrationNeeded()
                .build()
        )
    }
}