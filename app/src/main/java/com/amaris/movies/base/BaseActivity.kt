package com.amaris.movies.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.amaris.movies.DURATION_1_SECOND
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseActivity<DB : ViewDataBinding>(private val dataBinding: (LayoutInflater) -> DB) :
    AppCompatActivity() {

    private var _binding: DB? = null
    protected val binding: DB
        get() = _binding!!

    private val loadingDialog: LoadingDialog by lazy { LoadingDialog() }

    protected abstract val viewModel: BaseViewModel

    abstract fun onActivityCreated(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = dataBinding.invoke(layoutInflater)
        setContentView(binding.root)
        binding.hideKeyboardWhenTouchOutside()
        viewModel.networkError.observe(this) {
            if (it == null) return@observe
            handleNetworkException(it)
        }
        onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        loadingDialog.dismiss()
    }

    fun showLoading() {
        loadingDialog.show(this)
    }

    fun hideLoading() {
        loadingDialog.dismiss()
    }

    fun handleNetworkException(error: Result.Error<out Any>) {
        viewModel.isErrorHandling = true
        when (val notify = error.toNotify(this)) {
            is Notify.Toast -> {
                Toast.makeText(this, notify.message, Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    // delay 1 second for next toast
                    delay(DURATION_1_SECOND)
                    viewModel.isErrorHandling = false
                }
            }

            is Notify.PopUp -> {
                if (error.isUnauthorized()) {
                    Toast.makeText(this, notify.message, Toast.LENGTH_SHORT).show()
                    viewModel.isErrorHandling = false
                }
            }

            is Notify.Alert -> {
                if (notify.error != null){
                    Toast.makeText(this, notify.message, Toast.LENGTH_SHORT).show()
                    viewModel.isErrorHandling = false
                }
            }
        }
    }
}