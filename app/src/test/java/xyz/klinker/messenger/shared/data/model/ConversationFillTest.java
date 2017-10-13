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

package xyz.klinker.messenger.shared.data.model;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;

import org.junit.Test;

import xyz.klinker.messenger.MessengerRobolectricSuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConversationFillTest extends MessengerRobolectricSuite {

    @Test
    public void fillFromCursor() {
        Conversation conversation = new Conversation();
        Cursor cursor = createCursor();
        cursor.moveToFirst();
        conversation.fillFromCursor(cursor);

        assertEquals(1, conversation.getId());
        assertEquals(Color.RED, conversation.getColors().color);
        assertEquals(Color.BLUE, conversation.getColors().colorDark);
        assertEquals(Color.YELLOW, conversation.getColors().colorLight);
        assertEquals(Color.GREEN, conversation.getColors().colorAccent);
        assertEquals(Color.WHITE, conversation.getLedColor());
        assertTrue(conversation.getPinned());
        assertTrue(conversation.getRead());
        assertEquals(1000L, conversation.getTimestamp());
        assertEquals("Luke Klinker", conversation.getTitle());
        assertEquals("So maybe not going to be able to get platinum huh?", conversation.getSnippet());
        assertEquals("uri", conversation.getRingtoneUri());
        assertEquals("image_uri", conversation.getImageUri());
        assertEquals("11493", conversation.getIdMatcher());
        assertFalse(conversation.getMute());
        assertFalse(conversation.getPrivateNotifications());
    }

    private Cursor createCursor() {
        MatrixCursor cursor = new MatrixCursor(new String[]{
                Conversation.Companion.getCOLUMN_ID(),
                Conversation.Companion.getCOLUMN_COLOR(),
                Conversation.Companion.getCOLUMN_COLOR_DARK(),
                Conversation.Companion.getCOLUMN_COLOR_LIGHT(),
                Conversation.Companion.getCOLUMN_COLOR_ACCENT(),
                Conversation.Companion.getCOLUMN_LED_COLOR(),
                Conversation.Companion.getCOLUMN_PINNED(),
                Conversation.Companion.getCOLUMN_READ(),
                Conversation.Companion.getCOLUMN_TIMESTAMP(),
                Conversation.Companion.getCOLUMN_TITLE(),
                Conversation.Companion.getCOLUMN_PHONE_NUMBERS(),
                Conversation.Companion.getCOLUMN_SNIPPET(),
                Conversation.Companion.getCOLUMN_RINGTONE(),
                Conversation.Companion.getCOLUMN_IMAGE_URI(),
                Conversation.Companion.getCOLUMN_ID_MATCHER(),
                Conversation.Companion.getCOLUMN_MUTE(),
                Conversation.Companion.getCOLUMN_PRIVATE_NOTIFICATIONS()
        });

        cursor.addRow(new Object[]{
                1,
                Color.RED,
                Color.BLUE,
                Color.YELLOW,
                Color.GREEN,
                Color.WHITE,
                1,
                1,
                1000L,
                "Luke Klinker",
                "(515) 991-1493",
                "So maybe not going to be able to get platinum huh?",
                "uri",
                "image_uri",
                "11493",
                0,
                0
        });

        return cursor;
    }

}