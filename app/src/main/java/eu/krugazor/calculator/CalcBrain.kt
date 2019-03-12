package eu.krugazor.calculator

import java.util.*

public class CalcBrain {
    interface Operation {
        fun evaluate() : Unit
    }

    private var values = Stack<Double>()
    private var ops = Stack<Operation>()

    public fun clear() {
        values.clear()
        ops.clear()
    }

    public fun getValue() : Double {
        if(values.size > 0) {
            return values.peek()
        } else {
            return 0.0
        }
    }

    public fun pushValue(d: Double) {
        values.push(d)
    }

    public fun pushOperation(s: String) {
        when(s) {
            "+" -> ops.push(object : Operation {
                override fun evaluate() {
                    if(values.size > 1) {
                        values.push(values.pop() + values.pop())
                    }
                }
            })
            "-" -> ops.push(object : Operation {
                override fun evaluate() {
                    if(values.size > 1) {
                        val lastval = values.pop()
                        val prevval = values.pop()
                        values.push( prevval - lastval )
                    }
                }
            })
            "*" -> ops.push(object : Operation {
                override fun evaluate() {
                    if(values.size > 1) {
                        values.push(values.pop() + values.pop())
                    }
                }
            })
            "/" -> ops.push(object : Operation {
                override fun evaluate() {
                    if(values.size > 1) {
                        val lastval = values.pop()
                        val prevval = values.pop()
                        values.push( prevval / lastval )
                    }
                }
            })
            "√" -> ops.push(object : Operation {
                override fun evaluate() {
                    if(values.size > 0) {
                        values.push(Math.sqrt(values.pop()))
                    }
                }
            })
            "cos" -> ops.push(object : Operation {
                override fun evaluate() {
                    if(values.size > 0) {
                        values.push(Math.cos(values.pop()))
                    }
                }
            })
            "sin" -> ops.push(object : Operation {
                override fun evaluate() {
                    if(values.size > 0) {
                        values.push(Math.sin(values.pop()))
                    }
                }
            })
        }
    }

    public fun replaceOperation(s : String) {
        if(ops.size > 0) {
            ops.pop()
        }
        pushOperation(s)
    }

    public fun calc() {
        if(ops.size > 0) {
            val op = ops.pop()
            op.evaluate()
        }
    }
}