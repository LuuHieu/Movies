package com.amaris.movies.base.utils

import android.app.Activity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Auto reset to defValue when owner is DESTROYED (not by the configuration changed)
 * This one should be used when a Fragment is added to backstack and when it is popped back,
 * the data is expected to be reset to default value
 * @param defValue (optional) default value of LiveData
 */
class MutableResetLiveData<T>(
    private val defValue: T? = null
) : MutableLiveData<T>(defValue) {
    private var shouldResetValue = true

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        /*
         * when owner is fragment -> get the viewLifecycleOwner of fragment,
         * because fragment will not be destroyed when it was added to backstack
         */
        val lifecycleOwner = when (owner) {
            is Fragment -> owner.viewLifecycleOwner
            else -> owner
        }

        /*
         * add lifecycle observer in order to listen to configuration change at ON_STOP
         * and reset value at ON_DESTROY (not by configuration change)
         */
        lifecycleOwner.lifecycle.addObserver(
            object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_STOP) {
                        shouldResetValue = !isConfigurationChanging(owner)
                    }
                    /*
                     * Not reset the value when configuration change
                     */
                    if (shouldResetValue && event == Lifecycle.Event.ON_DESTROY) {
                        value = defValue
                    }
                }
            }
        )
        super.observe(owner, observer)
    }

    /**
     * use this in order to detect ON_DESTROY is trigger by configuration change or not
     */
    private fun isConfigurationChanging(owner: LifecycleOwner): Boolean = when (owner) {
        is Activity -> owner.isChangingConfigurations
        is Fragment -> owner.activity?.isChangingConfigurations == true
        else -> false
    }
}