package ejemplos



import redis.clients.jedis.Jedis

fun main(){
    val con = Jedis("localhost")
    con.connect()

    val ll = con.lrange("lista1", 0, -1)  // ll és un MutableList
    for (e in ll)
        println(e)

    con.close()
}

