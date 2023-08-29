package com.writenow.ui

import com.writenow.R
import com.writenow.base.BaseFragment
import com.writenow.databinding.FragmentShowResultBinding

class ShowResultFragment : BaseFragment<FragmentShowResultBinding>(R.layout.fragment_show_result) {
    private lateinit var recordResult:String
    private lateinit var cmdResult:String
    private var text:String = ""

    override fun initAfterBinding() {
        super.initAfterBinding()

        binding.btnAgainRecord.setOnClickListener{
            navController.navigate(R.id.action_showResultFragment_to_recordFragment)
        }

        binding.btnDoAction.setOnClickListener{
            //ActionTextDialog().show(parentFragmentManager, "actionText")
            if (recordResult=="sos")
                ActionCmdDialog(recordResult).show(parentFragmentManager, "actionCmd")
            else
                ActionTextDialog(recordResult).show(parentFragmentManager, "actionText")
        }
    }
}