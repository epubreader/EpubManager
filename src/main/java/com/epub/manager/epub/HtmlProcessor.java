package com.epub.manager.epub;

import java.io.OutputStream;

import com.epub.manager.domain.Resource;

public interface HtmlProcessor {
	
	void processHtmlResource(Resource resource, OutputStream out);
}
