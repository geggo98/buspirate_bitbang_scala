import gnu.io.SerialPort
import java.io.{OutputStream, InputStream}
import scala.Array

/**
 * Created by IntelliJ IDEA.
 * User: stefan
 * Date: 16.12.2009
 * Time: 20:55:57
 * To change this template use File | Settings | File Templates.
 */

class BusPirate(that: SerialPort) {
	serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE)
	serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN | SerialPort.FLOWCONTROL_XONXOFF_OUT)

	val serialPort : SerialPort = that
	val fromPort : InputStream = that.getInputStream
	val toPort : OutputStream = that.getOutputStream

	require(fromPort!=null)
	require(toPort!=null)

}