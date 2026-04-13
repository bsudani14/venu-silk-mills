package com.newtech.vplus.Database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_Helper extends SQLiteOpenHelper {

	private final static String Database_Name="Todros.db";
	private static final String TABLE_NAME = "ChatMst";
	private final static int Database_Version=1;
	private SQLiteDatabase sqliteDB;
	private  Database_Helper dbHelper;

	public Database_Helper(Context context) 
	{
		super(context, Database_Name, null, Database_Version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL("create table if not exists andmst(MOBILENO TEXT,PCODE TEXT,LOGIN TEXT,PNAME TEXT,PADDRESS TEXT,BRCODE TEXT,BRNAME TEXT,FTYPE TEXT,GCM_REG_ID TEXT,Android_id TEXT,slectiontype TEXT,dbname TEXT,Potp TEXT,Email TEXT,ROTP TEXT)");
		db.execSQL("create table if not exists email(Email TEXT,MOBILENO TEXT)");
		db.execSQL("create table if not exists categorymst(catcode INTEGER,JsonText TEXT)");
		db.execSQL("create table if not exists addcartsummary(counter int,amount float)");
		db.execSQL("create table if not exists addcartmst(Icode TEXT,JsonText TEXT,ID INTEGER)");
		db.execSQL("create table if not exists addcolorcartmst(Icode TEXT,PK_SHADE TEXT,JsonText TEXT,ID INTEGER)");
		db.execSQL("create table if not exists OrderListdetails_mst(Icode TEXT,PK_SHADE TEXT,JsonText TEXT,ID INTEGER)");
        db.execSQL("create table if not exists Notficationmsg_Mst(Message TEXT,MsgStatus TEXT)");
		db.execSQL("create table if not exists txtfile(pass TEXT)");
		db.execSQL("create table if not exists admintxtfile(admin TEXT)");
		db.execSQL("create table if not exists partyemail(paemail TEXT,salcode TEXT)");
		db.execSQL("create table if not exists dipatch(lotno TEXT,shade TEXT)");
		db.execSQL("create table if not exists Pos(post TEXT)");

	}		

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		onCreate(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) 
	{
		onCreate(db);
		super.onOpen(db);
	}

	public void ResetDatabase()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		db.delete("andmst", null, null);
		db.delete("addcartsummary", null, null);
		db.delete("addcartmst", null, null);
		db.delete("addcolorcartmst", null, null);
		
	}

	public Cursor getUserInfo(Object object) 
	{
		// TODO Auto-generated method stub
		Cursor cursor = sqliteDB.query(TABLE_NAME, null, null, null, null, null,
				null);
		cursor.moveToFirst();
		return cursor;
	}

	public String GetVal(String Str)
	{
		String Value="";
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor getchat = db.rawQuery(Str, null);
		try {
			if (getchat.getCount() > 0) {
				if (getchat.moveToFirst()) {
					try{
						do {
							Value=getchat.getString(0).toString();
						} while (getchat.moveToNext());
					}catch(Exception e)
					{return e.getMessage().toString();}
				}
				return Value;
			}else{
				return Value;
			}
		} finally {
			getchat.close();
		}
	}

	public int GetCode(String Str)
	{
		int Value=0;
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor getchat = db.rawQuery(Str, null);
		try {
			if (getchat.getCount() > 0) {
				if (getchat.moveToFirst()) {
					try{
						do {
							Value=getchat.getInt(0);
						} while (getchat.moveToNext());
					}catch(Exception e)
					{return 0;}
				}
				return Value;
			}else{
				return Value;
			}
		} finally {
			getchat.close();
		}
	}


	public String CheckRegistration(String Str)
	{
		String Value="NOT";
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor getchat = db.rawQuery(Str, null);
		try {
			if (getchat.getCount() > 0) {
				if (getchat.moveToFirst()) {
					try{
						do {
							if (getchat.getInt(0)>0)
							{
								Value="YES";
							}
						} while (getchat.moveToNext());
					}catch(Exception e)
					{return e.getMessage().toString();}
				}
				return Value;
			}else{
				return Value;
			}
		} finally {
			getchat.close();
		}
	}


	public void open(Object object) {
		// TODO Auto-generated method
		sqliteDB=dbHelper.getWritableDatabase();

	}

	public boolean isFieldExist(String tableName, String fieldName)
	{
		boolean isExist = false;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("PRAGMA table_info("+tableName+")",null);
		try {
			if (res.getCount() > 0) {
				if (res.moveToFirst()) {
					try{
						do {
							if (res.getString(1).equals(fieldName))
							{
								isExist = true;
								return isExist;
							}
						} while (res.moveToNext());
					}catch(Exception e)
					{isExist = false; }
				}
			}else{
				isExist = false;
			}
		} finally {
			res.close();
		}
		return isExist;
	}

	
	public boolean isTableExists(SQLiteDatabase db, String tableName)
	{
	    if (tableName == null || db == null || !db.isOpen())
	    {
	        return false;
	    }
	    Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
	    if (!cursor.moveToFirst())
	    {
	        return false;
	    }
	    int count = cursor.getInt(0);
	    cursor.close();
	    return count > 0;
	}

}
