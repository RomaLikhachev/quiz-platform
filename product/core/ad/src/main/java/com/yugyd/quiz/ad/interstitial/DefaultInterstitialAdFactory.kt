package com.yugyd.quiz.ad.interstitial

import android.app.Activity
import android.content.Context
import com.yugyd.quiz.ad.common.AdEventCallback

internal class DefaultInterstitialAdFactory : InterstitialAdFactory {

    override fun create(
        context: Context,
        callback: AdEventCallback,
        isActivityDestroyProvider: () -> Boolean
    ) {
        throw IllegalStateException("Ad not created in default factory")
    }

    override fun loadAd(adUnitId: String) {
        throw IllegalStateException("Ad not created in default factory")
    }

    override fun showAd(activity: Activity, callback: AdInterstitialEventCallback) {
        throw IllegalStateException("Ad not created in default factory")
    }

    override fun destroy() {
        throw IllegalStateException("Ad not created in default factory")
    }
}
