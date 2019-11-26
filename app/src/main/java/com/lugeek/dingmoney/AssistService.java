package com.lugeek.dingmoney;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 钉钉v4.7.16 亲测可用
 */
public class AssistService extends AccessibilityService {

    private static final String TAG = "AssistService";
    Set<String> clickedNodes = Collections.newSetFromMap(new LinkedHashMap<String, Boolean>(){
        protected boolean removeEldestEntry(Map.Entry<String, Boolean> eldest) {
            return size() > 2;
        }
    });


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, event.toString());

        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> nodeInfos = nodeInfo.findAccessibilityNodeInfosByText("拼手气红包");
            if (nodeInfos != null) {
                for (int i = 0; i < nodeInfos.size(); i++) {
                    if (nodeInfos.get(i).getParent() != null && nodeInfos.get(i).getParent().getParent() != null) {
                        String desc = (String) nodeInfos.get(i).getParent().getParent().getContentDescription();
                        if (clickedNodes.contains(desc)) {
                            continue;
                        }
                    }

                    if (nodeInfos.get(i).getParent() != null) {
                        nodeInfos.get(i).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        if (nodeInfos.get(i).getParent().getParent() != null
                                && nodeInfos.get(i).getParent().getParent().getContentDescription() != null) {
                            clickedNodes.add((String) nodeInfos.get(i).getParent().getParent().getContentDescription());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
