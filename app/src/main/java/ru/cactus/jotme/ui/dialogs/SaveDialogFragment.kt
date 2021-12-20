package ru.cactus.jotme.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.cactus.jotme.R
import ru.cactus.jotme.ui.note_edit.NoteEditActivity
import ru.cactus.jotme.ui.note_edit.NoteEditContract

/**
 * Диалог с подтверждением сохранения заметки
 */
class SaveDialogFragment(private val view: NoteEditContract.View) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(view.getContext())
        builder.setMessage(getString(R.string.order_confirmation))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                view.let { it as NoteEditActivity }.apply { view.onClickSaveBtn() }
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }

        return builder.create()
    }
}