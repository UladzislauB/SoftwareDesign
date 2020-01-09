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
    private lateinit var expression: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val fragmentView = inflater.inflate(R.layout.fragment_scientific_mode, container, false);
        screen = activity?.findViewById(R.id.screen)
        expression = (screen as EditText).text.toString()
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
        val cbrt = fragmentView.findViewById<Button>(R.id.btn_cbrt)
        val lg = fragmentView.findViewById<Button>(R.id.btn_lg)
        val ln = fragmentView.findViewById<Button>(R.id.btn_ln)
        val acos = fragmentView.findViewById<Button>(R.id.btn_acos)
        val sqrt = fragmentView.findViewById<Button>(R.id.btn_sqrt)
        val asin = fragmentView.findViewById<Button>(R.id.btn_asin)
        val exp = fragmentView.findViewById<Button>(R.id.btn_e)
        val tan = fragmentView.findViewById<Button>(R.id.btn_tan)
        val sc_cl = fragmentView.findViewById<Button>(R.id.btn_sc_cl)
        val sc_op = fragmentView.findViewById<Button>(R.id.btn_sc_op)

        val clickableViews: List<View> =
            listOf(sin, cos, cbrt, lg, ln, acos, sqrt, asin, exp, tan, sc_cl, sc_op)

        for (item in clickableViews) {
            item.setOnClickListener {
                when(item){
                    cbrt -> updateScreen("cbrt(")
                }
            }
        }
    }


}
