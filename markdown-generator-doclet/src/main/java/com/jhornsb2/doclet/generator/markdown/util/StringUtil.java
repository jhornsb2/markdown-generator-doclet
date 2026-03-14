package com.jhornsb2.doclet.generator.markdown.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

	private static final String INDENT = "  ";

	public static String formatLombokToString(String lombokToString) {
		if (lombokToString == null) {
			return "null";
		}

		StringBuilder formatted = new StringBuilder(lombokToString.length() + 32);
		int depth = 0;
		boolean inQuotedString = false;
		char quoteCharacter = '\0';
		boolean skipSingleSpace = false;

		for (int index = 0; index < lombokToString.length(); index++) {
			char current = lombokToString.charAt(index);

			if (skipSingleSpace && current == ' ') {
				skipSingleSpace = false;
				continue;
			}
			skipSingleSpace = false;

			if (isQuoteBoundary(lombokToString, index, current, inQuotedString, quoteCharacter)) {
				if (inQuotedString) {
					inQuotedString = false;
					quoteCharacter = '\0';
				} else {
					inQuotedString = true;
					quoteCharacter = current;
				}
				formatted.append(current);
				continue;
			}

			if (inQuotedString) {
				formatted.append(current);
				continue;
			}

			if (isOpeningDelimiter(current)) {
				formatted.append(current);

				if (hasContentInsideDelimiters(lombokToString, index, current)) {
					depth++;
					appendNewLineAndIndent(formatted, depth);
				}
				continue;
			}

			if (isClosingDelimiter(current)) {
				if (depth > 0) {
					depth--;
					appendNewLineAndIndent(formatted, depth);
				}
				formatted.append(current);
				continue;
			}

			if (current == ',') {
				formatted.append(current);
				appendNewLineAndIndent(formatted, depth);
				skipSingleSpace = true;
				continue;
			}

			formatted.append(current);
		}

		return formatted.toString();
	}

	private static boolean isOpeningDelimiter(char character) {
		return character == '(' || character == '[' || character == '{';
	}

	private static boolean isClosingDelimiter(char character) {
		return character == ')' || character == ']' || character == '}';
	}

	private static boolean hasContentInsideDelimiters(
		String text,
		int openingIndex,
		char openingDelimiter
	) {
		char expectedClosingDelimiter = switch (openingDelimiter) {
			case '(' -> ')';
			case '[' -> ']';
			case '{' -> '}';
			default -> '\0';
		};

		for (int index = openingIndex + 1; index < text.length(); index++) {
			char current = text.charAt(index);
			if (Character.isWhitespace(current)) {
				continue;
			}
			return current != expectedClosingDelimiter;
		}

		return false;
	}

	private static boolean isQuoteBoundary(
		String text,
		int index,
		char character,
		boolean inQuotedString,
		char quoteCharacter
	) {
		if (character != '\'' && character != '"') {
			return false;
		}

		if (isEscaped(text, index)) {
			return false;
		}

		if (!inQuotedString) {
			return true;
		}

		return character == quoteCharacter;
	}

	private static boolean isEscaped(String text, int index) {
		int slashCount = 0;
		for (int i = index - 1; i >= 0 && text.charAt(i) == '\\'; i--) {
			slashCount++;
		}
		return (slashCount % 2) == 1;
	}

	private static void appendNewLineAndIndent(StringBuilder output, int depth) {
		output.append('\n');
		for (int i = 0; i < depth; i++) {
			output.append(INDENT);
		}
	}

}
