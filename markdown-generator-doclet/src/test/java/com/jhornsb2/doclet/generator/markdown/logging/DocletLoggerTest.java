package com.jhornsb2.doclet.generator.markdown.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import javax.tools.Diagnostic.Kind;
import jdk.javadoc.doclet.Reporter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocletLoggerTest {

	@BeforeEach
	void resetReporterBeforeEach() throws Exception {
		resetReporter();
	}

	@AfterEach
	void resetReporterAfterEach() throws Exception {
		resetReporter();
	}

	@Test
	void throwsWhenReporterIsNotSet() {
		DocletLogger logger = DocletLogger.forClass(DocletLoggerTest.class);

		assertThrows(IllegalStateException.class, () -> logger.info("message"));
	}

	@Test
	void setReporterFailsWhenCalledTwice() {
		DocletLogger.setReporter(noopReporter());

		assertThrows(IllegalStateException.class, () ->
			DocletLogger.setReporter(noopReporter())
		);
	}

	@Test
	void infoFormatsPlaceholdersAndNullValues() {
		List<String> printed = new ArrayList<>();
		DocletLogger.setReporter(capturingReporter(printed));
		DocletLogger logger = DocletLogger.forClass(DocletLoggerTest.class);

		logger.info("Hello {} {}", "world", null);

		assertEquals(1, printed.size());
		String output = printed.get(0);
		assertTrue(output.startsWith(Kind.NOTE + ":"));
		assertTrue(output.contains("[INFO]"));
		assertTrue(output.contains("Hello world null"));
	}

	private static Reporter noopReporter() {
		return capturingReporter(new ArrayList<>());
	}

	private static Reporter capturingReporter(final List<String> sink) {
		return (Reporter) Proxy.newProxyInstance(
			Reporter.class.getClassLoader(),
			new Class<?>[] { Reporter.class },
			(proxy, method, args) -> {
				if ("print".equals(method.getName()) && args != null && args.length >= 2) {
					sink.add(args[0] + ":" + args[1]);
				}
				return null;
			}
		);
	}

	private static void resetReporter() throws Exception {
		Field reporterField = DocletLogger.class.getDeclaredField("reporter");
		reporterField.setAccessible(true);
		reporterField.set(null, null);
	}
}
