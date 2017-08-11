package pers.analyze;

import org.pcap4j.core.PcapNativeException;

import pers.analyze.util.ExcellUtil;

public class Main {
	public static void main(String[] args) throws PcapNativeException {
		ExcellUtil.WriteToExcell(new Analyze().getSearchRecord());
	}
}
