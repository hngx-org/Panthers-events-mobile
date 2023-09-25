package com.panther.events_app.fragment.create_event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.panther.events_app.R
import com.panther.events_app.databinding.FragmentCreateEventBinding
import java.util.Calendar

@Suppress("NAME_SHADOWING")
class CreateEventFragment : Fragment() {

    private var binding: FragmentCreateEventBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateEventBinding.inflate(inflater, container, false)


        binding!!.btnBack.setOnClickListener {

            val fragmentManager = requireActivity().supportFragmentManager

            fragmentManager.popBackStack()
        }

        binding?.linearLayoutStartTime?.setOnClickListener {
            clickStartTimePicker()
        }

        binding?.linearLayoutEndTime?.setOnClickListener {
            clickEndTimePicker()
        }

        binding?.linearLayoutStartDate?.setOnClickListener {
            clickStartDatePicker()
        }

        binding?.linearLayoutEndDate?.setOnClickListener {
            clickEndDatePicker()
        }



        binding?.btnDone?.setOnClickListener {
            Toast.makeText(requireContext(),"Button Clicked",Toast.LENGTH_SHORT).show()
        }





        return binding?.root
    }

    private fun clickEndTimePicker() {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(), { _, hour: Int, minutes  ->
            val selectedTime = "$hour: $minutes"
            binding?.tvEndTime?.text = selectedTime
        },
            hour,
            minute,
            //24 hour format
            true
        )
        timePickerDialog.show()


    }

    private fun clickStartTimePicker(){
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(), { _, hour: Int, minutes  ->
            val selectedTime = "$hour: $minutes"
            binding?.tvStartTime?.text = selectedTime
        },
            hour,
            minute,
            //24 hour format
            true
        )
        timePickerDialog.show()

    }

    private fun clickStartDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->
            val selectDate = "$day-${month + 1}-$year"
            binding?.tvStartDate?.text = selectDate

        },
            year,
            month,
            day)
        datePickerDialog.show()

    }

    private fun clickEndDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->
            val selectDate = "$day-${month + 1}-$year"
            binding?.tvEndDate?.text = selectDate

        },
            year,
            month,
            day)
        datePickerDialog.show()

    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}