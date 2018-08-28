package catwithbow.firstproject.Genetics

import android.util.Log
import catwithbow.firstproject.MainActivity
import java.util.*

class Evolution : Thread() {

    val debug : String = "Evolution"

    var population : MutableList<GObject> = mutableListOf<GObject>()

    var targetPhrase : String = ""
    var populationCount : Int = 0
    var mutationRate : Double = 0.0

    var generationNum : Int = 0
    var goOn = true

    val random = Random()

    var act : MainActivity? = null

    fun setUpValues(targetPhrase : String, populationCount : Int, mutationRate : Double) {
        this.targetPhrase = targetPhrase
        this.populationCount = populationCount
        this.mutationRate = mutationRate
        generationNum = 0
    }

    fun setUpUI(act : MainActivity) {
        this.act = act
    }

    override fun run() {
        population = mutableListOf<GObject>()
        generateFirstPopulation()

        goOn = true

        while(goOn)
            evolutionStep()
    }

    private fun evolutionStep() {
        generationNum++

        val best : GObject = findBest()
        act!!.setNewValues(best.DNA(), generationNum.toString())

        if (best.DNA() == targetPhrase)
            goOn = false

        makeNewPopulation()
    }

    private fun printPopulation() {
        var toPrint : String = "\n"

        toPrint += "Population #" + generationNum.toString() + "\n"
        for (i in 0..populationCount-1)
            toPrint +=  population[i].toString() + "\n"

        val best = findBest()
        toPrint += "BEST : " + best.toString() + "\n"
        Log.d(debug, toPrint);
    }

    private fun findBest() : GObject {
        var best : GObject = population[0]
        for (i in 1..populationCount-1)
            if (best.Fitness().compareTo(population[i].Fitness()) <= 0) {
                best = population[i]
            }
        return best
    }

    private fun makeNewPopulation() {
        var newPopulation : MutableList<GObject> = mutableListOf<GObject>()
        for (i in 0..populationCount-1)
            newPopulation.add(makeNewObject())
        population.clear()
        population = newPopulation
        var sum : Double = 0.0
        for (i in 0..populationCount-1)
            sum += population[i].Fitness()
        for (i in 0..populationCount-1)
            population[i].normalizeFitness(sum)
    }

    private fun makeNewObject() : GObject {
        var first = selectOne()
        var second = selectOne()
        return crossover(first, second)
    }

    private fun crossover(first : GObject, second : GObject) : GObject {
        var newDna : String = ""
        for (i in 0..targetPhrase.length - 1)
            if (i % 2 == 0)
                newDna += first.DNA().substring(i..i)
            else
                newDna += second.DNA().substring(i..i)

        for (i in 0..targetPhrase.length-1) {
            var current = random.nextDouble()
            if (current.compareTo(mutationRate) <= 0) {
                newDna = newDna.replaceRange(i..i, GObject.randomString(1))
            }
        }
        return GObject(newDna, targetPhrase)
    }

    private fun selectOne() : GObject {
        var num : Double = random.nextDouble()
        var index = 0
        while(num.compareTo(population[index].Fitness()) >= 0) {
            num -= population[index].Fitness()
            index++
        }
        return population[index]
    }


    private fun generateFirstPopulation() {
        var sum : Double = 0.0
        for (i in 0..populationCount-1) {
            population.add(GObject(targetPhrase.length))
            population[i].countFitness(targetPhrase)
            sum += population[i].Fitness()
        }

        for (i in 0..populationCount-1)
            population[i].normalizeFitness(sum)
    }
}