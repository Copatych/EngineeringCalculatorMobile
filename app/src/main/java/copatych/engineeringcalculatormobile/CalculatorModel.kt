package copatych.engineeringcalculatormobile

import calculator.*
import android.content.Context
import java.io.File

class CalculatorModel private constructor(private val app: CalculatorApp) {

    companion object {
        var instance: CalculatorModel? = null
            private set

        fun initialize(context: Context) {
//            if (instance != null) return
            val app = CalculatorApp(deserializeFunctionsAdder(context))
            val localInstance = CalculatorModel(app)
            instance = localInstance
        }

        private fun deserializeFunctionsAdder(context: Context): FunctionsAdder {
            val file = File(context.filesDir, "functionsAdder")
            if (!file.exists()) {
                file.createNewFile()
            }
            val fileLines = file.readLines()
            if (fileLines.isNotEmpty()) {
                val serializedJSON = fileLines[0]
                return FunctionsAdder.deserialize(serializedJSON)
            }
            return FunctionsAdder()
        }

        private fun serializeFunctionsAdder(context: Context) {
            val file = File(context.filesDir, "functionsAdder")
            instance?.let { file.writeText(it.app.functionsAdder.serialize()) }
        }
    }

    val allFunctionsNames = app.functionsAdder.getFunctionsNames() +
            app.functionsDirector.functionsNames()

    val operationsNames = app.operationsDirector.operationsNames()

    fun process(expr: String): String {
        return app.process(expr)
    }

}