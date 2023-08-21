package com.writenow.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.ContactsContract
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.writenow.R
import com.writenow.base.BaseDialogFragment
import com.writenow.databinding.DialogActionTextBinding

class ActionTextDialog(txt:String): BaseDialogFragment<DialogActionTextBinding>(R.layout.dialog_action_text) {
    private val text:String = txt

    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.tvResultText.text = text

        binding.btnEsc.setOnClickListener{
            dismiss()
        }
        binding.btnCopyText.setOnClickListener{
            val clipboardManager: ClipboardManager =
                requireContext().getSystemService(
                    Context.CLIPBOARD_SERVICE
                ) as ClipboardManager
            val clipData = ClipData.newPlainText("text", text)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(
                context,
                getString(R.string.copied),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onResume() {
        super.onResume()

        // dialog full Screen code
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
}