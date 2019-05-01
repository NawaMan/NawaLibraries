package net.nawaman.util.db;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface DB_Field {
	
	String  name();
	boolean isKey()     default false;
	boolean isDelayed() default false;

}
