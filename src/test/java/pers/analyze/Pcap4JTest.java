package pers.analyze;

import org.junit.Test;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.core.PcapHandle.TimestampPrecision;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

public class Pcap4JTest {
	@Test
	public void testPcap() throws PcapNativeException{
		PcapHandle handle = null;
		handle = Pcaps.openOffline("src/main/resources/search.pcap");
		int count = 0;
		int i=0;
		while (i<10) {
			try {
				count++;
				Packet packet = handle.getNextPacketEx();
				TcpPacket tcpPacket = packet.get(TcpPacket.class);
				if(tcpPacket!=null){
					i++;
					System.out.println("=============="+count+"====================");
				System.out.println(packet);
				}
			} catch (Exception e) {
//				e.printStackTrace();
				System.out.println("已处理" + count + "条数据包");
				break;
			}
		}
		}
	}
