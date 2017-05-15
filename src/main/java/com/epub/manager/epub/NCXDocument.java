package com.epub.manager.epub;

import com.epub.manager.Constants;
import com.epub.manager.domain.Book;
import com.epub.manager.domain.Resource;
import com.epub.manager.domain.TOCReference;
import com.epub.manager.domain.TableOfContents;
import com.epub.manager.util.ResourceUtil;
import com.epub.manager.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Writes the ncx document as defined by namespace http://www.daisy.org/z3986/2005/ncx/
 * 
 * @author paul
 *
 */
public class NCXDocument {
	
	public static final String NAMESPACE_NCX = "http://www.daisy.org/z3986/2005/ncx/";
	public static final String PREFIX_NCX = "ncx";
	public static final String NCX_ITEM_ID = "ncx";
	public static final String DEFAULT_NCX_HREF = "toc.ncx";
	public static final String PREFIX_DTB = "dtb";
	
	private static final Logger log = LoggerFactory.getLogger(NCXDocument.class);

	private interface NCXTags {
		String ncx = "ncx";
		String meta = "meta";
		String navPoint = "navPoint";
		String navMap = "navMap";
		String navLabel = "navLabel";
		String content = "content";
		String text = "text";
		String docTitle = "docTitle";
		String docAuthor = "docAuthor";
		String head = "head";
	}
	
	private interface NCXAttributes {
		String src = "src";
		String name = "name";
		String content = "content";
		String id = "id";
		String playOrder = "playOrder";
		String clazz = "class";
		String version = "version";
	}

	private interface NCXAttributeValues {

		String chapter = "chapter";
		String version = "2005-1";
		
	}
	
	public static Resource read(Book book, EpubReader epubReader) {
		Resource ncxResource = null;
		if(book.getSpine().getTocResource() == null) {
			log.error("Book does not contain a table of contents file");
			return ncxResource;
		}
		try {
			ncxResource = book.getSpine().getTocResource();
			if(ncxResource == null) {
				return ncxResource;
			}
			Document ncxDocument = ResourceUtil.getAsDocument(ncxResource);
			Element navMapElement = DOMUtil.getFirstElementByTagNameNS(ncxDocument.getDocumentElement(), NAMESPACE_NCX, NCXTags.navMap);
			TableOfContents tableOfContents = new TableOfContents(readTOCReferences(navMapElement.getChildNodes(), book));
			book.setTableOfContents(tableOfContents);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ncxResource;
	}
	
	private static List<TOCReference> readTOCReferences(NodeList navpoints, Book book) {
		if(navpoints == null) {
			return new ArrayList<TOCReference>();
		}
		List<TOCReference> result = new ArrayList<TOCReference>(navpoints.getLength());
		for(int i = 0; i < navpoints.getLength(); i++) {
			Node node = navpoints.item(i);
			if (node.getNodeType() != Document.ELEMENT_NODE) {
				continue;
			}
			if (! (node.getLocalName().equals(NCXTags.navPoint))) {
				continue;
			}
			TOCReference tocReference = readTOCReference((Element) node, book);
			result.add(tocReference);
		}
		return result;
	}

	static TOCReference readTOCReference(Element navpointElement, Book book) {
		String label = readNavLabel(navpointElement);
		String tocResourceRoot = StringUtil.substringBeforeLast(book.getSpine().getTocResource().getHref(), '/');
		if (tocResourceRoot.length() == book.getSpine().getTocResource().getHref().length()) {
			tocResourceRoot = "";
		} else {
			tocResourceRoot = tocResourceRoot + "/";
		}
		String reference = StringUtil.collapsePathDots(tocResourceRoot + readNavReference(navpointElement));
		String href = StringUtil.substringBefore(reference, Constants.FRAGMENT_SEPARATOR_CHAR);
		String fragmentId = StringUtil.substringAfter(reference, Constants.FRAGMENT_SEPARATOR_CHAR);
		Resource resource = book.getResources().getByHref(href);
		if (resource == null) {
			log.error("Resource with href " + href + " in NCX document not found");
		}
		TOCReference result = new TOCReference(label, resource, fragmentId);
		List<TOCReference> childTOCReferences = readTOCReferences(navpointElement.getChildNodes(), book);
		result.setChildren(childTOCReferences);
		return result;
	}
	
	private static String readNavReference(Element navpointElement) {
		Element contentElement = DOMUtil.getFirstElementByTagNameNS(navpointElement, NAMESPACE_NCX, NCXTags.content);
		String result = DOMUtil.getAttribute(contentElement, NAMESPACE_NCX, NCXAttributes.src);
		try {
			result = URLDecoder.decode(result, Constants.CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}
		return result;
	}

	private static String readNavLabel(Element navpointElement) {
		Element navLabel = DOMUtil.getFirstElementByTagNameNS(navpointElement, NAMESPACE_NCX, NCXTags.navLabel);
		return DOMUtil.getTextChildrenContent(DOMUtil.getFirstElementByTagNameNS(navLabel, NAMESPACE_NCX, NCXTags.text));
	}


}
