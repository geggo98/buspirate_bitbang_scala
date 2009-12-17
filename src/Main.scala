import gnu.io.{SerialPort, CommPort, CommPortIdentifier}
import java.io.{OutputStream, InputStream}

/**
 * Created by IntelliJ IDEA.
 * User: stefan
 * Date: 14.12.2009
 * Time: 21:10:27
 * To change this template use File | Settings | File Templates.
 */

object Main {
	def main(arguments: Array[String]) {

		println("Hallo Welt")
		val portName: String = "/dev/tty.usbserial-A600aSsZ"
		val portIdentifier: CommPortIdentifier = CommPortIdentifier.getPortIdentifier(portName)
		if (portIdentifier.isCurrentlyOwned()) {
			println("Error: Port is currently in use")
		} else {
			val timeout: Int = 2000
			// Make sure that the directory "/var/lock" exists and is writable for the current user.
			val commPort: CommPort = portIdentifier.open("buspirate_bitbang", timeout)

			commPort match {
				case serialPort: SerialPort =>
					serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE)
					serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN | SerialPort.FLOWCONTROL_XONXOFF_OUT)
					
					val toPort: OutputStream = serialPort.getOutputStream
					//val fromPort : InputStream = serialPort.getInputStream
					val read = new ReadSerialPort(serialPort)

					println("Serial port opened")

					println("Write a single zero")
					toPort.write(0x00); //readAvailable(fromPort)

					println
					println("Write Returns")
					for(i <- 0 to 10) { toPort.write(0x0a); /*readAvailable(fromPort)*/ }
					toPort.write(0x23); toPort.write(0x0a) // "#\n"
					Thread.sleep(20)

					println
					println("Write Zeros")
					for(i <- 0 to 20) { toPort.write(0x00); /*readAvailable(fromPort)*/ }

					Thread.sleep(1000)

					println
					println("Read returns")
					//readAvailable(fromPort)

					println

					serialPort.close()
					System.exit(0)
				case _ => println("Error: Only serial ports are handled by this example.")
			}
		}
	}
	def readAvailable(fromPort : InputStream) {
		while(fromPort.available > 0){
			val msg: Int = fromPort.read
			if (msg > -1) { print(" "); print(msg) }
		}
	}
}
