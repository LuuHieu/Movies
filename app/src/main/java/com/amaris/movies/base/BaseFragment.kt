package com.amaris.movies.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<DB : ViewDataBinding>(private val dataBinding: (LayoutInflater, ViewGroup?, Boolean) -> DB) :
    Fragment() {

    private var _binding: DB? = null
    protected val binding: DB
        get() = _binding!!

    abstract val viewModel: BaseViewModel

    open fun needHandleBackPress(): Boolean = false

    open fun onFragmentBackPressed() {}

    abstract fun onFragmentCreated(view: View, savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (needHandleBackPress()) {
                    onFragmentBackPressed()
                } else {
                    isEnabled = false
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = dataBinding.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.hideKeyboardWhenTouchOutside()
        viewModel.networkError.observe(viewLifecycleOwner) {
            if (it != null) {
                (activity as? BaseActivity<*>)?.handleNetworkException(it)
            }
        }
        onFragmentCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    protected fun showToast(resId: (Int)) {
        Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT).show()
    }
}