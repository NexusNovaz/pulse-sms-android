package xyz.klinker.messenger.shared.util;

import org.hamcrest.Matchers;
import org.junit.Test;

import xyz.klinker.messenger.MessengerSuite;
import xyz.klinker.messenger.shared.data.model.Conversation;

import static org.junit.Assert.*;

public class ContactUtilsTest extends MessengerSuite {

    @Test
    public void shouldConvertLongNumberToPlain() {
        assertThat("5159911493", Matchers.is(ContactUtils.getPlainNumber("+15159911493")));
        assertThat("5159911493", Matchers.is(ContactUtils.getPlainNumber("+5159911493")));
        assertThat("5159911493", Matchers.is(ContactUtils.getPlainNumber("15159911493")));
        assertThat("5159911493", Matchers.is(ContactUtils.getPlainNumber("5159911493")));
        assertThat("9911493", Matchers.is(ContactUtils.getPlainNumber("9911493")));
    }

    @Test
    public void shouldShowImageLetter() {
        Conversation conversation = new Conversation();
        conversation.setPhoneNumbers("555");

        conversation.setTitle("test");
        assertThat(ContactUtils.shouldDisplayContactLetter(conversation), Matchers.is(true));

        conversation.setTitle("Test");
        assertThat(ContactUtils.shouldDisplayContactLetter(conversation), Matchers.is(true));

        conversation.setTitle("Żółć");
        assertThat(ContactUtils.shouldDisplayContactLetter(conversation), Matchers.is(true));

        conversation.setTitle("Ὀδυσσεύς");
        assertThat(ContactUtils.shouldDisplayContactLetter(conversation), Matchers.is(true));

        conversation.setTitle("原田雅彦");
        assertThat(ContactUtils.shouldDisplayContactLetter(conversation), Matchers.is(true));
    }

    @Test
    public void shouldNotShowImageLetter() {
        Conversation conversation = new Conversation();
        conversation.setPhoneNumbers("1, 1");

        conversation.setTitle(" test");
        assertThat(ContactUtils.shouldDisplayContactLetter(conversation), Matchers.is(false));

        conversation.setTitle("5.");
        assertThat(ContactUtils.shouldDisplayContactLetter(conversation), Matchers.is(false));

        conversation.setTitle(".j4");
        assertThat(ContactUtils.shouldDisplayContactLetter(conversation), Matchers.is(false));

        conversation.setTitle("+1");
        assertThat(ContactUtils.shouldDisplayContactLetter(conversation), Matchers.is(false));

        conversation.setTitle("-");
        assertThat(ContactUtils.shouldDisplayContactLetter(conversation), Matchers.is(false));

        conversation.setTitle("'");
        assertThat(ContactUtils.shouldDisplayContactLetter(conversation), Matchers.is(false));

        conversation.setTitle(":");
        assertThat(ContactUtils.shouldDisplayContactLetter(conversation), Matchers.is(false));
    }
}