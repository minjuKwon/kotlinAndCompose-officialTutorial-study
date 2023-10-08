fun main() {
	
	//1
	println("#1")
	val morningNotification=51
	val eveningNotification=135
	printNotificationSummary(morningNotification)
	printNotificationSummary(eveningNotification)
	println()
	
	//2
	println("#2")
	val child=5
	val adult=28
	val senior=87
	val isMonday=true
	println("The Movie ticket price for a person aged $child is \$${ticketPrice(child, isMonday)}.")
	println("The Movie ticket price for a person aged $adult is \$${ticketPrice(adult, isMonday)}.")
	println("The Movie ticket price for a person aged $senior is \$${ticketPrice(senior, isMonday)}.")
	println()
	
	//3
	println("#3")
	printFinalTemperature(27.0, "Celsius", "Fahrenheit"){9.0/5.0*it+32}
	printFinalTemperature(350.0, "Kelvin", "Celsius"){it-273.15}
	printFinalTemperature(10.0, "Fahrenheit", "Kelvin"){5.0/9.0*(it-32)+273.15}
	println()
	
	//4
	println("#4")
	val song=Song("We Don't Talk About Bruno", "Encanto Cast", 2022, 1_000_000)
	song.printInfo()
	println(song.isFamous)
	println()
	
	//5
	println("#5")
	val amanda=Person("Amanda", 33, "play tennis", null)
	val atiqah=Person("Atiqah", 28, "climb", amanda)
	amanda.showProfile()
	atiqah.showProfile()
	println()
	
	//6
	println("#6")
	val foldablePhone=FoldablePhone()
	foldablePhone.switchOn()
	foldablePhone.checkPhoneScreenLight()
	foldablePhone.unfold()
	foldablePhone.switchOn()
	foldablePhone.checkPhoneScreenLight()
	println()
	
	//7
	println("#7")
	val winningBid=Bid(5000,"Private Collector")
	println("Item A is sold at ${auctionPrice(winningBid,2000)}.")
	println("Item B is sold at ${auctionPrice(null, 3000)}.")
	println()
	
}

//1
fun printNotificationSummary(numberOfMessages: Int){
	if(numberOfMessages<100){
		println("You have $numberOfMessages notifications")
	}else{
		println("Your phone is blowing up! You have 99+ notifications.")
	}
}

//2
fun ticketPrice(age: Int, isMonday: Boolean): Int{
	return when(age){
		in 0..12->15
		in 13..60->if(isMonday) 25 else 30
		in 61..100->20
		else ->-1
	}
}
	
//3
fun printFinalTemperature(
		initialMeasurement: Double,
		initialUnit: String,
		finalUnit: String,
		conversionFormula: (Double) ->Double
)	{
	val finalMeasurement=String.format("%.2f", conversionFormula(initialMeasurement))
	println("$initialMeasurement degrees $initialUnit is $finalMeasurement degrees $finalUnit.")
}
	
//4
class Song(
	val title: String,
	val artist: String,
	val published: Int,
	val count: Int
) {
	val isFamous:Boolean
		get()=count>=1000
	fun printInfo() {
		println("[$title], artist [$artist] published [$published]")
	}
}
	
	
//5
class Person(val name: String, val age: Int, val hobby: String?, val referrer: Person?){
	fun showProfile(){
		println("Name: $name")
		println("Age: $age")
		if(hobby!=null){
			print("Likes to $hobby.")
		}
		if(referrer!=null){
			print(" Has a referrer named ${referrer.name}")
			if(referrer.hobby!=null){
				print(", who likes to ${referrer.hobby}.")
			}else{
				print(".")
			}
		}else{
			println(" Doesnt' have a referrer.")
		}
		println()
	}
}	
	
//6
open class Phone(var isScreenLightOn: Boolean=false){
	
	open fun switchOn(){
		isScreenLightOn=true
	}
	
	fun switchOff(){
		isScreenLightOn=false
	}
	
	fun checkPhoneScreenLight() {
		val phoneScreenLight=if(isScreenLightOn) "on" else "off"
		println("The phone screen's light is $phoneScreenLight")
	}
	
}

class FoldablePhone(var isFolded:Boolean=true): Phone(){
	
	
	override fun switchOn(){
		if(!isFolded){
			isScreenLightOn=true
		}
	}
	
	fun fold(){
		isFolded=true
	}
	
	fun unfold(){
		isFolded=false
	}
	
}
	
//7
class Bid(val amount: Int, val bidder: String)

fun auctionPrice(bid:Bid?, minimumPrice: Int):Int{
	return bid?.amount?:minimumPrice
}
