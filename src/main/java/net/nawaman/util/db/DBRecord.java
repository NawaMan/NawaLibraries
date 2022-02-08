package net.nawaman.util.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import net.nawaman.util.UClass;
import net.nawaman.util.UObject;
import net.nawaman.util.UString;

abstract public class DBRecord {
	
	private static Hashtable<String, DBTable> Tables = new Hashtable<String, DBTable>();
	
	static public DBTable registerTable(String Name, Database DB, String TableName, Class<? extends DBRecord> Cls) {
		if(Name      == null) return null;
		if(DB        == null) return null;
		if(TableName == null) return null;
		if(Cls       == null) return null;
		
		DBTable DBT = new DBTable(Name, DB, TableName, Cls);
		Tables.put(Name, DBT);
		return DBT;
	}
	
	DBTable Table = null;
	
	protected DBRecord(DBTable pTable) {
		this.Table = pTable;
	}
	
	// Status --------------------------------------------------------

	public boolean IsSyncronized = true;
	
	// Load & Save ---------------------------------------------------
	
	/** Load data from the DB */
	protected void load(String[] FieldNames) {
		boolean   isAll      = (FieldNames == null);
		boolean   isNonDelay = (FieldNames != null) && (FieldNames.length == 0);
		ResultSet RS         = null;
		try {
			Statement StmLoad = this.Table.Database.Conn.createStatement();
			
			// Get All Primitive Key
			StringBuffer SB = new StringBuffer();
			String[] Ks = this.Table.getKeyNames();
			try {
				for(String S : Ks) {
					if(SB.length() != 0) SB.append(",");
					SB.append("`").append(S).append("`");
					SB.append("=");
					DBField DBF = this.Table.TableFields.get(S);
					String V = UObject.toString(UClass.getFieldValue(DBF.getField(), this));
					SB.append("'").append(UString.escapeText(V)).append("'");
				}
			} catch(Exception E) {
				throw new RuntimeException("An error occur while loading a record.", E);
			}
			String K = SB.toString();
			
			// Get All field to be load
			String F = isAll?"*":(isNonDelay?this.Table.getNonDelayedStr():null);
			if((FieldNames != null) && (FieldNames.length != 0)) {
				SB.delete(0, SB.length());
				for(String S : FieldNames) {
					if(SB.length() != 0) SB.append(",");
					SB.append("`").append(S).append("`");
				}
				F = SB.toString();
			}
			
			String SQLStr = "SELECT "+F+" FROM "+this.Table.TableName+" WHERE "+K+" LIMIT 0,1";
			RS = StmLoad.executeQuery(SQLStr);
			
			if(RS.next()) {
				if((FieldNames != null) && (FieldNames.length != 0)) {
					for(String FN : FieldNames) {
						DBField DBF = this.Table.TableFields.get(FN);
						UClass.setFieldValue(DBF.getField(), this, RS.getObject(DBF.getName()));
					}
				} else {
					for(DBField DBF : this.Table.TableFields.values()) {
						if(!isAll && DBF.isDelayed()) continue;
						UClass.setFieldValue(DBF.getField(), this, RS.getObject(DBF.getName()));
					}
				}
				this.IsSyncronized = true;
			} else {
				throw new RuntimeException("Record is not found. (Condition = '"+K+"')");
			}
		} catch(Throwable E) {
			if(E.getClass() == RuntimeException.class) throw (RuntimeException)E;
			throw new RuntimeException("An error occur while loading a record.", E);
		} finally {
			if(RS != null) { try{ RS.close(); } catch(Exception E) {} }
		} 
	}
	
	/*
	protected void load(String Condition) {
		ResultSet RS = null;
		try {
			Statement StmLoad = this.Table.DB.Conn.createStatement();
			String SQLStr = "SELECT * FROM "+Table.Name+" WHERE "+Condition+" LIMIT 0,1";
			RS = StmLoad.executeQuery(SQLStr);
			if(RS.next()) {
				this.onLoad(RS);
				this.IsChanged = false;
			} else {
				throw new RuntimeException("Record is not found. (Condition = '"+Condition+"')");
			}
		} catch(SQLException E) {
			throw new RuntimeException("An error occur while loading a record. (Condition = '"+Condition+"')", E);
		} finally {
			if(RS != null) { try{ RS.close(); } catch(Exception E) {} }
		} 
	}*/
	
	protected void onLoad() throws SQLException {}
	
}