package com.writenow.ui

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.writenow.R
import com.writenow.base.BaseDialogFragment
import com.writenow.databinding.DialogActionCmdBinding

class ActionCmdDialog(recordResult:String): BaseDialogFragment<DialogActionCmdBinding>(R.layout.dialog_action_cmd) {
    private val cmd:String = recordResult

    override fun initDataBinding() {
        super.initDataBinding()

        binding.tvCmd.text = resources.getString((R.string.info_cmd), cmd)
    }
    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.btnCancelCmd.setOnClickListener{
            setFragmentResult("cancelCmd", bundleOf("result" to "cancel"))
            dismiss()
        }
        binding.btnDoCmd.setOnClickListener{
            setFragmentResult("cancelCmd", bundleOf("result" to "do"))
            dismiss()
        }
    }
}