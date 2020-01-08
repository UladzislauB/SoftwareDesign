package com.example.calculator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

/**
 * A simple [Fragment] subclass.
 */
class ScientificModeFragment : Fragment() {

    private var screen: EditText? = null
    private var expression: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val fragmentView = inflater.inflate(R.layout.fragment_scientific_mode, container, false);
        screen = activity?.findViewById(R.id.screen)

        setListeners(fragmentView)
        return fragmentView
    }

    private fun updateScreen(string: String) {
        expression += string
        screen?.setText(expression)
    }

    private fun setListeners(fragmentView: View) {
        val sin = fragmentView.findViewById<Button>(R.id.btn_sin)
        val cos = fragmentView.findViewById<Button>(R.id.btn_cos)
        val percent = fragmentView.findViewById<Button>(R.id.btn_percent)
        val log = fragmentView.findViewById<Button>(R.id.btn_log)
        val ln = fragmentView.findViewById<Button>(R.id.btn_ln)
        val fact = fragmentView.findViewById<Button>(R.id.btn_fact)
        val sqrt = fragmentView.findViewById<Button>(R.id.btn_sqrt)
        val pow = fragmentView.findViewById<Button>(R.id.btn_pow)
        val exp = fragmentView.findViewById<Button>(R.id.btn_e)
        val pi = fragmentView.findViewById<Button>(R.id.btn_pi)
        val sc_cl = fragmentView.findViewById<Button>(R.id.btn_sc_cl)
        val sc_op = fragmentView.findViewById<Button>(R.id.btn_sc_op)

        val clickableViews: List<View> =
            listOf(sin, cos, percent, log, ln, fact, sqrt, pow, exp, pi, sc_cl, sc_op)

        for (item in clickableViews) {
            item.setOnClickListener {
                when (item) {
                    sin, cos, log, ln, exp, sqrt -> updateScreen((item as Button).text.toString() + "(")
                    else -> updateScreen((item as Button).text.toString())
                }
            }
        }
    }


}
