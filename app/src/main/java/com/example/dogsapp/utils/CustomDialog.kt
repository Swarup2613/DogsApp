package com.example.dogsapp.utils

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.dogsapp.R

object CustomDialog {

    fun showAlertDialogButtonClicked(context: Context, layoutInflater: LayoutInflater) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Action!")
        val customLayout: View = layoutInflater.inflate(R.layout.select_action_dialog, null)
        builder.setView(customLayout)

        // add a button
        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }
}