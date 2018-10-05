package org.kikermo.thingsaudio.renderer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.kikermo.thingsaudio.renderer.nowplaying.NowPlayingFragment
import org.kikermo.thingsaudioreceiver.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = NowPlayingFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
