package com.github.epubreader.manager.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Stack;
import java.util.StringTokenizer;

public class PathUtil {
    static final String workingDirectory = System.getProperty("user.dir");

    public static String resolveRelativeReference(String base, String ref,
                                                  String baseRewrite) throws IllegalArgumentException {

        //baseRewrite is null unless head/base or xml:base is set in the instance
        String actualBase = base;
        if (baseRewrite != null && baseRewrite.length() > 0 && !baseRewrite.equals(".")) {

            actualBase = baseRewrite;
        }

        if (ref.startsWith("data:") || ref.startsWith("http:")) {
            return ref;
        }
        try {
            ref = URLDecoder.decode(ref.replace("+", "%2B"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // UTF-8 is guaranteed to be supported
            throw new InternalError(e.toString());
        }

        if (ref.startsWith("#")) {
            int index = actualBase.indexOf("#");
            if (index < 0) {
                ref = actualBase + ref;
            } else {
                ref = actualBase.substring(0, index) + ref;
            }
        } else {
            int index = actualBase.lastIndexOf("/");
            ref = actualBase.substring(0, index + 1) + ref;
        }
        return normalizePath(ref);
    }

    public static String normalizePath(String path) throws IllegalArgumentException {

        // Test for any ../ or ./
        if (!path.contains("./")) {
            return path;
        }

        Stack<String> pathSegments = new Stack<String>();
        StringTokenizer tokenizer = new StringTokenizer(path, "/");
        while (tokenizer.hasMoreTokens()) {
            String pathSegment = tokenizer.nextToken();
            if (".".equals(pathSegment)) {
                continue;
            }
            if ("..".equals(pathSegment)) {
                if (pathSegments.size() == 0) {
                    throw new IllegalArgumentException("Invalid path: " + path);
                }
                pathSegments.pop();
            } else {
                pathSegments.push(pathSegment);
            }
        }
        StringBuilder sb = new StringBuilder(path.length());
        int len = pathSegments.size();
        for (int i = 0; i < len; i++) {
            if (i != 0) {
                sb.append('/');
            }
            sb.append(pathSegments.elementAt(i));
        }
        return sb.toString();
    }

    public static String removeWorkingDirectory(String path) {
        if (path == null || path.length() == 0) {
            return path;
        }
        return path.replace(workingDirectory, ".");
    }


    public static String removeAnchor(String href) {
        int index = href.indexOf("#");
        if (index == -1) {
            return href;
        }
        return (href.substring(0, index));
    }
}