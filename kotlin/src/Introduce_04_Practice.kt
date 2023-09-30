fun main() {
	
	//1
	println("#1")
	println("Use the val keyword when the value doesn't change.")
	println("Use the var keyword when the value can change.")
	println("When you define a function, you define the parameters that can be passed to it.")
	println("When you call a function, you pass arguments for the parameters.")
	println()
	
	//2
	println("#2")
	println("New chat message from a friend")
	println()
	
	//3
	println("#3")
	var discountPercentage: Int =0
	var offer: String=""
	val item="Google Chromecast"
	discountPercentage=20
	offer="Sale - Up to $discountPercentage discount on $item! Hurry up!"
	println(offer)
	println()
	
	//4
	println("#4")
	val numberOfAdults=20
	val numberOfKids=30
	val total=numberOfAdults+numberOfKids
	println("The total party size is : $total")
	println()
	
	//5
	println("#5")
	val baseSalary=5000
	val bonusAmount=1000
	val totalSalary="$baseSalary+$bonusAmount"
	println("Congratulations for you bonus! You will receive a total of $totalSalary (additional bonus).")
	println()
	
	//6
	println("#6")
	/*val firstNumber=10
	val secondNumber=5
	val result=firstNumber+secondNumber
	println("$firstNumber + $secondNumber = $result")*/
	
	val firstNumber=10
	val secondNumber=5
	val thirdNumber=8
	val result=add(firstNumber,secondNumber)
	val anotherResult=add(firstNumber, thirdNumber)
	println("$firstNumber+$secondNumber=$result")
	println("$firstNumber+$thirdNumber=$anotherResult")
	
	val sub1=subtract(firstNumber,secondNumber)
	val sub2=subtract(firstNumber,thirdNumber)
	println("$firstNumber-$secondNumber=$sub1")
	println("$firstNumber-$thirdNumber=$sub2")
	println()
	
	//7
	println("#7")
	val operatingSystem="Chrome OS"
	val emailId="sample@gamil.com"
	println(displayAlertMessage(operatingSystem,emailId))
	
	val firstUserEmailId="user_one@gmail.com"
	println(displayAlertMessage(emailId=firstUserEmailId))
	println()
	val secondUserOperatingSystem="Windows"
	val secondUserEmailId="user_two@gmail.com"
	println(displayAlertMessage(secondUserOperatingSystem, secondUserEmailId))
	println()
	val thirdUserOperatingSystem="Mac OS"
	val thirdUserEmailId="user_three@gmail.com"
	println(displayAlertMessage(thirdUserOperatingSystem, thirdUserEmailId))
	println()
	
	//8
	println("#8")
	val steps=4000
	val caloriesBurned=pedometerStepToCalories(steps)
	println("Walking $steps steps burn $caloriesBurned calories")
	println()
	
	//9
	println("#9")
	println(time(300,250))
	println(time(300,300))
	println(time(200,220))
	println()
	
	//10
	println("#10")
	println(weather("Ankara", 27, 31, 82))
	println(weather("Tokyo", 32, 26, 10))
	println(weather("Cape Town", 59, 64, 2))
	println(weather("Guatemala City", 50, 55, 7))
	
}

//6
fun add(num1 : Int, num2 : Int):Int {
	return num1+num2
}
fun subtract(num1: Int, num2:Int):Int {
	return num1-num2
}

//7
fun displayAlertMessage(system: String="Unknown OS", emailId:String): String {
	return "There's a new sign-in request on $system for you Google Account $emailId"
}

//8
fun pedometerStepToCalories(numberOfSteps: Int): Double {
	val caloriesBurnedForEachStep=0.04
	val totalCaloriesBurned=numberOfSteps*caloriesBurnedForEachStep
	return totalCaloriesBurned
}

//9
fun time(timeSpentToday: Int, timeSpentYesterday: Int): Boolean {
	return timeSpentToday>timeSpentYesterday
}

//10
fun weather(city: String, lowT: Int, highT: Int, rain: Int ): String {
	return "City: $city\nLow temperature: $lowT, High temperature: $highT\nChance of rain: $rain%\n"
}