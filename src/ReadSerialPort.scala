import gnu.io.{SerialPortEvent, ParallelPortEvent, SerialPortEventListener, SerialPort}
import java.io.InputStream

/**
 * Created by IntelliJ IDEA.
 * User: stefan
 * Date: 16.12.2009
 * Time: 22:40:51
 * To change this template use File | Settings | File Templates.
 */

class ReadSerialPort(that : SerialPort) extends SerialPortEventListener {
	val serialPort : SerialPort = that
	val fromPort : InputStream = that.getInputStream

	serialPort.addEventListener(this)
	serialPort.notifyOnDataAvailable(true)

	override def serialEvent(event : SerialPortEvent) {
		event.getEventType match {
			case SerialPortEvent.DATA_AVAILABLE => readData
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY => writeData
			case _ => println("?")
		}
	}

	private def readData {
		while (fromPort.available>0){
			val msg=fromPort.read;
			if (msg>0){
				print(msg)
			}
		}

	}

	private def writeData {
		
	}
}