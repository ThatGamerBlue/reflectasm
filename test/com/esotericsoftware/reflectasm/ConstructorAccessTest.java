
package com.esotericsoftware.reflectasm;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import junit.framework.TestCase;

public class ConstructorAccessTest extends TestCase {
	public void testNewInstance () {
		ConstructorAccess<SomeClass> access = ConstructorAccess.get(SomeClass.class);
		SomeClass someObject = new SomeClass();
		assertEquals(someObject, access.newInstance());
		assertEquals(someObject, access.newInstance());
		assertEquals(someObject, access.newInstance());
	}

	public void _testPackagePrivateNewInstance () {
		ConstructorAccess<PackagePrivateClass> access = ConstructorAccess.get(PackagePrivateClass.class);
		PackagePrivateClass someObject = new PackagePrivateClass();
		assertEquals(someObject, access.newInstance());
		assertEquals(someObject, access.newInstance());
		assertEquals(someObject, access.newInstance());
	}

	public void testHasArgumentConstructor () {
		try {
			ConstructorAccess.get(HasArgumentConstructor.class);
			assertTrue(false);
		}
		catch (RuntimeException re) {
			System.out.println("Expected exception happened: " + re);
		}
		catch (Throwable t) {
			System.out.println("Unexpected exception happened: " + t);
			assertTrue(false);
		}
	}

	public void testHasPrivateConstructor () {
		try {
			ConstructorAccess.get(HasPrivateConstructor.class);
			assertTrue(false);
		}
		catch (RuntimeException re) {
			System.out.println("Expected exception happened: " + re);
		}
		catch (Throwable t) {
			System.out.println("Unexpected exception happened: " + t);
			assertTrue(false);
		}
	}

	public void testHasProtectedConstructor () {
		try {
			ConstructorAccess<HasProtectedConstructor> access = ConstructorAccess.get(HasProtectedConstructor.class);
			fail("Expected exception");
		}
		catch (Throwable t) {
			assertEquals("Class cannot be created (the no-arg constructor is private): com.esotericsoftware.reflectasm.ConstructorAccessTest$HasProtectedConstructor", t.getMessage());
		}
	}

	public void testHasPackageProtectedConstructor () {
		try {
			ConstructorAccess<HasPackageProtectedConstructor> access = ConstructorAccess.get(HasPackageProtectedConstructor.class);
			fail("Failed exception");
		}
		catch (Throwable t) {
			assertEquals("Class cannot be created (the no-arg constructor is private): com.esotericsoftware.reflectasm.ConstructorAccessTest$HasPackageProtectedConstructor", t.getMessage());
		}
	}

	public void testHasPublicConstructor () {
		try {
			ConstructorAccess<HasPublicConstructor> access = ConstructorAccess.get(HasPublicConstructor.class);
			HasPublicConstructor newInstance = access.newInstance();
			assertEquals("cow", newInstance.getMoo());
		}
		catch (Throwable t) {
			System.out.println("Unexpected exception happened: " + t);
			t.printStackTrace();
			assertTrue(false);
		}
	}

	static class PackagePrivateClass {
		public String name;
		public int intValue;
		protected float test1;
		Float test2;
		private String test3;

		public boolean equals (Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			PackagePrivateClass other = (PackagePrivateClass)obj;
			if (intValue != other.intValue) return false;
			if (name == null) {
				if (other.name != null) return false;
			} else if (!name.equals(other.name)) return false;
			if (Float.floatToIntBits(test1) != Float.floatToIntBits(other.test1)) return false;
			if (test2 == null) {
				if (other.test2 != null) return false;
			} else if (!test2.equals(other.test2)) return false;
			if (test3 == null) {
				if (other.test3 != null) return false;
			} else if (!test3.equals(other.test3)) return false;
			return true;
		}
	}

	static public class SomeClass {
		public String name;
		public int intValue;
		protected float test1;
		Float test2;
		private String test3;

		public boolean equals (Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			SomeClass other = (SomeClass)obj;
			if (intValue != other.intValue) return false;
			if (name == null) {
				if (other.name != null) return false;
			} else if (!name.equals(other.name)) return false;
			if (Float.floatToIntBits(test1) != Float.floatToIntBits(other.test1)) return false;
			if (test2 == null) {
				if (other.test2 != null) return false;
			} else if (!test2.equals(other.test2)) return false;
			if (test3 == null) {
				if (other.test3 != null) return false;
			} else if (!test3.equals(other.test3)) return false;
			return true;
		}
	}
	
	static public class HasArgumentConstructor {
		public String moo;

		public HasArgumentConstructor (String moo) {
			this.moo = moo;
		}

		public boolean equals (Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			HasArgumentConstructor other = (HasArgumentConstructor)obj;
			if (moo == null) {
				if (other.moo != null) return false;
			} else if (!moo.equals(other.moo)) return false;
			return true;
		}
		
		public String getMoo() {
			return moo;
		}
	}

	static public class HasPrivateConstructor extends HasArgumentConstructor {
		private HasPrivateConstructor () {
			super("cow");
		}
	}

	static public class HasProtectedConstructor extends HasPrivateConstructor {
		protected HasProtectedConstructor () {
			super();
		}
	}

	static public class HasPackageProtectedConstructor extends HasProtectedConstructor {
		HasPackageProtectedConstructor () {
			super();
		}
	}

	static public class HasPublicConstructor extends HasPackageProtectedConstructor {
		public HasPublicConstructor () {
			super();
		}
	}
}
