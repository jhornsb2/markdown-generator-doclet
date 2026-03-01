package com.jhornsb2.doclet.generator.markdown.elements.factory;

import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.EntityTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Scope;
import com.sun.source.tree.Tree;
import com.sun.source.util.DocSourcePositions;
import com.sun.source.util.DocTreeFactory;
import com.sun.source.util.DocTreePath;
import com.sun.source.util.DocTrees;
import com.sun.source.util.TreePath;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.BreakIterator;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import jdk.javadoc.doclet.DocletEnvironment;

final class FactoryTestFixtures {

	private FactoryTestFixtures() {}

	static void ensureDocletEnvironment() {
		try {
			DocCommentUtil.setEnvironment(new EmptyDocletEnvironment());
		} catch (IllegalStateException ignored) {
			// Environment may already be initialized in other tests.
		}
	}

	static TypeElement minimalTypeElement(
		String simpleName,
		String qualifiedName,
		ElementKind kind
	) {
		return typeElement(
			simpleName,
			qualifiedName,
			kind,
			noneTypeMirror(),
			Collections.emptyList()
		);
	}

	static TypeElement typeElement(
		String simpleName,
		String qualifiedName,
		ElementKind kind,
		TypeMirror superClass,
		List<? extends TypeMirror> interfaces
	) {
		Map<String, Object> values = new LinkedHashMap<>();
		values.put("getSimpleName", new SimpleName(simpleName));
		values.put("getQualifiedName", new SimpleName(qualifiedName));
		values.put("getKind", kind);
		values.put("getSuperclass", superClass);
		values.put("getInterfaces", interfaces);
		values.put("toString", qualifiedName);
		return proxy(TypeElement.class, values);
	}

	static PackageElement packageElement(
		String simpleName,
		String qualifiedName,
		List<? extends Element> enclosed
	) {
		Map<String, Object> values = new LinkedHashMap<>();
		values.put("getSimpleName", new SimpleName(simpleName));
		values.put("getQualifiedName", new SimpleName(qualifiedName));
		values.put("getKind", ElementKind.PACKAGE);
		values.put("getEnclosedElements", enclosed);
		values.put("toString", qualifiedName);
		return proxy(PackageElement.class, values);
	}

	static Element moduleElement(
		String simpleName,
		String qualifiedName,
		List<? extends Element> enclosed
	) {
		Map<String, Object> values = new LinkedHashMap<>();
		values.put("getSimpleName", new SimpleName(simpleName));
		values.put("getQualifiedName", new SimpleName(qualifiedName));
		values.put("getKind", ElementKind.MODULE);
		values.put("getEnclosedElements", enclosed);
		values.put("toString", qualifiedName);
		return proxy(javax.lang.model.element.ModuleElement.class, values);
	}

	static Element plainElement(ElementKind kind, String displayValue) {
		Map<String, Object> values = new LinkedHashMap<>();
		values.put("getKind", kind);
		values.put("toString", displayValue);
		return proxy(Element.class, values);
	}

	static DeclaredType declaredType(Element element) {
		Map<String, Object> values = new LinkedHashMap<>();
		values.put("getKind", javax.lang.model.type.TypeKind.DECLARED);
		values.put("asElement", element);
		return proxy(DeclaredType.class, values);
	}

	static TypeMirror nonDeclaredTypeMirror() {
		Map<String, Object> values = new LinkedHashMap<>();
		values.put("getKind", javax.lang.model.type.TypeKind.BOOLEAN);
		return proxy(TypeMirror.class, values);
	}

	static TypeMirror noneTypeMirror() {
		Map<String, Object> values = new LinkedHashMap<>();
		values.put("getKind", javax.lang.model.type.TypeKind.NONE);
		return proxy(TypeMirror.class, values);
	}

	@SuppressWarnings("unchecked")
	private static <T> T proxy(Class<T> clazz, Map<String, Object> values) {
		InvocationHandler handler = new ValueInvocationHandler(values);
		return (T) Proxy.newProxyInstance(
			clazz.getClassLoader(),
			new Class<?>[] { clazz },
			handler
		);
	}

	private static final class ValueInvocationHandler
		implements InvocationHandler
	{

		private final Map<String, Object> values;

		private ValueInvocationHandler(Map<String, Object> values) {
			this.values = values;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
			String methodName = method.getName();
			if ("equals".equals(methodName)) {
				return proxy == args[0];
			}
			if ("hashCode".equals(methodName)) {
				return System.identityHashCode(proxy);
			}
			if (
				"toString".equals(methodName) &&
				this.values.containsKey("toString")
			) {
				return this.values.get("toString");
			}
			if (this.values.containsKey(methodName)) {
				return this.values.get(methodName);
			}
			return defaultValue(method.getReturnType());
		}

		private Object defaultValue(Class<?> returnType) {
			if (!returnType.isPrimitive()) {
				return null;
			}
			if (returnType == boolean.class) {
				return false;
			}
			if (returnType == byte.class) {
				return (byte) 0;
			}
			if (returnType == short.class) {
				return (short) 0;
			}
			if (returnType == int.class) {
				return 0;
			}
			if (returnType == long.class) {
				return 0L;
			}
			if (returnType == float.class) {
				return 0f;
			}
			if (returnType == double.class) {
				return 0d;
			}
			if (returnType == char.class) {
				return '\0';
			}
			return null;
		}
	}

	private static final class SimpleName implements Name {

		private final String value;

		private SimpleName(String value) {
			this.value = Objects.requireNonNull(value);
		}

		@Override
		public boolean contentEquals(CharSequence cs) {
			return this.value.contentEquals(cs);
		}

		@Override
		public int length() {
			return this.value.length();
		}

		@Override
		public char charAt(int index) {
			return this.value.charAt(index);
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			return this.value.subSequence(start, end);
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

	private static final class EmptyDocletEnvironment
		implements DocletEnvironment
	{

		private final DocTrees docTrees = new EmptyDocTrees();

		@Override
		public Set<? extends Element> getSpecifiedElements() {
			return Collections.emptySet();
		}

		@Override
		public Set<? extends Element> getIncludedElements() {
			return Collections.emptySet();
		}

		@Override
		public DocTrees getDocTrees() {
			return this.docTrees;
		}

		@Override
		public Elements getElementUtils() {
			return null;
		}

		@Override
		public Types getTypeUtils() {
			return null;
		}

		@Override
		public boolean isIncluded(Element element) {
			return false;
		}

		@Override
		public boolean isSelected(Element element) {
			return false;
		}

		@Override
		public JavaFileManager getJavaFileManager() {
			return null;
		}

		@Override
		public SourceVersion getSourceVersion() {
			return SourceVersion.latestSupported();
		}

		@Override
		public ModuleMode getModuleMode() {
			return ModuleMode.ALL;
		}

		@Override
		public JavaFileObject.Kind getFileKind(TypeElement typeElement) {
			return JavaFileObject.Kind.SOURCE;
		}
	}

	private static final class EmptyDocTrees extends DocTrees {

		@Override
		public BreakIterator getBreakIterator() {
			return BreakIterator.getSentenceInstance();
		}

		@Override
		public DocCommentTree getDocCommentTree(TreePath path) {
			return null;
		}

		@Override
		public DocCommentTree getDocCommentTree(Element element) {
			return null;
		}

		@Override
		public DocCommentTree getDocCommentTree(FileObject fileObject) {
			return null;
		}

		@Override
		public DocCommentTree getDocCommentTree(
			Element element,
			String relativePath
		) throws IOException {
			return null;
		}

		@Override
		public DocTreePath getDocTreePath(
			FileObject fileObject,
			PackageElement packageElement
		) {
			return null;
		}

		@Override
		public Element getElement(DocTreePath path) {
			return null;
		}

		@Override
		public TypeMirror getType(DocTreePath path) {
			return null;
		}

		@Override
		public List<DocTree> getFirstSentence(List<? extends DocTree> list) {
			return Collections.emptyList();
		}

		@Override
		public DocSourcePositions getSourcePositions() {
			return null;
		}

		@Override
		public void printMessage(
			Diagnostic.Kind kind,
			CharSequence msg,
			DocTree tree,
			DocCommentTree commentTree,
			CompilationUnitTree root
		) {}

		@Override
		public void setBreakIterator(BreakIterator breakIterator) {}

		@Override
		public DocTreeFactory getDocTreeFactory() {
			return null;
		}

		@Override
		public String getCharacters(EntityTree tree) {
			return "";
		}

		@Override
		public Tree getTree(Element element) {
			return null;
		}

		@Override
		public ClassTree getTree(TypeElement typeElement) {
			return null;
		}

		@Override
		public MethodTree getTree(ExecutableElement executableElement) {
			return null;
		}

		@Override
		public Tree getTree(
			Element element,
			AnnotationMirror annotationMirror
		) {
			return null;
		}

		@Override
		public Tree getTree(
			Element element,
			AnnotationMirror annotationMirror,
			AnnotationValue annotationValue
		) {
			return null;
		}

		@Override
		public TreePath getPath(CompilationUnitTree unit, Tree tree) {
			return null;
		}

		@Override
		public TreePath getPath(Element element) {
			return null;
		}

		@Override
		public TreePath getPath(
			Element element,
			AnnotationMirror annotationMirror
		) {
			return null;
		}

		@Override
		public TreePath getPath(
			Element element,
			AnnotationMirror annotationMirror,
			AnnotationValue annotationValue
		) {
			return null;
		}

		@Override
		public Element getElement(TreePath path) {
			return null;
		}

		@Override
		public TypeMirror getTypeMirror(TreePath path) {
			return null;
		}

		@Override
		public Scope getScope(TreePath path) {
			return null;
		}

		@Override
		public String getDocComment(TreePath path) {
			return null;
		}

		@Override
		public boolean isAccessible(Scope scope, TypeElement type) {
			return false;
		}

		@Override
		public boolean isAccessible(
			Scope scope,
			Element member,
			DeclaredType type
		) {
			return false;
		}

		@Override
		public TypeMirror getOriginalType(ErrorType errorType) {
			return errorType;
		}

		@Override
		public void printMessage(
			Diagnostic.Kind kind,
			CharSequence msg,
			Tree tree,
			CompilationUnitTree root
		) {}

		@Override
		public TypeMirror getLub(CatchTree tree) {
			return null;
		}
	}
}
