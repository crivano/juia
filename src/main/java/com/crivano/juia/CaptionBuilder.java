package com.crivano.juia;

public interface CaptionBuilder {
	default String buildCaptionFromName(String name) {
		String s = name;
		String result = "";
		for (int i = 0; i < s.length(); i++) {
			String ch = s.substring(i, i + 1);
			if (i == 0)
				ch = ch.toUpperCase();
			else if (ch.toUpperCase().equals(ch))
				ch = " " + ch;
			result += ch;
		}
		s = result;
		return s;
	};
}
