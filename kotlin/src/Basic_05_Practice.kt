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
	printFinalTemperature(27.0, "Celsius", "Fahrenheit"){(9*it)/5+32}
	printFinalTemperature(350.0, "Kelvin", "Celsius"){it-273.15}
	printFinalTemperature(10.0, "Fahrenheit", "Kelvin"){(it-32)*5/9+273.15}
	println()
	
	//4
	println("#4")
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
	if(age<=12){
		return 15
	}else if(age>=13&&age<=60){
		if(isMonday){
			return 25
		}
		return 30
	}else if(age>=61&&age<=100){
		return 20
	}else{
		return -1		
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
class Song(val title: String, val artist: String, val published: String, val count: Int) {
	private var isFamous=false
			set(value){
				if(count<1000){
					field=value
				}else{
					field=!value
				}
			}
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
			println(" Has a referrer named ${referrer.name}, who likes to ${referrer.hobby}.")
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
	
	open fun switchOff(){
		isScreenLightOn=false
	}
	
	open fun checkPhoneScreenLight() {
		val phoneScreenLight=if(isScreenLightOn) "on" else "off"
		println("The phone screen's light is $phoneScreenLight")
	}
	
}

class FoldablePhone(var isScreenLightOnFoldable:Boolean=false): Phone(){
	
	var isFoldable=false
	
	override fun switchOn(){
		isScreenLightOn=true
		isFoldable=true
	}
	
	override fun switchOff(){
		isScreenLightOn=false
		isFoldable=false
	}
	
	override fun checkPhoneScreenLight(){
		val phoneScreenLight=if(isScreenLightOn&&isFoldable) "on" else "off"
		println("The phone screen's light is $phoneScreenLight")
	}
}
	
//7
class Bid(val amount: Int, val bidder: String)

fun auctionPrice(bid:Bid?, minimumPrice: Int):Int{
	return bid?.amount?:minimumPrice
}
