/*
 * (c) Copyright QNX Software Systems Ltd. 2002.
 * All Rights Reserved.
 */

package org.eclipse.cdt.debug.mi.core.output;

import java.util.ArrayList;
import java.util.List;


/**
 * -break-insert main
 * ^done,bkpt={number="1",type="breakpoint",disp="keep",enabled="y",addr="0x08048468",func="main",file="hello.c",line="4",times="0"}
 * -break-insert -a p
 * ^done,hw-awpt={number="2",exp="p"}
 * -break-watch -r p
 * ^done,hw-rwpt={number="4",exp="p"}
 * -break-watch p
 * ^done,wpt={number="6",exp="p"}
 */
public class MIBreakInsertInfo extends MIInfo {

	MIBreakPoint[] breakpoints;

	void parse() {
		List aList = new ArrayList(1);
		if (isDone()) {
			MIOutput out = getMIOutput();
			MIResultRecord rr = out.getMIResultRecord();
			if (rr != null) {
				MIResult[] results =  rr.getMIResults();
				for (int i = 0; i < results.length; i++) {
					String var = results[i].getVariable();
					MIValue val = results[i].getMIValue();
					MIBreakPoint bpt = null;
					if (var.equals("bkpt")) {
						if (val instanceof MITuple) {
							bpt = new MIBreakPoint((MITuple)val);
							bpt.setEnabled(true);
						}
					} else if (var.equals("hw-awpt")) {
						if (val instanceof MITuple) {
							bpt = new MIBreakPoint((MITuple)val);
							bpt.setAccessWatchpoint(true);
							bpt.setEnabled(true);
						}
					} else if (var.equals("hw-rwpt")) {
						if (val instanceof MITuple) {
							bpt = new MIBreakPoint((MITuple)val);
							bpt.setReadWatchpoint(true);
							bpt.setEnabled(true);
						}
					}
					if (bpt != null) {
						aList.add(bpt);
					}
				}
			}
		}
		breakpoints = (MIBreakPoint[])aList.toArray(new MIBreakPoint[aList.size()]);
	}

	public MIBreakInsertInfo(MIOutput record) {
		super(record);
	}

	public MIBreakPoint[] getBreakPoints() {
		if (breakpoints == null) {
			parse();
		}
		return breakpoints;
	}
}
