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

					val toPort: OutputStream = serialPort.getOutputStream()
					val read = new ReadSerialPort(serialPort)

					//serialPort.addEventListener(new SerialReader(in));
					serialPort.notifyOnDataAvailable(true)
					println("Serial port opened")

					for(i <- 0 to 10) toPort.write(0x10)
					for(i <- 0 to 20) toPort.write(0x00)

					Thread.sleep(10000)

					serialPort.close()
					System.exit(0)
				case _ => println("Error: Only serial ports are handled by this example.")
			}
		}
	}
}
