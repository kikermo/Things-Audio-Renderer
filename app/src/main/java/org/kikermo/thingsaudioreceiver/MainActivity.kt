package org.kikermo.thingsaudioreceiver

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.kikermo.thingsaudioreceiver.nowplaying.NowPlayingFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = NowPlayingFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
