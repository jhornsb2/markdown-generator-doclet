package com.documentation.generator.java.sub.sub;

import lombok.Data;

@Data
public class ClassExample {

	private String field1;

	@Data
	public static class InnerClass {
		private String innerField;

		public InnerClass(String innerField) {
			this.innerField = innerField;
		}

		public String getInnerField() {
			return innerField;
		}

		public void setInnerField(String innerField) {
			this.innerField = innerField;
		}
	}

}
