package ru.cactus.jotme.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.cactus.jotme.R
import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.utils.EXTRA_NOTE

class SaveDialogFragment(private val onBtnYesClick: (Note) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val note = arguments?.getParcelable<Note>(EXTRA_NOTE)

        val builder = AlertDialog.Builder(activity)
        builder.setMessage(getString(R.string.order_confirmation))
            .setPositiveButton(getString(R.string.ok)) { dialog, id ->
                onBtnYesClick.apply {
                    note?.let {
                        invoke(
                            it
                        )
                    }
                }
            }
            .setNegativeButton(getString(R.string.no)) { dialog, id -> dialog.cancel() }

        return builder.create()
    }

    companion object {
        const val TAG = "SaveConfirmationDialog"
    }

}