package com.variantxx.codefest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "CodeFestDB";
    private static final int DB_VERSION = 1;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL" +
                ")";
        String createNotesTable = "CREATE TABLE IF NOT EXISTS notes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "title TEXT NOT NULL," +
                "content TEXT NOT NULL," +
                "FOREIGN KEY(user_id) REFERENCES users(id)" +
                ")";
        String createCakesTable = "CREATE TABLE IF NOT EXISTS cakes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "thumbnail Text NOT NULL," +
                "name TEXT NOT NULL," +
                "description TEXT NOT NULL" +
                ")";
        db.execSQL(createUsersTable);
        db.execSQL(createNotesTable);
        db.execSQL(createCakesTable);
    }


    // USERS METHODS
    public void addUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertUser = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        db.execSQL(insertUser, new Object[]{username, email, password});
        db.close();
    }


    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
            users.add(new User(id, username, email, password));
        }
        cursor.close();
        db.close();
        return users;
    }


    // NOTES METHODS
    // Get all notes for a specific user.
    public ArrayList<Note> getNotesByUser(int userId) {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM notes WHERE user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") int ownerId = cursor.getInt(cursor.getColumnIndex("user_id"));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
            notes.add(new Note(id, ownerId, title, content));
        }
        cursor.close();
        db.close();
        return notes;
    }

    public int getUserIdByEmail(String email) {
        int id = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT id FROM users WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("id"));
            id = userId;
        }
        cursor.close();
        db.close();
        return id;
    }


    // Add a new note for a specific user.
    public void addNote(int userId, String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertNote = "INSERT INTO notes (user_id, title, content) VALUES (?, ?, ?)";
        db.execSQL(insertNote, new Object[]{userId, title, content});
        db.close();
    }


    // Update an existing note by its ID.
    public void updateNote(int noteId, String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateNote = "UPDATE notes SET title = ?, content = ? WHERE id = ?";
        db.execSQL(updateNote, new Object[]{title, content, noteId});
        db.close();
    }


    // Delete a note by its ID.
    public void deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteNote = "DELETE FROM notes WHERE id = ?";
        db.execSQL(deleteNote, new Object[]{noteId});
        db.close();
    }


    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }


    public ArrayList<Cake> getAllCakes() {
        ArrayList<Cake> cakes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM cakes";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String thumbnail = cursor.getString(cursor.getColumnIndex("thumbnail"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            cakes.add(new Cake(id, thumbnail, name, description));
        }
        cursor.close();
        db.close();
        return cakes;
    }

    public void addCake(String thumbnail, String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertCake = "INSERT INTO cakes (thumbnail, name, description) VALUES (?, ?, ?)";
        db.execSQL(insertCake, new Object[]{thumbnail, name, description});
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS notes");
        db.execSQL("DROP TABLE IF EXISTS cakes");
        onCreate(db);
    }

}
