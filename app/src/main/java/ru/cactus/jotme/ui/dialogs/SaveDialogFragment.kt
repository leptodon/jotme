package ru.cactus.jotme.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import ru.cactus.jotme.R
import ru.cactus.jotme.utils.FRG_BUNDLE_SAVE
import ru.cactus.jotme.utils.FRG_SDF_SAVE

/**
 * Диалог с подтверждением сохранения заметки
 */
class SaveDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(getString(R.string.order_confirmation))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                setFragmentResult(FRG_BUNDLE_SAVE, bundleOf(FRG_SDF_SAVE to true))
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }

        return builder.create()
    }
}