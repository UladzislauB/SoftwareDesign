package com.example.calculator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 */
class BasicModeFragment : Fragment() {

    private var screen: EditText? = null
    private var expression: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentView = inflater.inflate(R.layout.fragment_basic_mode, container, false);
        screen = activity?.findViewById(R.id.screen)

        setListeners(fragmentView)

        val buttonDelete = fragmentView.findViewById<TextView>(R.id.btn_del)
        buttonDelete.setOnClickListener { onDelete() }
        buttonDelete.setOnLongClickListener { onLongClickDelete(it) }
        return fragmentView
    }

                private fun updateScreen(string: String) {
                    expression += string
                    screen?.setText(expression)
                }

    private fun onDelete() {
        expression = expression.dropLast(1)
        updateScreen("")
    }

    private fun onLongClickDelete(view: View): Boolean{
        if(view.id == R.id.btn_del){
            expression = ""
            screen?.setText("")
            return true
        }
        return false
    }

    private fun setListeners(fragmentView: View) {
        val zero = fragmentView.findViewById<TextView>(R.id.btn_0)
        val one = fragmentView.findViewById<TextView>(R.id.btn_1)
        val two = fragmentView.findViewById<TextView>(R.id.btn_2)
        val three = fragmentView.findViewById<TextView>(R.id.btn_3)
        val four = fragmentView.findViewById<TextView>(R.id.btn_4)
        val five = fragmentView.findViewById<TextView>(R.id.btn_5)
        val six = fragmentView.findViewById<TextView>(R.id.btn_6)
        val seven = fragmentView.findViewById<TextView>(R.id.btn_7)
        val eight = fragmentView.findViewById<TextView>(R.id.btn_8)
        val nine = fragmentView.findViewById<TextView>(R.id.btn_9)

        val div = fragmentView.findViewById<TextView>(R.id.btn_div)
        val mul = fragmentView.findViewById<TextView>(R.id.btn_mul)
        val add = fragmentView.findViewById<TextView>(R.id.btn_add)
        val sub = fragmentView.findViewById<TextView>(R.id.btn_sub)
        val dot = fragmentView.findViewById<TextView>(R.id.btn_dot)

        val clickableViews: List<TextView?> = listOf(
            zero, one, two, three, four, dot,
            five, six, seven, eight, nine, div, mul, add, sub
        )

        for (item in clickableViews) {
            item?.setOnClickListener {
                updateScreen((it as TextView).text.toString())
            }
        }
    }
}
