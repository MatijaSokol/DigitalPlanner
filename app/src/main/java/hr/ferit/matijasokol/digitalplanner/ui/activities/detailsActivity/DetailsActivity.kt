package hr.ferit.matijasokol.digitalplanner.ui.activities.detailsActivity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import hr.ferit.matijasokol.digitalplanner.R
import hr.ferit.matijasokol.digitalplanner.extensions.*
import hr.ferit.matijasokol.digitalplanner.model.Item
import hr.ferit.matijasokol.digitalplanner.notifications.alarm.AlarmHelperImpl
import hr.ferit.matijasokol.digitalplanner.presenters.DetailsActivityPresenter
import hr.ferit.matijasokol.digitalplanner.ui.activities.base.BaseActivity
import hr.ferit.matijasokol.digitalplanner.ui.activities.mainActivity.MainActivity
import hr.ferit.matijasokol.digitalplanner.ui.fragments.savedItemsFragment.ITEM_EXTRA
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.toolbar_details.*
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : BaseActivity(R.layout.activity_details), DetailsActivityContract.View {

    private var itemId: Long = 0
    private lateinit var item: Item
    private val presenter: DetailsActivityContract.Presenter by lazy { DetailsActivityPresenter(this) }

    override fun setUpUi() {
        setSupportActionBar(toolbarDetailsToolbar as Toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        intent.getLongExtra(ITEM_EXTRA, -1).also {
            if (it == -1L) {
                onBackPressed()
            } else {
                itemId = it
                presenter.getItemById(itemId)
            }
        }
    }

    override fun onGetItemSuccess(item: Item) {
        this.item = item
        setViews()
        setEditTextListener()
        setListeners()
    }

    override fun onGetItemFailure() {
        displayToast(getString(R.string.error))
        onBackPressed()
    }

    private fun setViews() {
        textViewInformation.text = getString(R.string.note_created_at, item.dateCreated, item.timeCreated)
        textViewSavedDate.text = item.date
        textViewSavedTime.text = item.time
        textViewSavedNote.text = item.note

        textViewNotePreviewDetails.text = item.note
        textViewDatePreviewDetails.text = item.date
        textViewTimePreviewDetails.text = item.time
    }

    private fun setListeners() {
        buttonSaveChangedItem.setOnClickListener { handleUpdateItem() }
        buttonDeleteItem.setOnClickListener { handleDeleteItem() }
        imageViewCalendarDetails.setOnClickListener { handleChooseDate() }
        imageViewClockDetails.setOnClickListener { handleChooseTime() }
    }

    private fun setEditTextListener() {
        editTextEditNote.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                text?.let { text ->
                    if (text.isEmpty()) {
                        textViewNotePreviewDetails.text = getString(R.string.note_preview)
                    } else {
                        textViewNotePreviewDetails.text = text.toString().trim()
                    }
                }
            }
        })
    }

    private fun handleDeleteItem() {
        presenter.deleteItem(itemId)
        AlarmHelperImpl.cancelAlarm(this, item)
        displayToast(getString(R.string.item_deleted))
        onBackPressed()
    }

    private fun handleUpdateItem() {
        if (isDataEntered()) {
            if (!isSameData()) {
                val noteText = textViewNotePreviewDetails.text.toString()
                val dateText = textViewDatePreviewDetails.text.toString()
                val timeText = textViewTimePreviewDetails.text.toString()

                presenter.updateItem(Item(itemId, noteText, dateText, timeText, item.dateCreated, item.timeCreated))
                AlarmHelperImpl.setAlarm(this, Item(itemId, noteText, dateText, timeText, item.dateCreated, item.timeCreated))

                displayToast(getString(R.string.note_updated))
            }
            onBackPressed()
        } else {
            displayToast(getString(R.string.data_missing))
        }
    }

    private fun isDataEntered(): Boolean {
        if (textViewNotePreviewDetails.stringText() == getString(R.string.note_preview)) {
            return false
        }

        return true
    }

    private fun isSameData(): Boolean {
        val oldNoteText = textViewSavedNote.stringText()

        val noteText = textViewNotePreviewDetails.stringText()

        if (oldNoteText == noteText && isTimeDateSame()) {
            return true
        }

        return false
    }

    private fun isTimeDateSame(): Boolean {
        val oldNoteDate = textViewSavedDate.stringText()
        val oldNoteTime = textViewSavedTime.stringText()

        val dateText = textViewDatePreviewDetails.stringText()
        val timeText = textViewTimePreviewDetails.stringText()

        if (oldNoteDate == dateText && oldNoteTime == timeText) {
            return true
        }

        return false
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
        TimePickerDialog(this@DetailsActivity, R.style.DialogTheme, timePickerDialogListener, hoursOfDay, minutes, true).show()
    }

    private fun handleChooseDate() {
        val year = calendarYear()
        val month = calendarMonth()
        val dayOfMonth = calendarDayOfMonth()
        DatePickerDialog(this@DetailsActivity, R.style.DialogTheme, datePickerDialogListener, year, month, dayOfMonth).show()
    }

    private val datePickerDialogListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        Calendar.getInstance().also { calendar ->
            calendar.set(year, month, dayOfMonth)
            SimpleDateFormat("dd/MM/yyyy").format(calendar.time).also { date->
                textViewDatePreviewDetails.text = date
            }
        }
    }

    private val timePickerDialogListener = TimePickerDialog.OnTimeSetListener { _, hoursOfDay, minute->
        Calendar.getInstance().also { calendar ->
            calendar.set(0, 0, 0, hoursOfDay, minute)
            SimpleDateFormat("HH:mm").format(calendar.time).also { time->
                textViewTimePreviewDetails.text = time
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openMainActivity() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    override fun onBackPressed() {
        if (isTaskRoot) {
            openMainActivity()
        }
        else {
            super.onBackPressed()
        }
    }
}
