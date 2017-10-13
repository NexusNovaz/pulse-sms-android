/*
 * Copyright (C) 2017 Luke Klinker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.klinker.messenger.shared.data.model

import android.database.Cursor

import xyz.klinker.messenger.api.entity.ScheduledMessageBody
import xyz.klinker.messenger.shared.data.DatabaseSQLiteHelper
import xyz.klinker.messenger.encryption.EncryptionUtils

/**
 * Table for holding drafts for a conversation.
 */
class ScheduledMessage : DatabaseTable {

    var id: Long = 0
    var title: String? = null
    var to: String? = null
    var data: String? = null
    var mimeType: String? = null
    var timestamp: Long = 0

    constructor()
    constructor(body: ScheduledMessageBody) {
        this.id = body.deviceId
        this.title = body.title
        this.to = body.to
        this.data = body.data
        this.mimeType = body.mimeType
        this.timestamp = body.timestamp
    }

    override fun getCreateStatement() = DATABASE_CREATE
    override fun getTableName() = TABLE
    override fun getIndexStatements() = emptyArray<String>()

    override fun fillFromCursor(cursor: Cursor) {
        for (i in 0 until cursor.columnCount) {
            when (cursor.getColumnName(i)) {
                COLUMN_ID -> this.id = cursor.getLong(i)
                COLUMN_TITLE -> this.title = cursor.getString(i)
                COLUMN_TO -> this.to = cursor.getString(i)
                COLUMN_DATA -> this.data = cursor.getString(i)
                COLUMN_MIME_TYPE -> this.mimeType = cursor.getString(i)
                COLUMN_TIMESTAMP -> this.timestamp = cursor.getLong(i)
            }
        }
    }

    override fun encrypt(utils: EncryptionUtils) {
        this.title = utils.encrypt(this.title)
        this.to = utils.encrypt(this.to)
        this.data = utils.encrypt(this.data)
        this.mimeType = utils.encrypt(this.mimeType)
    }

    override fun decrypt(utils: EncryptionUtils) {
        try {
            this.title = utils.decrypt(this.title)
            this.to = utils.decrypt(this.to)
            this.data = utils.decrypt(this.data)
            this.mimeType = utils.decrypt(this.mimeType)
        } catch (e: Exception) {
        }
    }

    companion object {

        val TABLE = "scheduled_message"
        val COLUMN_ID = "_id"
        val COLUMN_TITLE = "title"
        val COLUMN_TO = "phone_number"
        val COLUMN_DATA = "data"
        val COLUMN_MIME_TYPE = "mime_type"
        val COLUMN_TIMESTAMP = "timestamp"

        private val DATABASE_CREATE = "create table if not exists " +
                TABLE + " (" +
                COLUMN_ID + " integer primary key, " +
                COLUMN_TITLE + " text not null, " +
                COLUMN_TO + " text not null, " +
                COLUMN_DATA + " text not null, " +
                COLUMN_MIME_TYPE + " text not null, " +
                COLUMN_TIMESTAMP + " integer not null" +
                ");"
    }

}
