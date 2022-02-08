package net.nawaman.util.db;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Vector;

public class DBTable {
	String                     Name        = null;
	String                     TableName   = null;
	Database                   Database    = null;
	Hashtable<String, DBField> TableFields = null;
	Class<? extends DBRecord>  TableClass  = null;
	
	DBTable(String pName, Database pDatabase, String pDBName, Class<? extends DBRecord> pTableClass) {
		this.Name        = pName;
		this.TableName   = pDBName;
		this.Database    = pDatabase;
		this.TableClass  = pTableClass;

		this.TableFields = new Hashtable<String, DBField>();
		Field[] Fs = pTableClass.getFields();
		for(Field F : Fs) {
			if(!DBRecord.class.isAssignableFrom(F.getDeclaringClass())) continue;
			if(F.getAnnotation(DB_Field.class) == null) continue;
			DBField DBF =  new DBField(F);
			this.TableFields.put(DBF.getName(), DBF);
		}
	}
	
	public String   getTableName() { return this.TableName; }
	public Database getDB()        { return this.Database;  }
	
	String[] KeyNames = null;
	String[] getKeyNames() {
		if(this.KeyNames == null) {
			Vector<String> TKeys = new Vector<String>();
			if(this.TableFields != null) {
				for(DBField DBF : this.TableFields.values()) {
					if(!DBF.isPrimitiveKey()) continue;
					TKeys.add(DBF.getName());
				}
			}
			this.KeyNames = TKeys.toArray(new String[0]);
		}
		return this.KeyNames;
	}
	
	String NonDelayedStr = null;
	String getNonDelayedStr() {
		if(this.NonDelayedStr == null) {
			this.NonDelayedStr = "";
			if(this.TableFields != null) {
				for(DBField DBF : this.TableFields.values()) {
					if(DBF.isDelayed()) continue;
					if(this.NonDelayedStr != "") this.NonDelayedStr += ",";
					this.NonDelayedStr += "`"+DBF.getName()+"`";
				}
			}
		}
		return this.NonDelayedStr;
	}
}