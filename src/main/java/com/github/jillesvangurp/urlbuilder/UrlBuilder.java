/**
 * Copyright (c) 2012, Jilles van Gurp
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.jillesvangurp.urlbuilder;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

/**
 * Simple builder class to construct urls out of a base url, one or more post fixes, and query parameters.
 *
 * The builder class enables you to not worry about things like trailing slashes, url encoding, etc.
 */
public class UrlBuilder {

    private final StringBuilder url;
    // preserve order in which parameters are added
    private final LinkedList<Entry<String, String>> params = new LinkedList<>();

    private UrlBuilder(String baseUrl) {
        url = new StringBuilder(baseUrl);
    }

    /**
     * @param baseUrl
     * @return builder for the baseUrl
     */
    public static UrlBuilder url(String baseUrl) {
        return new UrlBuilder(baseUrl);
    }

    /**
     * @param host
     * @param port
     * @return builder for http://[host]:[port]
     */
    public static UrlBuilder url(String host, int port) {
        return new UrlBuilder("http://" + host + ":" + port);
    }

    /**
     * @return URL as a String. Note, you can also use toString.
     */
    public String build() {
    	StringBuilder result=new StringBuilder(url);
        if(params.size() > 0) {
        	result.append('?');

            for (Entry<String, String> entry: params) {
                String value = entry.getValue();
                result.append(entry.getKey() + '=' + value);
                result.append('&');
            }
            result.deleteCharAt(result.length()-1); // remove trailing &
        }
        return result.toString();
    }

    /**
     * @return URL instance
     * @throws IllegalArgumentException in case the URL is not valid (wraps checked MalformedURLException).
     */
    public URL buildUrl() {
        try {
            return new URL(build());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Appends one or more postfixes and separates them by slashes. Note, prefixes are url encoded, so don't include the actual slashes.
     * @param postFix
     * @return the builder.
     */
    public UrlBuilder append(String... postFix) {
        return append(true,postFix);
    }

    /**
     * Appends one or more postfixes and separates them by slashes.
     * @param encode if true, the postFixes are url encoded.
     * @param postFix one or more String values
     * @return the builder
     */
    public UrlBuilder append(boolean encode,String... postFix) {
        for (String part : postFix) {
            if(StringUtils.isNotBlank(part)) {
                if (url.charAt(url.length() - 1) != '/' && !part.startsWith("/")) {
                    url.append('/');
                }
                if(encode) {
                    try {
                        url.append(URLEncoder.encode(part, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        throw new IllegalStateException(e);
                    }
                } else {
                    url.append(part);
                }
            }
        }
        return this;
    }

    /**
     * Create a query parameter with a boolean value.
     * @param name parameter name
     * @param value
     * @return the builder
     */
    public UrlBuilder queryParam(String name, Boolean value) {
        if(value != null) {
            return queryParam(name, value.toString());
        } else {
            return null;
        }
    }

    /**
     * Create a query parameter with a number value.
     * @param name parameter name
     * @param value
     * @return the builder
     */
    public UrlBuilder queryParam(String name, Number value) {
        if(value != null) {
            return queryParam(name, value.toString());
        } else {
            return null;
        }
    }

    /**
     * Create a query parameter with a String value. The value will be urlencoded.
     * @param name
     * @param value
     * @return the builder
     */
    public UrlBuilder queryParam(String name, String value) {
        return queryParam(name, value, true);
    }

    /**
     * Create a query parameter with a String value.
     * @param name
     * @param value
     * @param encode if true, the value is urlencoded.
     * @return the builder
     */
    public UrlBuilder queryParam(String name, String value, boolean encode) {
        if (StringUtils.isNotEmpty(value)) {
            if (encode) {
                try {
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalStateException(e);
                }
            }
            params.add(new EntryImpl(name, value));
        }
        return this;
    }

    @Override
    public String toString() {
        return build();
    }

    private static class EntryImpl implements Entry<String,String> {
        private final String name;
        private final String value;

        public EntryImpl(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String getKey() {
            return name;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String setValue(String value) {
            throw new UnsupportedOperationException("entry is immutable");
        }
    }
}
