package ejercicios
import redis.clients.jedis.Jedis
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    val sc = Scanner(System.`in`)
    val con = Jedis("localhost")
    con.connect()
    val allKeys = con.keys("*")
    val keyList = ArrayList<String>()
    keyList.addAll(allKeys)
    var index = 0
    for (key in allKeys) {
        index++
        val typeKey = con.type(key)
        println("${index}.- ${key} (${typeKey})")
    }

    var boo = 1
    do {
        println("Introdueix un número (0 per a eixir)")
        boo = sc.nextLine().toInt()
        val key = keyList.get(boo - 1)
        val type = con.type(key)

        when (type) {
            "string" -> StringType.print(key, con)
            "set" -> SetType.print(key, con)
            "zset" -> ZSetType.print(key, con)
            "hash" -> HashType.print(key, con)
            "list" -> ListType.print(key, con)
        }

    } while (boo != 0)
    con.close()
}

object StringType {
    fun print(key: String, con: Jedis) = println(con.get(key))
}

object HashType {
    fun print(key: String, con: Jedis) {
        val keys = con.hgetAll(key)
        println(key)
        for (keyValue in keys) {
            println("\t${keyValue.key} --> ${keyValue.value}")
        }

    }
}

object ListType {
    fun print(key: String, con: Jedis) {
        val set = con.lrange(key, 0, -1)
        println(key)
        for (value in set) {
            println("\t${value}")
        }

    }
}

object SetType {
    fun print(key: String, con: Jedis) {
        val set = con.smembers(key)
        println(key)
        for (value in set) {
            println("\t${value}")
        }

    }
}

object ZSetType {
    fun print(key: String, con: Jedis) {
        val set = con.zrangeWithScores(key, 0, -1)
        println(key)
        for (t in set) {
            println("\t${t.element} --> ${t.score}")
        }

    }
}
