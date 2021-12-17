package ru.cactus.jotme.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.cactus.jotme.R
import ru.cactus.jotme.ui.note_edit.NoteEditActivity

class SaveDialogFragment() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(getString(R.string.order_confirmation))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                activity?.let { it as NoteEditActivity }.apply { this?.saveNote() }
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }

        return builder.create()
    }
}