package mods.eln.misc

import mods.eln.Eln
import java.lang.SecurityException
import java.lang.IllegalAccessException
import java.lang.NoSuchFieldException
import java.lang.ClassNotFoundException
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.text.DecimalFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.sqrt

object Utils {
    val d = arrayOfNulls<Any>(5)
    const val minecraftDay = (60 * 24).toDouble()
    val random = Random()
    const val burnTimeToEnergyFactor = 1.0
    const val voltageMageFactor = 0.1

    @JvmStatic
    var uuid = 1
        get() {
            if (field < 1) field = 1
            return field++
        }
        private set

    @JvmStatic
    fun rand(min: Double, max: Double): Double {
        return random.nextDouble() * (max - min) + min
    }

    @JvmStatic
    fun println(str: String?) {
        if (Eln.debugEnabled) System.out.println(str);
    }

    @JvmStatic
    fun println(str: Any?) {
        if (str != null) println(str.toString())
    }

    @JvmStatic
    fun println(format: String?, vararg data: Any?) {
        println(String.format(format!!, *data))
    }

    @JvmStatic
    fun floatToStr(f: Double, high: Int, low: Int): String {
        var temp = ""
        for (idx in 0 until high) temp += "0"
        temp = "$temp."
        for (idx in 0 until low) temp += "0"
        val str = DecimalFormat(temp).format(f)
        var idx = 0
        val ch = str.toCharArray()
        while (true) {
            if (str.length == idx) break
            if (ch[idx] == '.') {
                ch[idx - 1] = '0'
                break
            }
            if (ch[idx] != '0' && ch[idx] != ' ') break
            ch[idx] = '_'
            idx++
        }
        return String(ch)
    }

    @JvmStatic
    fun isTheClass(o: Any, c: Class<*>): Boolean {
        if (o.javaClass == c) return true
        var classIterator: Class<*>? = o.javaClass.superclass
        while (classIterator != null) {
            if (classIterator == c) {
                return true
            }
            classIterator = classIterator.superclass
        }
        return false
    }

    @JvmStatic
    val coalEnergyReference: Double
        get() = burnTimeToEnergyFactor * 80000.0

    @JvmStatic
    fun plotValue(value: Double): String {
        val valueAbs = abs(value)
        return when {
            valueAbs < 0.0001 ->
                "0"
            valueAbs < 0.000999 ->
                String.format("%1.2fµ", value * 10_000)
            valueAbs < 0.00999 ->
                String.format("%1.2fm", value * 1_000)
            valueAbs < 0.0999 ->
                String.format("%2.1fm", value * 1_000)
            valueAbs < 0.999 ->
                String.format("%3.0fm", value * 1_000)
            valueAbs < 9.99 ->
                String.format("%1.2f", value)
            valueAbs < 99.9 ->
                String.format("%2.1f", value)
            valueAbs < 999 ->
                String.format("%3.0f", value)
            valueAbs < 9999 ->
                String.format("%1.2fk", value / 1_000.0)
            valueAbs < 99999 ->
                String.format("%2.1fk", value / 1_000.0)
            valueAbs < 999999 ->
                String.format("%3.0fK", value / 1_000.0)
            valueAbs < 9999999 ->
                String.format("%1.2fM", value / 1_000_000.0)
            valueAbs < 99999999 ->
                String.format("%2.1fM", value / 1_000_000.0)
            else ->
                String.format("%3.0fM", value / 1_000_000.0)
        }
    }

    @JvmStatic
    fun plotValue(value: Double, unit: String): String {
        return plotValue(value) + unit
    }

    @JvmStatic
    fun plotVolt(value: Double): String {
        return plotValue(value, "V  ")
    }

    @JvmStatic
    fun plotVolt(header: String, value: Double): String {
        var z = header
        if (z != "") z += " "
        return z + plotVolt(value)
    }

    @JvmStatic
    fun plotAmpere(value: Double): String {
        return plotValue(value, "A  ")
    }

    @JvmStatic
    fun plotAmpere(header: String, value: Double): String {
        var z = header
        if (z != "") z += " "
        return z + plotAmpere(value)
    }

    @JvmStatic
    fun plotPercent(header: String, value: Double): String {
        var s = header
        if (s != "") s += " "
        return if (value >= 1.0) s + String.format("%3.0f", value * 100.0) + "%   " else s + String.format("%3.1f", value * 100.0) + "%   "
    }

    @JvmStatic
    fun plotEnergy(value: Double): String {
        return plotValue(value, "J  ")
    }

    @JvmStatic
    fun plotEnergy(header: String, value: Double): String {
        var s = header
        if (s != "") s += " "
        return s + plotEnergy(value)
    }

    @JvmStatic
    fun plotRads(header: String, value: Double): String {
        var string = header
        if (string != "") string += " "
        return string + plotValue(value, "rad/s ")
    }

    @JvmStatic
    fun plotER(E: Double, R: Double): String {
        return plotEnergy("E", E) + plotRads("R", R)
    }

    @JvmStatic
    fun plotPower(value: Double): String {
        return plotValue(value, "W  ")
    }

    @JvmStatic
    fun plotPower(header: String, value: Double): String {
        var s = header
        if (s != "") s += " "
        return s + plotPower(value)
    }

    @JvmStatic
    fun plotOhm(value: Double): String {
        return plotValue(value, "\u2126 ")
    }

    @JvmStatic
    fun plotOhm(header: String, value: Double): String {
        var s = header
        if (s != "") s += " "
        return s + plotOhm(value)
    }

    @JvmStatic
    fun plotUIP(U: Double, I: Double): String {
        return plotVolt("U", U) + plotAmpere("I", I) + plotPower("P", Math.abs(U * I))
    }

    @JvmStatic
    fun plotUIP(U: Double, I: Double, R: Double): String {
        return plotVolt("U", U) + plotAmpere("I", I) + plotPower("P", I * I * R)
    }

    @JvmStatic
    fun plotTime(value: Double): String {
        var value = value
        var str = ""
        val h: Int
        val mn: Int
        val s: Int
        if (value == 0.0) return str + "0''"
        h = (value / 3600).toInt()
        value = value % 3600
        mn = (value / 60).toInt()
        value = value % 60
        s = (value / 1).toInt()
        if (h != 0) str += h.toString() + "h"
        if (mn != 0) str += "$mn'"
        if (s != 0) str += "$s''"
        return str
    }

    @JvmStatic
    fun plotTime(header: String, value: Double): String {
        var header = header
        if (header != "") header += " "
        return header + plotTime(value)
    }

    @JvmStatic
    fun plotBuckets(header: String, buckets: Double): String {
        var header = header
        if (header != "") header += " "
        return header + plotValue(buckets, "B ")
    }

    fun voltageMargeFactorSub(value: Double): Double {
        if (value > 1 + voltageMageFactor) {
            return value - voltageMageFactor
        } else if (value > 1) {
            return 1.0
        }
        return value
    }

    @JvmStatic
    fun modbusToShort(outputNormalized: Double, i: Int): Short {
        val bit = java.lang.Float.floatToRawIntBits(outputNormalized.toFloat())
        return if (i == 1) bit.toShort() else (bit ushr 16).toShort()
    }

    @JvmStatic
    fun modbusToFloat(first: Short, second: Short): Float {
        val bit = (first.toInt() and 0xFFFF shl 16) + (second.toInt() and 0xFFFF)
        return java.lang.Float.intBitsToFloat(bit)
    }

    @JvmStatic
    fun getLength(x: Double, y: Double, z: Double, tx: Double, ty: Double, tz: Double): Double {
        val dx: Double = tx - x
        val dy: Double = ty - y
        val dz: Double = tz - z
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    fun <T> readPrivateInt(o: Any, feildName: String?): Int {
        try {
            val f = o.javaClass.getDeclaredField(feildName)
            f.isAccessible = true
            return f.getInt(o)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return 0
    }

    fun <T> readPrivateDouble(o: Any, feildName: String?): Double {
        try {
            val f = o.javaClass.getDeclaredField(feildName)
            f.isAccessible = true
            return f.getDouble(o)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return 0.0
    }

    @JvmStatic
    fun nullCheck(o: Any?): Boolean {
        return o == null
    }

    fun nullFatal(o: Any?) {
        if (o == null) fatal()
    }

    @JvmStatic
    fun fatal() {
        try {
            throw Exception()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isClassLoaded(name: String?): Boolean {
        try {
            val cc = Class.forName(name)
            return true
        } catch (e: ClassNotFoundException) {
        }
        return false
    }

    @JvmStatic
    fun plotSignal(U: Double): String {
        return plotVolt("U", U) + plotPercent("Value", U / 50)
    }

    @JvmStatic
    fun limit(value: Float, min: Float, max: Float): Float {
        return Math.max(Math.min(value, max), min)
    }

    @JvmStatic
    fun limit(value: Double, min: Double, max: Double): Double {
        return Math.max(Math.min(value, max), min)
    }
}
