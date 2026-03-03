package com.documentation.generator.java.fixtures.sealed;

/**
 * Non-sealed sealed hierarchy member.
 */
public non-sealed class RichMessage extends Message {

	private final String format;

	/**
	 * Creates a rich message.
	 *
	 * @param content message content
	 * @param format message format
	 */
	public RichMessage(String content, String format) {
		super(content);
		this.format = format;
	}

	/**
	 * Returns message format.
	 *
	 * @return format
	 */
	public String format() {
		return format;
	}

	@Override
	public String render() {
		return "[" + format + "] " + content();
	}
}
