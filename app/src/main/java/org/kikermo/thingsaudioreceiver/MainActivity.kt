package org.kikermo.thingsaudioreceiver

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import org.kikermo.thingsaudioreceiver.model.ReceiverRepositoryImp
import org.kikermo.thingsaudioreceiver.nowplaying.NowPlayingContract
import org.kikermo.thingsaudioreceiver.nowplaying.NowPlayingFragment
import org.kikermo.thingsaudioreceiver.nowplaying.NowPlayingPresenter

class MainActivity : AppCompatActivity() {
    private lateinit var presenter: NowPlayingContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = NowPlayingFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()

        val view = fragment
        presenter = NowPlayingPresenter(fragment, ReceiverRepositoryImp(this))
    }

    override fun onResume() {
        super.onResume()

        presenter?.run {  subscribe()}
    }

    override fun onPause() {
        presenter.unsubscribe()

        super.onPause()
    }
}
