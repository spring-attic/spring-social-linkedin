/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.linkedin.api.impl;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base Template extended by various specific API Templates
 * 
 * @author Robert Drysdale
 */
abstract class AbstractTemplate {

	/** Captures URI template variable names. */
	private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)\\}");
	
	protected URI expand(String url, Object[] variables, boolean encode) {
		Matcher matcher = NAMES_PATTERN.matcher(url);
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		String separator = "";
		while (matcher.find()) {
			Object uriVariable = variables[i++];
			String replacement = Matcher.quoteReplacement(uriVariable != null ? (encode ? encode(uriVariable.toString()): uriVariable.toString()) : "");
			String key = matcher.group();
			if (key.charAt(1) == '&' && replacement != null && replacement.length() > 0) {
				key = key.substring(2, key.length()-1);
				matcher.appendReplacement(buffer, separator + key + '=' + replacement);
				separator = "&";
			}
			else {
				matcher.appendReplacement(buffer, replacement);
			}
		}
		matcher.appendTail(buffer);
		try {
			return new URI(buffer.toString());
		}
		catch (URISyntaxException ex) {
			throw new IllegalArgumentException("Could not create URI from [" + buffer + "]: " + ex, ex);
		}
	}
	
	private String encode(String param) {
		byte[] source = param.getBytes();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(source.length);
        for (int i = 0; i < source.length; i++) {
            int b = source[i];
            if (b < 0) {
                b += 256;
            }
            if (isAllowed(b)) {
                bos.write(b);
            }
            else {
                bos.write('%');

                char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
                char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));

                bos.write(hex1);
                bos.write(hex2);
            }
        }
        return bos.toString();
	}
	
	private boolean isAllowed(int c) {
         if ('=' == c || '+' == c || '&' == c) {
             return false;
         }
         else {
             return isPchar(c) || '/' == c || '?' == c;
         }
     }
	 
	private boolean isPchar(int c) {
         return isUnreserved(c) || isSubDelimiter(c) || ':' == c || '@' == c;
     }
	 
	private boolean isUnreserved(int c) {
         return isAlpha(c) || isDigit(c) || '-' == c || '.' == c || '_' == c || '~' == c;
     }
	 
	private boolean isAlpha(int c) {
         return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
     }

	private boolean isDigit(int c) {
         return c >= '0' && c <= '9';
     }
     
	private boolean isSubDelimiter(int c) {
         return '!' == c || '$' == c || '&' == c || '\'' == c || '(' == c || ')' == c || '*' == c || '+' == c ||
                 ',' == c || ';' == c || '=' == c;
     }

}
