fun main() {
	
	val count: Int =10;
	println("You have $count unread messages.")
	
	val unreadCount=5
	val readCount=100
	println("You have ${unreadCount+readCount} total messages in you inbox.")
	
	val numberOfPhotos=100
	val photosDeleted=10
	println("$numberOfPhotos photos")
	println("$photosDeleted photos deleted")
	println("${numberOfPhotos-photosDeleted} photos left")
	
	var cartTotal=0
	cartTotal=20
	println("Total : $cartTotal")
	
	var cnt=10
	println("You have $cnt unread messages.")
	cnt++
	println("You have $cnt unread messages.")
	cnt=10
	println("You have $cnt unread messages.")
	cnt--
	println("You have $cnt unread messages.")
	
	val trip1=3.20
	val trip2=4.10
	val trip3=1.72
	val totalTripLength=trip1+trip2+trip3
	println("$totalTripLength miles left to destination")
	
	val nextMeeting="Next metting is : "
	val date="January 1"
	val reminder=nextMeeting+date+" at work"
	println(reminder)
	
	println("Say \"hello\"")
	
	val notificationsEnabled: Boolean = false
	println("Are notifications enabled ? "+notificationsEnabled)
	
}