package pers.analyze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapHandle.TimestampPrecision;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;

import pers.analyze.util.EncodeUtil;

/**
 * Created by StSahana on 2017/8/8.
 */
public class Analyze {
	private static final String FILE_NAME = "search";
	private static final String PCAP_FILE = "src/main/resources/" + FILE_NAME + ".pcap";
    Map<Integer,Map<String,String>> contentRecord=new HashMap<Integer,Map<String,String>>();//需要记录内容的部分
	boolean flag = true;

	public Map<Integer, Set<String>> getSearchRecord() throws PcapNativeException {
		Map<Integer, Set<String>> record = new HashMap<Integer, Set<String>>();// 鍖归厤缁撴灉
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
						Map<String,String> tempMap=new HashMap<String,String>();
 						System.out.println(new String(tcpPacket.getPayload().getRawData(),"gbk"));
						String rawData = EncodeUtil.byteToString(tcpPacket.getPayload().getRawData()) + packet.toString();
						String gbkData=new String(tcpPacket.getPayload().getRawData(),"gbk");
						tempMap.put("body", gbkData);//内容
						if(Pattern.matches("HTTP)", packet.toString())){//http头
							tempMap.put("httpHeader",gbkData);
						}else{
							tempMap.put("httpHeader","");
						}
						tempMap.put("export", FILE_NAME+"_"+count+".pcap");
//						Set<String> tempSet = findWords(rawData,gbkData);
						Infomation info = new Infomation();
						Set<String> tempSet = new HashSet<String>();
						// 检测中文
						info.getChineseInfo().forEach((k, v) -> {
							for (String s : v) {
								if (!rawData.contains(s)) {
									flag = false;
								}
							}
							if (flag) {
								tempSet.add(k.split("_")[0]);
								if (k.split("_").length > 1) {
//									tempSet.add(k.split("_")[1]);
									if(tempMap.containsKey("secret")){
										tempMap.put("secret",tempMap.get("secret")+"、"+k.split("_")[1]);
									}else{
										tempMap.put("secret",k.split("_")[1]);
									}
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
								tempSet.add(k.split("_")[0]);
								if (k.split("_").length > 1) {
//									tempSet.add(k.split("_")[1]);
									if(tempMap.containsKey("secret")){
										tempMap.put("secret",tempMap.get("secret")+"、"+k.split("_")[1]);
									}else{
										tempMap.put("secret",k.split("_")[1]);
									}
								}
							}
							flag = true;
						});
						
						info.getRegex().forEach((k,v)->{
							if(Pattern.matches(v, gbkData)){
								tempSet.add(k);
							}
						});
						record.put(count, tempSet);
						contentRecord.put(count, tempMap);
					}
				}
			} catch (Exception e) {
				System.out.println("已处理" + count + "条数据包");
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
	
	//匹配数据
	public Set<String> findWords(String rawData,String gbkData){
		Infomation info = new Infomation();
		Set<String> tempSet = new HashSet<String>();
		// 检测中文
		info.getChineseInfo().forEach((k, v) -> {
			for (String s : v) {
				if (!rawData.contains(s)) {
					flag = false;
				}
			}
			if (flag) {
				tempSet.add(k.split("_")[0]);
				if (k.split("_").length > 1) {
					tempSet.add(k.split("_")[1]);
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
				tempSet.add(k.split("_")[0]);
				if (k.split("_").length > 1) {
					tempSet.add(k.split("_")[1]);
				}
			}
			flag = true;
		});
		
		info.getRegex().forEach((k,v)->{
			if(Pattern.matches(v, gbkData)){
				tempSet.add(k);
			}
		});
		
		return tempSet;
	}

	public Map<Integer, Map<String, String>> getContentRecord() {
		return contentRecord;
	}
	
	
}
