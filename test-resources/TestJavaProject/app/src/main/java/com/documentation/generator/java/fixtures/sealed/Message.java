package com.documentation.generator.java.fixtures.sealed;

/**
 * Sealed class root with final and non-sealed subclasses.
 */
public abstract sealed class Message permits TextMessage, RichMessage {

	private final String content;

	/**
	 * Creates a message.
	 *
	 * @param content message content
	 */
	protected Message(String content) {
		this.content = content;
	}

	/**
	 * Returns message content.
	 *
	 * @return content
	 */
	public String content() {
		return content;
	}

	/**
	 * Returns a renderable representation.
	 *
	 * @return rendered text
	 */
	public abstract String render();
}
