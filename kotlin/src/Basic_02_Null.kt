fun main() {
	
	var favoriteActor: String?="Sandra Oh"
	println(favoriteActor)
	favoriteActor=null
	println(favoriteActor)
	
	var number: Int?=10
	println(number)
	number=null
	println(number)
	
	//==============================================
	
	var actor: String?="san"
	println(actor?.length)
	actor=null
	println(actor?.length)
	
	var person: String?="Sam"
	println(person!!.length)
	
	//==============================================
	
	var name: String?="Kim"
	if(name!=null){
		println("The number of characters in you favorite actor's name is ${name.length}.")
	}else{
		println("You didn't input a name.")
	}
	name=null
	if(name!=null){
		println("The number of characters in you favorite actor's name is ${name.length}.")
	}else{
		println("You didn't input a name.")
	}
	
	val actress: String?="Lee"
	val lengthOfName=if(actress!=null){
		actress.length
	}else{
		0
	}
	println("The number of characters in you favorite actress's name is $lengthOfName")
	
	val celebrity: String?="James"
	val length=celebrity?.length?:0
	println("The number of characters in you favorite celebrity name is $length")
}