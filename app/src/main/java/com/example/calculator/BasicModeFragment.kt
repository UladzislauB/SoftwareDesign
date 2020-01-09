package com.example.calculator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception


interface DataInterface {
    fun setResult(needClear: Boolean)
}


/**
 * A simple [Fragment] subclass.
 */
class BasicModeFragment : Fragment(), DataInterface {


    private var screen: EditText? = null
    private lateinit var expression: String
    var needClear: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentView = inflater.inflate(R.layout.fragment_basic_mode, container, false);
        screen = activity?.findViewById(R.id.screen)
        setListeners(fragmentView)

        val buttonDelete = fragmentView.findViewById<TextView>(R.id.btn_del)
        buttonDelete.setOnClickListener {
            if (needClear) clear()
            onDelete()
        }
        buttonDelete.setOnLongClickListener {
                        onLongClickDelete(it)
        }

        val eq = fragmentView.findViewById<TextView>(R.id.btn_eq)
        eq.setOnClickListener {

            Evaluate(screen)
        }

        return fragmentView
    }


    override fun setResult(needClear: Boolean) {
        this.needClear = needClear
    }

    private fun clear() {
        expression = ""
        screen?.setText("")
    }

    private fun updateScreen(string: String) {
        expression = screen?.text.toString()
        expression += string
        screen?.setText(expression)
    }

    private fun onDelete() {

        expression = (screen as EditText).text.toString().dropLast(1)
        (screen as EditText).setText(expression)
    }

    private fun onLongClickDelete(view: View): Boolean {
        if (view.id == R.id.btn_del) {
            clear()
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
                if (needClear) {
                    screen?.setText("")
                    expression = ""
                    needClear = false
                    (activity as DataInterface).setResult(needClear)
                }
                updateScreen((it as TextView).text.toString())
            }
        }
    }


    private fun Evaluate(screen: View?) {
        val text = (screen as EditText).text.toString()
        try {
            val e = ExpressionBuilder(text).build()
            val check = e.validate()
            if (check.isValid) {
                val result = e.evaluate().toString()
                HistorySingleton.pages.add(Page(expression, result))
                expression = result
                screen.setText(expression)
            } else {
                val errors = check.errors.joinToString(", ")
                HistorySingleton.pages.add(Page(expression, errors))
                expression = errors
                screen.setText(expression)

            }

        } catch (exception: Exception) {
            HistorySingleton.pages.add(Page(expression, "Bad expression"))
            expression = ""
            screen.setText("Bad expression")
        }
        needClear = true
        (activity as DataInterface).setResult(needClear)
    }
}
