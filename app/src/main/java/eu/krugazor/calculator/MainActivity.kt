package eu.krugazor.calculator

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var resultTxt: TextView? = null
    private val brain = CalcBrain() // ?
    private var clearOnType = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val result = findViewById<TextView>(R.id.result)
        resultTxt = result

        val numberClickListener = View.OnClickListener { v ->
            val b = v as Button
            if (!clearOnType)
            // ?
                result.text = result.text.toString() + b.text.toString()
            else {
                result.text = b.text.toString()
                clearOnType = false
            }
        }

        findViewById<View>(R.id.b0).setOnClickListener(numberClickListener)
        findViewById<View>(R.id.b1).setOnClickListener(numberClickListener)
        findViewById<View>(R.id.b2).setOnClickListener(numberClickListener)
        findViewById<View>(R.id.b3).setOnClickListener(numberClickListener)
        findViewById<View>(R.id.b4).setOnClickListener(numberClickListener)
        findViewById<View>(R.id.b5).setOnClickListener(numberClickListener)
        findViewById<View>(R.id.b6).setOnClickListener(numberClickListener)
        findViewById<View>(R.id.b7).setOnClickListener(numberClickListener)
        findViewById<View>(R.id.b8).setOnClickListener(numberClickListener)
        findViewById<View>(R.id.b9).setOnClickListener(numberClickListener)

        // special case, only one . allowed
        findViewById<View>(R.id.bDot).setOnClickListener(View.OnClickListener {
            if (result.getText().toString().contains("."))
                return@OnClickListener  // ?

            result.text = result.text.toString() + "."
        })

        val opListener = View.OnClickListener { v ->
            val b = v as Button
            if (clearOnType) {
                brain.replaceOperation(b.text.toString()) // ?
                return@OnClickListener
            }
            brain.pushValue(java.lang.Double.parseDouble(result.text.toString()))
            brain.calc()
            val br = brain.getValue()
            result.text = "$br"

            brain.pushOperation(b.text.toString()) // ?
            clearOnType = true
        }

        val unopListener = View.OnClickListener { v ->
            val b = v as Button
            if (!clearOnType)
                brain.pushValue(java.lang.Double.parseDouble(result.text.toString())) // ?
            brain.pushOperation(b.text.toString())
            brain.calc()
            val br = brain.getValue()
            result.text = "$br"
            clearOnType = true
        }

        // binary operations
        findViewById<View>(R.id.bPlus).setOnClickListener(opListener)
        findViewById<View>(R.id.bMoins).setOnClickListener(opListener)
        findViewById<View>(R.id.bMult).setOnClickListener(opListener)
        findViewById<View>(R.id.bDiv).setOnClickListener(opListener)

        // unary operations
        findViewById<View>(R.id.bcos).setOnClickListener(unopListener)
        findViewById<View>(R.id.bSin).setOnClickListener(unopListener)
        findViewById<View>(R.id.bSqrt).setOnClickListener(unopListener)

        // clear
        findViewById<View>(R.id.bClear).setOnClickListener {
            brain.clear()
            result.text = ""
        }

        // equals
        findViewById<View>(R.id.bEq).setOnClickListener {
            brain.pushValue(java.lang.Double.parseDouble(result.text.toString()))
            brain.calc()
            val v = brain.getValue()
            result.text = "$v"
            clearOnType = true
        }
    }
}
