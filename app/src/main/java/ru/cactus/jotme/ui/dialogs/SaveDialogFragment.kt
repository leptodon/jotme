package ru.cactus.jotme.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.cactus.jotme.R
import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.utils.EXTRA_NOTE

/**
 * Диалог с подтверждением сохранения заметки
 */
class SaveDialogFragment : DialogFragment() {

    private val _saveNote = MutableLiveData<Note>()

    val saveNote: LiveData<Note>
        get() = _saveNote

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)

        val note = arguments?.getParcelable<Note>(EXTRA_NOTE)

        builder.setMessage(getString(R.string.order_confirmation))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                note?.let { _saveNote.postValue(it) }
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }

        return builder.create()
    }
}