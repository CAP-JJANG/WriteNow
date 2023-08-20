package com.writenow.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.view.WindowManager
import com.writenow.R
import com.writenow.base.BaseDialogFragment
import com.writenow.databinding.DialogActionTextBinding

class ActionTextDialog(txt:String): BaseDialogFragment<DialogActionTextBinding>(R.layout.dialog_action_text) {

    override fun onResume() {
        super.onResume()

        // dialog full Screen code
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
}