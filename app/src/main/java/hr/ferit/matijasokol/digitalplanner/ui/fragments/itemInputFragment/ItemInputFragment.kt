package hr.ferit.matijasokol.digitalplanner.ui.fragments.itemInputFragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import hr.ferit.matijasokol.digitalplanner.R
import hr.ferit.matijasokol.digitalplanner.extensions.*
import hr.ferit.matijasokol.digitalplanner.model.Item
import hr.ferit.matijasokol.digitalplanner.notifications.alarm.AlarmHelperImpl
import hr.ferit.matijasokol.digitalplanner.presenters.ItemInputFragmentPresenter
import hr.ferit.matijasokol.digitalplanner.ui.activities.mainActivity.MainActivity
import hr.ferit.matijasokol.digitalplanner.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_item_input.*
import java.text.SimpleDateFormat
import java.util.*

class ItemInputFragment : BaseFragment(R.layout.fragment_item_input), ItemInputFragmentContract.View {

    private val presenter: ItemInputFragmentContract.Presenter by lazy { ItemInputFragmentPresenter(this) }

    interface OnChangeTabListener {
        fun changeTab()
    }

    private lateinit var onChangeTabListener: OnChangeTabListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onChangeTabListener = context as OnChangeTabListener
    }

    override fun setUpUi() {
        setListeners()
        setEditTextListener()
    }

    private fun setListeners() {
        buttonSaveItem.setOnClickListener { handleSaveItem() }
        imageViewCalendarMain.setOnClickListener { handleChooseDate() }
        imageViewClockMain.setOnClickListener { handleChooseTime() }
    }

    private fun setEditTextListener() {
        editTextAddNote.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text?.let { text ->
                    if (text.isEmpty()) {
                        textViewNotePreviewMain.text = getString(R.string.note_preview)
                    } else {
                        textViewNotePreviewMain.text = text.toString().trim()
                    }
                }
            }
        })
    }

    private fun handleSaveItem() {
        if (isDataEntered()) {
            val noteText = textViewNotePreviewMain.stringText()
            val dateText = textViewDatePreviewMain.stringText()
            val timeText = textViewTimePreviewMain.stringText()

            Item(note = noteText, date = dateText, time = timeText, dateCreated = getItemDateCreated(), timeCreated = getItemTimeCreated()).also {item ->
                presenter.saveItem(item)

                editTextAddNote.setText("")
                textViewNotePreviewMain.text = getString(R.string.note_preview)
                textViewDatePreviewMain.text = getString(R.string.date_preview)
                textViewTimePreviewMain.text = getString(R.string.time_preview)

                activity?.displayToast(getString(R.string.item_inserted))
                if ((activity!! as MainActivity).switchTabEnabled) {
                    onChangeTabListener.changeTab()
                }

                AlarmHelperImpl.setAlarm(activity!!, item)
            }
        } else {
            activity?.displayToast(getString(R.string.data_must_be_entered))
        }
    }

    private fun isDataEntered(): Boolean {
        val noteText = textViewNotePreviewMain.stringText()
        val dateText = textViewDatePreviewMain.stringText()
        val timeText = textViewTimePreviewMain.stringText()

        if (noteText == getString(R.string.note_preview) || dateText == getString(R.string.date_preview) || timeText == getString(R.string.time_preview)) {
            return false
        }

        return true
    }

    private fun handleChooseDate() {
        val year = calendarYear()
        val month = calendarMonth()
        val dayOfMonth = calendarDayOfMonth()
        DatePickerDialog(activity!!, R.style.DialogTheme, datePickerDialogListener, year, month, dayOfMonth).show()
    }

    private fun handleChooseTime() {
        var hoursOfDay = calendarHourOfDay()
        //clock set one minute earlier
        var minutes = calendarMinute() + 1
        if (minutes == 60) {
            minutes = 0
            hoursOfDay++
            if (hoursOfDay == 24) {
                hoursOfDay = 0
            }
        }
        TimePickerDialog(activity!!, R.style.DialogTheme, timePickerDialogListener, hoursOfDay, minutes, true).show()
    }

    private val datePickerDialogListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        Calendar.getInstance().also { calendar ->
            calendar.set(year, month, dayOfMonth)
            SimpleDateFormat("dd/MM/yyyy").format(calendar.time).also { date->
                textViewDatePreviewMain.text = date
            }
        }
    }

    private val timePickerDialogListener = TimePickerDialog.OnTimeSetListener { _, hoursOfDay, minute->
        Calendar.getInstance().also { calendar ->
            calendar.set(0, 0, 0, hoursOfDay, minute)
            SimpleDateFormat("HH:mm").format(calendar.time).also { time->
                textViewTimePreviewMain.text = time
            }
        }
    }

    private fun getItemDateCreated(): String {
        Calendar.getInstance().also { calendar ->
            SimpleDateFormat("dd/MM/yyyy").format(calendar.time).also { date->
                return date
            }
        }
    }

    private fun getItemTimeCreated(): String {
        Calendar.getInstance().also { calendar ->
            val hour = calendarHourOfDay()
            val minute = calendarMinute()
            calendar.set(0, 0, 0, hour, minute)
            SimpleDateFormat("HH:mm").format(calendar.time).also { time->
                return time
            }
        }
    }

    companion object {
        fun newInstance() =
            ItemInputFragment()
    }
}