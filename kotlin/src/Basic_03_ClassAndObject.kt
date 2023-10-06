import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SmartDevice(val name: String, val category: String) {

	open val deviceType="unknown"
	
	var deviceStatus="online"
		protected set
	
//	constructor(name: String, category: String, statusCode: Int) : this(name, category){
//		deviceStatus=when(statusCode){
//			0->"offfline"
//			1->"online"
//			else->"unknown"
//		}		
//	}
	
	open fun turnOn() {
		//println("Smart device is turned on.")
		deviceStatus="on"
	}
	
	open fun turnOff() {
		//println("Smart device is turned off.")
		deviceStatus="off"
	}
	
}

class SmartTvDevice(deviceName: String, deviceCategory: String):
		SmartDevice(name=deviceName, category=deviceCategory){
	
//	private var speakerVolume=2
//			set(value){
//				if(value in 0..100){
//					field=value
//				}
//			}
//	
//	private var channelNumber=1
//			set(value){
//				if(value in 0..200){
//					field=value
//				}
//			}
	
	private var speakerVolume by RangeRegulator(initialValue=0, minValue=0, maxValue=100)
	private var channelNumber by RangeRegulator(initialValue=1, minValue=0, maxValue=200)
	override val deviceType="Smart Tv"
	
	override fun turnOn() {
		//deviceStatus="on"
		super.turnOn()
		println("$name is turned on. Speaker volume is set to $speakerVolume and channel number is"+
					" set to $channelNumber.")
	}
	
	override fun turnOff() {
		//deviceStatus="off"
		super.turnOff()
		println("$name turned off")
	}
	
	fun increaseSpeakerVolume() {
		speakerVolume++
		println("Speaker volume increased to $speakerVolume.")
	}
	
	fun nextChannel() {
		channelNumber++
		println("Channel number increased to $channelNumber.")
	}
	
}

class SmartLightDevice(deviceName: String, deviceCategory: String):
		SmartDevice(name=deviceName, category=deviceCategory){
	
//	private var brightnessLevel=0
//			set(value){
//				if(value in 0..100){
//					field=value
//				}
//			}

	private var brightnessLevel by RangeRegulator(initialValue=2, minValue=0, maxValue=100)
	override val deviceType="Smart Light"
	
	override fun turnOn() {
		//deviceStatus="on"
		super.turnOn()
		brightnessLevel=2
		println("$name turned on. The brightness level is $brightnessLevel.")
	}
	
	override fun turnOff() {
		//deviceStatus="off"
		super.turnOff()
		brightnessLevel=0
		println("Smart Light turned off")
	}
	
	fun increaseBrightness() {
		brightnessLevel++
		println("Brightness increased to $brightnessLevel.")
	}
	
}

class SmartHome(
	val smartTvDevice: SmartTvDevice,
	val smartLightDevice: SmartLightDevice
){
	
	var deviceTurnOnCount=0
			private set
	
	fun turnOnTv() {
		deviceTurnOnCount++
		smartTvDevice.turnOn()
	}
	
	fun turnOffTv() {
		deviceTurnOnCount--
		smartTvDevice.turnOff()
	}
	
	fun increaseTvVolume() {
		smartTvDevice.increaseSpeakerVolume()
	}
	
	fun changeTvChannelToNext() {
		smartTvDevice.nextChannel()
	}
	
	fun turnOnLight() {
		deviceTurnOnCount++
		smartLightDevice.turnOn()
	}
	
	fun turnOffLight() {
		deviceTurnOnCount--
		smartLightDevice.turnOff()
	}
	
	fun increaseLightBrightness() {
		smartLightDevice.increaseBrightness()
	}
	
	fun turnOffAllDevices() {
		turnOffTv()
		turnOffLight()
	}
	
}

class RangeRegulator(
	initialValue: Int,
	private val minValue: Int,
	private val maxValue: Int
) : ReadWriteProperty<Any?, Int>{
	
	var fieldData=initialValue
	
	override fun getValue(thisRef: Any?, property: KProperty<*>): Int{
		return fieldData
	}
	
	override fun setValue(thisRef: Any?, property: KProperty<*>, value : Int){
		if(value in minValue..maxValue){
			fieldData=value
		}
		
	}
	
}

fun main() {
	
//	val smartTvDevice=SmartDevice(name="Android TV",category="Entertainment")
//	
//	println("Device name is : ${smartTvDevice.name}")
//	smartTvDevice.turnOn()
//	smartTvDevice.turnOff()
	
	var smartDevice: SmartDevice=SmartTvDevice("Android Tv", "Entertainment")
	smartDevice.turnOn()
	
	smartDevice=SmartLightDevice("Google Light", "Utility")
	smartDevice.turnOn()
	println()
	
	val smartHome=SmartHome(
		SmartTvDevice(deviceName="Android TV", deviceCategory="Entertainment"),
		SmartLightDevice(deviceName="Google light", deviceCategory="Utility")
	)
	
	smartHome.turnOnTv()
	smartHome.turnOnLight()
	println("Total number of devices currently turned on : ${smartHome.deviceTurnOnCount}")
	println()
	
	smartHome.increaseTvVolume()
	smartHome.changeTvChannelToNext()
	smartHome.increaseLightBrightness()
	println()
	
	smartHome.turnOffAllDevices()
	println("Total number of devices currently turned on : ${smartHome.deviceTurnOnCount}")
	
}