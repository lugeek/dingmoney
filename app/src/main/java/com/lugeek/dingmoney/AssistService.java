package com.lugeek.dingmoney;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 钉钉v4.7.16 亲测可用
 */
public class AssistService extends AccessibilityService {

    private static final String TAG = "AssistService";
    private Set<AccessibilityNodeInfo> clickedNodes = new HashSet<>();


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, event.toString());

        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> nodeInfos = nodeInfo.findAccessibilityNodeInfosByText("拼手气红包");
            if (nodeInfos != null) {
                for (int i = 0; i < nodeInfos.size(); i++) {
                    if (clickedNodes.contains(nodeInfos.get(i))) {
                        continue;
                    }
                    nodeInfos.get(i).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    clickedNodes.add(nodeInfos.get(i));
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
