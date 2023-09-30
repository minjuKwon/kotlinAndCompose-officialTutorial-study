fun main() {
	val greeting=birthdayGreeting("Rover",5)
	println(greeting)
	println(birthdayGreeting(age=2,name="Rex"))
	println(birthdayGreeting(age=5))
}

fun birthdayGreeting(name : String="Rover", age : Int) : String {
	val nameGreeting="Happy Birthday, $name!"
	val ageGreeting="You are now $age years old!"
	return "$nameGreeting\n$ageGreeting"
}