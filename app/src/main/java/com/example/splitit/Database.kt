package com.example.splitit

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    companion object {
        private const val DATABASE_NAME = "db"

        //Users Table
        private const val NAME ="name"
        private const val LASTNAME ="lastname"
        private const val EMAIL ="lastname"
        private const val PASSWORD ="lastname"

        //Friends Table
        private const val ID_FRIEND ="id_friend"  //PK
        private const val ID_USER1 ="id_user1" //FK from users
        private const val ID_USER2 ="id_user2" //FK from users
        private const val STATUS ="status"

        // Splits TABLE
        private const val ID_SPLIT ="id_split" //PK
        private const val ID_USER ="id_user" //FK from users
        private const val TOTAL ="total" // Total Amount
        private const val DATE ="date"

        // TODO
        // Split details TABLE
        //private const val ID_DETAIL ="id_detail" //PK
        //private const val ID_PAYMENT ="id_payment" FK from Payment
        //private const val ID_USER ="id_user" //FK from users
        private const val INDIVIDUAL_AMOUNT ="individual_amount"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // CREATE USERS TABLE
        db?.execSQL("CREATE TABLE si_users(id integer primary key autoincrement, name varchar, lastname varchar, email varchar, password varchar )")

        val usersCV = ContentValues() // INSERT INTO USERS TABLE
        usersCV.put(NAME, "Mauricio")
        usersCV.put(LASTNAME, "Solano Nájera")
        usersCV.put(EMAIL, "mau.solano@gmail.com")
        usersCV.put(PASSWORD, "holamundo123")
        db?.insert("si_users", NAME, usersCV)

        usersCV.put(NAME, "Andres")
        usersCV.put(LASTNAME, "Orihuela Otero")
        usersCV.put(EMAIL, "andres.orihuela@gmail.com")
        usersCV.put(PASSWORD, "andres123")
        db?.insert("si_users", NAME, usersCV)

        usersCV.put(NAME, "Sebastian")
        usersCV.put(LASTNAME, "Moran Hernandez")
        usersCV.put(EMAIL, "sebas.moran@gmail.com")
        usersCV.put(PASSWORD, "everlong123")
        db?.insert("si_users", NAME, usersCV)

        // CREATE FRIENDS TABLE
        db?.execSQL("CREATE TABLE si_friends(id_friend integer primary key autoincrement, id_user1 int, id_user2 int, status varchar, FOREIGN KEY (id_user1) REFERENCES si_users(id), FOREIGN KEY (id_user2) REFERENCES si_users(id))")

        val friendsCV = ContentValues() // INSERT INTO FRIENDS TABLE
        friendsCV.put(ID_USER1, 1)
        friendsCV.put(ID_USER2, 2)
        friendsCV.put(STATUS, "accepted") //Friends
        db?.insert("si_friends", ID_USER1, friendsCV)

        friendsCV.put(ID_USER1, 1)
        friendsCV.put(ID_USER2, 3) //Friends
        friendsCV.put(STATUS, "accepted")
        db?.insert("si_friends", ID_USER1, friendsCV)

        friendsCV.put(ID_USER1, 2)
        friendsCV.put(ID_USER2, 3)
        friendsCV.put(STATUS, "waiting") //Not friends yet
        db?.insert("si_friends", ID_USER1, friendsCV)
        
        //CREATE PAYMENTS TABLE
        db?.execSQL("create table si_splits(id_split integer primary key autoincrement, id_user int, total decimal, date date, FOREIGN KEY (id_user) REFERENCES si_users(id))")
        
        val splitsCV = ContentValues() //INSERT VALUES INTO PAYMENTS TABLE
        splitsCV.put(ID_USER, 1)
        splitsCV.put(TOTAL, 102.00)
        splitsCV.put(DATE, "2023-11-16")
        db?.insert("si_splits", ID_USER, splitsCV)

        splitsCV.put(ID_USER, 2)
        splitsCV.put(TOTAL, 500.00)
        splitsCV.put(DATE, "2023-11-17")
        db?.insert("si_splits", ID_USER, splitsCV)

        splitsCV.put(ID_USER, 3)
        splitsCV.put(TOTAL, 600.00)
        splitsCV.put(DATE, "2023-11-18")
        db?.insert("si_splits", ID_USER, splitsCV)

        //CREATE SPLIT DETAILS TABLE
        db?.execSQL("create table si_split_details(id_detail integer primary key autoincrement, id_split int, id_user int, individual_amount decimal, FOREIGN KEY (id_split) REFERENCES si_splits(id_split), FOREIGN KEY (id_user) REFERENCES si_users(id))")

        val splitDetailsCV = ContentValues() //INSERT VALUES INTO PAYMENT DETAILS TABLE
        //SPLIT FIRST PAYMENT
        splitDetailsCV.put(ID_SPLIT, 1)
        splitDetailsCV.put(ID_USER, 2)
        splitDetailsCV.put(INDIVIDUAL_AMOUNT, 34.00)
        db?.insert("si_split_details", ID_SPLIT, splitDetailsCV)

        splitDetailsCV.put(ID_SPLIT, 1)
        splitDetailsCV.put(ID_USER, 3)
        splitDetailsCV.put(INDIVIDUAL_AMOUNT, 34.00)
        db?.insert("si_split_details", ID_SPLIT, splitDetailsCV)

        splitDetailsCV.put(ID_SPLIT, 1)
        splitDetailsCV.put(ID_USER, 1)
        splitDetailsCV.put(INDIVIDUAL_AMOUNT, 34.00)
        db?.insert("si_split_details", ID_SPLIT, splitDetailsCV)

        //SPLIT SECOND PAYMENT
        splitDetailsCV.put(ID_SPLIT, 2)
        splitDetailsCV.put(ID_USER, 1)
        splitDetailsCV.put(INDIVIDUAL_AMOUNT, 250.00)
        db?.insert("si_split_details", ID_SPLIT, splitDetailsCV)

        splitDetailsCV.put(ID_SPLIT, 2)
        splitDetailsCV.put(ID_USER, 3)
        splitDetailsCV.put(INDIVIDUAL_AMOUNT, 150.00)
        db?.insert("si_split_details", ID_SPLIT, splitDetailsCV)

        splitDetailsCV.put(ID_SPLIT, 2)
        splitDetailsCV.put(ID_USER, 2)
        splitDetailsCV.put(INDIVIDUAL_AMOUNT, 100.00)
        db?.insert("si_split_details", ID_SPLIT, splitDetailsCV)

        //SPLIT THIRD PAYMENT
        splitDetailsCV.put(ID_SPLIT, 3)
        splitDetailsCV.put(ID_USER, 2)
        splitDetailsCV.put(INDIVIDUAL_AMOUNT, 550.50)
        db?.insert("si_split_details", ID_SPLIT, splitDetailsCV)

        splitDetailsCV.put(ID_SPLIT, 3)
        splitDetailsCV.put(ID_USER, 3)
        splitDetailsCV.put(INDIVIDUAL_AMOUNT, 49.50)
        db?.insert("si_split_details", ID_SPLIT, splitDetailsCV)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        android.util.Log.w("constants", "Actualizando la base de datos. Se destruirá la información anterior")
    }
}