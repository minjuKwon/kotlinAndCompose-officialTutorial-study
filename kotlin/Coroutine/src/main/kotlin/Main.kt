import kotlin.system.*
import kotlinx.coroutines.*

fun main() {
    val time=measureTimeMillis{
        runBlocking {
            println("Weather forecast")
        /*    launch {
                printForecast()
            }
            launch {
                printTemperature()
            }*/

/*            val forecast: Deferred<String> = async{
                getForecast()
            }
            val temperature: Deferred<String> = async {
                getTemperature()
            }
            println("${forecast.await()} ${temperature.await()}")*/

         /*   try{
                println(getWeatherReport())
            }catch (e: java.lang.AssertionError){
                println("Caught exception in runBlocking(): $e")
                println("Report unavailable at this time")
            }*/

            println(getWeatherReport())
            println("Have a good day!")
        }
    }

    println("Execution time: ${time/1000.0} seconds")

    runBlocking {
        println("${Thread.currentThread().name} - runBlocking function")
        launch {
            println("${Thread.currentThread().name} - launch function")
            withContext(Dispatchers.Default){
                println("${Thread.currentThread().name} - withContext function")
                delay(1000)
                println("10 results found.")
            }
            println("${Thread.currentThread().name} - end of launch function")
        }
        println("Loading...")
    }

}

suspend fun printForecast() {
    delay(1000)
    print( "Sunny")
}

suspend fun printTemperature() {
    delay(1000)
    throw AssertionError("Temperature is invalid")
    print( "30\u00b0C")
}

suspend fun getForecast(): String {
    delay(1000)
    return "Sunny"
}

suspend fun getTemperature(): String {
    delay(500)
    throw AssertionError("Temperature is invalid")
    return "30\u00b0C"
}

suspend fun getWeatherReport() = coroutineScope {
    val forecast = async { getForecast() }
  /*  val temperature = async {
        try{
            getTemperature()
        }catch (e: java.lang.AssertionError){
            println("Caught exception $e")
            "{No temperature found}"
        }
    }*/
    val temperature = async { getTemperature() }
    delay(200)
    temperature.cancel()
    //"${forecast.await()} ${temperature.await()}"
    "${forecast.await()}"
}