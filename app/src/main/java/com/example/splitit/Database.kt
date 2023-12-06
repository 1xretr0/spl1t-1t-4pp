package com.example.splitit

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        // CREATE USERS TABLE
        db.execSQL("CREATE TABLE si_users(id_user integer primary key autoincrement, name varchar, lastname varchar, email varchar, password varchar)")

        val usersCV = ContentValues() // INSERT INTO USERS TABLE
        usersCV.put(NAME, "Mauricio")
        usersCV.put(LASTNAME, "Solano Nájera")
        usersCV.put(EMAIL, "mau.solano@gmail.com")
        usersCV.put(PASSWORD, "holamundo123")
        db.insert("si_users", NAME, usersCV)

        usersCV.put(NAME, "Andres")
        usersCV.put(LASTNAME, "Orihuela Otero")
        usersCV.put(EMAIL, "andres.orihuela@gmail.com")
        usersCV.put(PASSWORD, "andres123")
        db.insert("si_users", NAME, usersCV)

        usersCV.put(NAME, "Sebastian")
        usersCV.put(LASTNAME, "Moran Hernandez")
        usersCV.put(EMAIL, "sebas.moran@gmail.com")
        usersCV.put(PASSWORD, "everlong123")
        db.insert("si_users", NAME, usersCV)

        // CREATE FRIENDS TABLE
        db.execSQL("CREATE TABLE si_friends(id_friend integer primary key autoincrement, id_user1 int, id_user2 int, FOREIGN KEY (id_user1) REFERENCES si_users(id), FOREIGN KEY (id_user2) REFERENCES si_users(id))")

        /* val friendsCV = ContentValues() // INSERT INTO FRIENDS TABLE
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
        db?.insert("si_friends", ID_USER1, friendsCV) */
        
        //CREATE SPLITS TABLE
        db.execSQL("CREATE TABLE si_splits(id_split integer primary key autoincrement, id_user int, store_name varchar, total decimal, status int, date date, FOREIGN KEY (id_user) REFERENCES si_users(id))")
        
        val splitsCV = ContentValues() //INSERT VALUES INTO PAYMENTS TABLE
        splitsCV.put(ID_USER, 1)
        splitsCV.put(STORE_NAME, "Perro Cafe")
        splitsCV.put(TOTAL, 102.00)
        splitsCV.put(STATUS, 1)
        splitsCV.put(DATE, "2023-11-16")
        db.insert("si_splits", ID_USER, splitsCV)

        /*splitsCV.put(ID_USER, 2)
        splitsCV.put(TOTAL, 500.00)
        splitsCV.put(DATE, "2023-11-17")
        db?.insert("si_splits", ID_USER, splitsCV)

        splitsCV.put(ID_USER, 3)
        splitsCV.put(TOTAL, 600.00)
        splitsCV.put(DATE, "2023-11-18")
        db?.insert("si_splits", ID_USER, splitsCV)*/

        //CREATE SPLIT DETAILS TABLE
        db.execSQL("CREATE TABLE si_split_details(id_detail integer primary key autoincrement, id_split int, id_user int, individual_amount decimal, FOREIGN KEY (id_split) REFERENCES si_splits(id_split), FOREIGN KEY (id_user) REFERENCES si_users(id))")

        /*val splitDetailsCV = ContentValues() //INSERT VALUES INTO PAYMENT DETAILS TABLE
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
        db?.insert("si_split_details", ID_SPLIT, splitDetailsCV)*/
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        android.util.Log.w("constants", "Updating database. Data will be removed!")
        db.execSQL("DROP TABLE IF EXISTS si_users")
        db.execSQL("DROP TABLE IF EXISTS si_friends")
        db.execSQL("DROP TABLE IF EXISTS si_splits")
        db.execSQL("DROP TABLE IF EXISTS si_split_details")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "db"

        //Users Table
        const val NAME ="name"
        const val LASTNAME ="lastname"
        const val EMAIL ="email"
        const val PASSWORD ="password"

        //Friends Table
        const val ID_FRIEND ="id_friend"  //PK
        const val ID_USER1 ="id_user1" //FK from users
        const val ID_USER2 ="id_user2" //FK from users

        // Splits TABLE
        const val ID_SPLIT = "id_split" //PK
        const val ID_USER = "id_user" //FK from users
        const val STORE_NAME = "store_name"
        const val TOTAL = "total" // Total Amount
        const val STATUS = "status" // Split status
        const val DATE = "date"

        // Split details TABLE
        const val ID_DETAIL ="id_detail" //PK
        const val ID_PAYMENT ="id_payment" // FK from Payment
        const val INDIVIDUAL_AMOUNT ="individual_amount"
    }

    // GET METHODS
    /**
     * custom data class to return obtained records from the query
     */
    data class UserRecord(
        var idUser: String? = null,
        var name: String? = null,
        var lastname: String? = null,
        var email: String? = null,
        var password: String? = null
    )
    fun getUserFromDB(
        projection: Array<String>? = null,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        order: String? = null): List<UserRecord>
    {
        val db = this.readableDatabase
        val cursor = db.query(
            "si_users",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            order
        )

        val result = mutableListOf<UserRecord>()
        with(cursor) {
            while (moveToNext()) {
                if (projection != null) {
                    val userRecord = UserRecord()
                    for (field in projection) {
                        when (field) {
                            ID_USER -> userRecord.idUser = getString(getColumnIndexOrThrow(ID_USER))
                            NAME -> userRecord.name = getString(getColumnIndexOrThrow(NAME))
                            LASTNAME -> userRecord.lastname = getString(getColumnIndexOrThrow(LASTNAME))
                            EMAIL -> userRecord.email = getString(getColumnIndexOrThrow(EMAIL))
                            PASSWORD -> userRecord.password = getString(getColumnIndexOrThrow(PASSWORD))
                            else -> println("entered ELSE in UserRecord when exp")
                        }
                    }
                    result.add(userRecord)
                }
                else {
                    val userRecord = UserRecord(
                        getString(getColumnIndexOrThrow(ID_USER)),
                        getString(getColumnIndexOrThrow(NAME)),
                        getString(getColumnIndexOrThrow(LASTNAME)),
                        getString(getColumnIndexOrThrow(EMAIL)),
                        getString(getColumnIndexOrThrow(PASSWORD)),
                    )
                    result.add(userRecord)
                }
            }
        }
        cursor.close()

        return result
    }

    /**
     * custom data class to return obtained records from the query
     */
    data class SplitRecord(
        var idSplit: String? = null,
        var idUser: String? = null,
        var storeName: String? = null,
        var total: String? = null,
        var status: String? = null,
        var date: String? = null
    )
    fun getSplitsFromDB(
        projection: Array<String>? = null,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        order: String? = null) : List<SplitRecord>
    {
        val db = this.readableDatabase
        val cursor = db.query(
            "si_splits",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            order
        )

        val result = mutableListOf<SplitRecord>()
        with(cursor) {
            while (moveToNext()) {
                if (projection != null) {
                    val splitRecord = SplitRecord()
                    for (field in projection) {
                        when (field) {
                            ID_SPLIT -> splitRecord.idSplit = getString(getColumnIndexOrThrow(ID_SPLIT))
                            ID_USER -> splitRecord.idUser = getString(getColumnIndexOrThrow(ID_USER))
                            STORE_NAME -> splitRecord.storeName = getString(getColumnIndexOrThrow(STORE_NAME))
                            TOTAL -> splitRecord.total = getString(getColumnIndexOrThrow(TOTAL))
                            STATUS -> splitRecord.status = getString(getColumnIndexOrThrow(STATUS))
                            DATE -> splitRecord.date = getString(getColumnIndexOrThrow(DATE))
                            else -> println("entered ELSE in FriendRecord when exp")
                        }
                    }
                    result.add(splitRecord)
                }
                else {
                    val splitRecord = SplitRecord(
                        getString(getColumnIndexOrThrow(ID_SPLIT)),
                        getString(getColumnIndexOrThrow(ID_USER)),
                        getString(getColumnIndexOrThrow(STORE_NAME)),
                        getString(getColumnIndexOrThrow(TOTAL)),
                        getString(getColumnIndexOrThrow(STATUS)),
                        getString(getColumnIndexOrThrow(DATE))
                    )
                    result.add(splitRecord)
                }
            }
        }
        cursor.close()

        return result
    }

    /**
     * custom data class to return obtained records from the query
     */
    data class FriendRecord(
        var idFriend: String? = null,
        var idUser1: String? = null,
        var idUser2: String? = null,
        var name: String? = null,
        var splitAmount: Float? = null,
        var total: Float? = null
    )
    fun getFriendsFromDB(
        projection: Array<String>? = null,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        order: String? = null): List<FriendRecord>
    {
        val db = this.readableDatabase
        val cursor = db.query(
            "si_friends",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            order
        )

        val result = mutableListOf<FriendRecord>()
        with(cursor) {
            while (moveToNext()) {
                if (projection != null) {
                    val friendRecord = FriendRecord()
                    for (field in projection) {
                        when (field) {
                            ID_FRIEND -> friendRecord.idFriend = getString(getColumnIndexOrThrow(ID_FRIEND))
                            ID_USER1 -> friendRecord.idUser1 = getString(getColumnIndexOrThrow(ID_USER1))
                            ID_USER2 -> friendRecord.idUser2 = getString(getColumnIndexOrThrow(ID_USER2))
                            NAME -> friendRecord.name = getString(getColumnIndexOrThrow(NAME))
                            else -> println("entered ELSE in FriendRecord when exp")
                        }
                    }
                    result.add(friendRecord)
                }
                else {
                    val idFriend = getString(getColumnIndexOrThrow(ID_FRIEND))
                    val idUser1 = getString(getColumnIndexOrThrow(ID_USER1))
                    val idUser2 = getString(getColumnIndexOrThrow(ID_USER2))

                    val friendRecord = FriendRecord(idFriend, idUser1, idUser2)
                    result.add(friendRecord)
                }
            }
        }
        cursor.close()

        return result
    }

    // INSERT METHODS
    fun insertIntoFriends(userId1: String, userId2: String) : Long? {
        val db = this.readableDatabase
        val values = ContentValues().apply {
            put(ID_USER1, userId1)
            put(ID_USER2, userId2)
        }

        return db?.insert("si_friends", null, values)
    }
}