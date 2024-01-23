package com.fit.app.alina.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.fit.app.alina.ui.activity.MainActivity


class ConfirmPhoneDialog(val code: String) : DialogFragment() {

    lateinit var editText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        editText = EditText(requireContext())
        return AlertDialog.Builder(requireContext())
            .setMessage("Введите код из смс")
            .setView(editText)
            .setPositiveButton("Подтвердить", null)
            .create()
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onStart() {
        super.onStart()
        val dialog = dialog as AlertDialog?
        val positiveButton = dialog!!.getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (checkValidCode(editText.text.toString()))
                (activity as MainActivity).loginViewModel.smsValidated()
                dialog.cancel()
        }
    }
    private fun checkValidCode(enteredCode: String): Boolean {
        return code == enteredCode
    }
}