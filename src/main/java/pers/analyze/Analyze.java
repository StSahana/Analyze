package pers.analyze;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapHandle.TimestampPrecision;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import com.alibaba.fastjson.JSON;

import pers.analyze.util.EncodeUtil;

/**
 * Created by StSahana on 2017/8/8.
 */
public class Analyze {
	private static final String FILE_NAME = "search";
	private static final String PCAP_FILE = "src/main/resources/" + FILE_NAME + ".pcap";
	String rawData = null;// 截获的数据
	boolean flag = true;

	public Map<Integer, Set<String>> getSearchRecord() throws PcapNativeException {
		Map<Integer, Set<String>> record = new HashMap<Integer, Set<String>>();// 匹配结果
		Infomation info = new Infomation();
		PcapHandle handle = null;
		try {
			handle = Pcaps.openOffline(PCAP_FILE, TimestampPrecision.NANO);
		} catch (PcapNativeException e) {
			handle = Pcaps.openOffline(PCAP_FILE);
		}
		int count = 0;
		while (true) {
			try {
				count++;
				Packet packet = handle.getNextPacketEx();
				TcpPacket tcpPacket = packet.get(TcpPacket.class);
				if (tcpPacket != null) {
					if (tcpPacket.getPayload() != null) {
						rawData = EncodeUtil.byteToString(tcpPacket.getPayload().getRawData()) + packet.toString();
						// 分析HTTP数据
						Set<String> tempList = new HashSet<String>();
						// 检测中文
						info.getChineseInfo().forEach((k, v) -> {
							for (String s : v) {
								if (!rawData.contains(s)) {
									flag = false;
								}
							}
							if (flag) {
								tempList.add(k.split("_")[0]);
								if (k.split("_").length > 1) {
									tempList.add(k.split("_")[1]);
								}
							}
							flag = true;
						});
						// 检测英文
						info.getEnglishInfo().forEach((k, v) -> {
							for (String s : v) {
								if (!rawData.contains(s)) {
									flag = false;
									break;
								}
							}
							if (flag) {
								tempList.add(k.split("_")[0]);
								if (k.split("_").length > 1) {
									tempList.add(k.split("_")[1]);
								}
							}
							flag = true;
						});
						record.put(count, tempList);

					}
				}
			} catch (Exception e) {
				System.out.println("共处理" + count + "条数据包");
				// System.out.println("已全部处理完毕");
				break;
			}
		}
		handle.close();
		return DleteRepeat(record);
		// return record;
	}

	// 如果匹配到的元素相同，去掉后面匹配到的
	public Map<Integer, Set<String>> DleteRepeat(Map<Integer, Set<String>> map) {
		Map<Integer, Set<String>> tempRecord = new HashMap<Integer, Set<String>>();
		Set<Set<String>> set = new HashSet<Set<String>>();// 存储重复数据
		map.forEach((k, v) -> {
			if (!set.contains(v)) {
				set.add(v);
				tempRecord.put(k, v);
			}
		});
		return tempRecord;
	}
}
