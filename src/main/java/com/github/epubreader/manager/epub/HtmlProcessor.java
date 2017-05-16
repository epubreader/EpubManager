package com.github.epubreader.manager.epub;

import com.github.epubreader.manager.domain.Resource;

import java.io.OutputStream;

public interface HtmlProcessor {
	
	void processHtmlResource(Resource resource, OutputStream out);
}
