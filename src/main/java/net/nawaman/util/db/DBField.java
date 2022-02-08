package net.nawaman.util.db;

import java.lang.reflect.Field;

public class DBField {
	DBField(Field pF) {
		this.Field      = pF;
		this.Annotation = pF.getAnnotation(DB_Field.class);
		
		if(this.getName() == null) new NullPointerException("Field name is null.");
	}
	Field   Field;
	DB_Field Annotation;

	String getFieldName() { return this.Field.getName(); }
	Field  getField()     { return this.Field;           }
	
	String  getName()        { return this.Annotation.name();      }
	boolean isPrimitiveKey() { return this.Annotation.isKey();     }
	boolean isDelayed()      { return this.Annotation.isDelayed(); }
}