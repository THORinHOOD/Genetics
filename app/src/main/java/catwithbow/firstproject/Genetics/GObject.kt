package catwithbow.firstproject.Genetics

import java.util.*
import kotlin.math.pow

class GObject {

    companion object {
        fun randomString(length : Int) : String {
            var res : String = ""
            val alphabet : String = "QWERTYUIOPLKJHGFDSAZXCVBNMqwertyuioplkjhgfdsazxcvbnm,.:;[]{}#@$!&?+=- 1234567890/*\\<>\"№%^()_"
            val random = Random()

            for (i in 1..length) {
                var current = random.nextInt(alphabet.length)
                res += alphabet[current]
            }
            return res
        }
    }

    private var dna : String
    private var fitness : Double = 0.0

    fun DNA() : String { return dna }
    fun Fitness() : Double { return fitness }

    constructor(length : Int) {
        dna = randomString(length);
    }

    constructor(dna : String) {
        this.dna = dna
    }

    constructor(dna : String, target : String) {
        this.dna = dna
        countFitness(target)
    }

    fun countFitness(target : String) {
        fitness = 1.0
        for (i in 0..dna.length-1) {
            if (dna[i] == target[i]) {
                fitness += 1
            }
        }
        fitness = fitness.pow(4)
    }

    fun normalizeFitness(sum : Double) {
        fitness /= sum
    }

    override fun toString(): String {
        return dna + " -> " + fitness;
    }
}