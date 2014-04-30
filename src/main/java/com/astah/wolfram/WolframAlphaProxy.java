package com.astah.wolfram;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;

public class WolframAlphaProxy {
    private static String appid = "93GPTH-GTLAAXJVK9";
    private WAEngine engine;
    
    public WolframAlphaProxy() {
    	System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    	
    	engine = new WAEngine();
    	
    	engine.setAppID(appid);
    	engine.addFormat("plaintext");
    }
    
    public String query(String input) {
    	WAQuery query = engine.createQuery(input);
    	
    	try {
    		WAQueryResult queryResult = engine.performQuery(query);
    		
    		if(queryResult.isError() || !queryResult.isSuccess()) {
    			return "ERROR: WolframAlpha";
    		}
    		else {
    			for (WAPod pod : queryResult.getPods()) {
                    if (!pod.isError() && "Result".equals(pod.getTitle())) {
                        for (WASubpod subpod : pod.getSubpods()) {
                            for (Object element : subpod.getContents()) {
                                if (element instanceof WAPlainText) {
                                	return ((WAPlainText) element).getText();
                                }
                            }
                        }
                    }
                }
    		}
    	} catch (WAException e) {
            e.printStackTrace();
        }
    	
    	return "ERROR: WolframAlpha";
    }
}
