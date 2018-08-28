package catwithbow.firstproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import catwithbow.firstproject.Genetics.Evolution
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var thread : Evolution? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setNewValues(best : String, generation : String) {
        this@MainActivity.runOnUiThread(java.lang.Runnable {
            label_best_object.text = best
            label_generation.text = generation
        })
    }

    fun startEvolution(view : View) {
        if (thread != null)
            thread!!.interrupt()
        thread = null
        thread = Evolution()
        thread!!.setUpValues(phrase.text.toString(),  population.text.toString().toInt(), mutation.text.toString().toDouble())
        thread!!.setUpUI(this)
        thread!!.start()
    }

}
