package com.test.local.info

import android.content.Context
import android.os.Handler
import com.test.data.datasources.IInfoDS
import com.test.domain.model.Info
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class LocalInfoDS(private val context: Context) : IInfoDS {

    private var beaconsSource = PublishSubject.create<Info>()

    private val beaconManager = BCBeaconManager()

    override fun observeBeacons(period: Int): Observable<List<Beacons>> {
        return Observable.create<List<Beacons>> { it ->
            val appTokenVerificationStatus = BlueCatsSDK.getAppTokenVerificationStatus()
            if (appTokenVerificationStatus == BlueCatsSDK.BCAppTokenVerificationStatus.BC_APP_TOKEN_VERIFICATION_STATUS_NOT_PROVIDED) {
                //The app token hasn't been provided; do something.
                it.onError(BluecatsErrors())
            } else if (!BlueCatsSDK.isLocationAuthorized(context)) {
                //No GPS available; enable GPS.
                it.onError(BluecatsErrors())
            } else if (!BlueCatsSDK.isNetworkReachable(context)) {
                //No network reachable; enable network connection.
                it.onError(BluecatsErrors())
            } else if (!BlueCatsSDK.isBluetoothEnabled()) {
                //Bluetooth is turned off; enable it.
                it.onError(BluecatsErrors())
            } else {
                Handler().postDelayed({
                                          beaconsSource.subscribe({ list ->
                                                                      it.onNext(list)
                                                                  }, {})
                                          beaconManager.registerCallback(beaconManagerCallback)
                                      }, 200)
            }
        }
                .sample(Observable.interval(0, period.toLong(), TimeUnit.SECONDS))
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnTerminate {
                    beaconManager.unregisterCallback(beaconManagerCallback)
                }
    }

    private val beaconManagerCallback = object : BCBeaconManagerCallback() {
        override fun didRangeBeacons(beacons: MutableList<BCBeacon>?) {
            beacons?.apply {
                val beacons = mutableListOf<Beacons>()
                for (item in this) {
                    val beacon =
                            Beacons(item.beaconID, item.accuracy, item.rssi)
                    beacons.add(beacon)
                }
                if (beacons.isNotEmpty()) {
                    beaconsSource.onNext(beacons)
                }
            }
        }
    }

    override fun postBeacons(beacon: List<Beacons>, inviteCode: String, pbUserId: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
