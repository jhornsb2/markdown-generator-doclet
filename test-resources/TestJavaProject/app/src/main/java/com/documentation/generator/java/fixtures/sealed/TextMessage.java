package com.documentation.generator.java.fixtures.sealed;

/**
 * Final sealed hierarchy member.
 */
public final class TextMessage extends Message {

	/**
	 * Creates a plain text message.
	 *
	 * @param content message content
	 */
	public TextMessage(String content) {
		super(content);
	}

	@Override
	public String render() {
		return content();
	}
}
