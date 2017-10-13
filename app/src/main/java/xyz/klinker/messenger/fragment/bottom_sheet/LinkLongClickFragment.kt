package xyz.klinker.messenger.fragment.bottom_sheet

import android.app.Dialog
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast

import java.util.Random

import xyz.klinker.android.article.ArticleIntent
import xyz.klinker.messenger.BuildConfig
import xyz.klinker.messenger.R
import xyz.klinker.messenger.activity.ComposeActivity
import xyz.klinker.messenger.shared.data.MimeType
import xyz.klinker.messenger.shared.data.Settings
import xyz.klinker.messenger.shared.data.model.Message
import xyz.klinker.messenger.shared.util.media.parsers.ArticleParser

import android.content.Context.CLIPBOARD_SERVICE

class LinkLongClickFragment : TabletOptimizedBottomSheetDialogFragment() {

    private var link: String? = null
    private var mainColor: Int = 0
    private var accentColor: Int = 0

    override fun createLayout(inflater: LayoutInflater): View {
        val contentView = View.inflate(context, R.layout.bottom_sheet_link, null)

        val openExternal = contentView.findViewById<View>(R.id.open_external)
        val openInternal = contentView.findViewById<View>(R.id.open_internal)
        val copyText = contentView.findViewById<View>(R.id.copy_text)

        openExternal.setOnClickListener { view ->
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(mainColor)
            builder.setShowTitle(true)
            builder.setActionButton(
                    BitmapFactory.decodeResource(resources, R.drawable.ic_share),
                    getString(R.string.share), getShareIntent(link), true)
            val customTabsIntent = builder.build()

            customTabsIntent.launchUrl(activity, Uri.parse(link))
            dismiss()
        }

        openInternal.setOnClickListener { view ->
            val intent = ArticleIntent.Builder(contentView.context, ArticleParser.ARTICLE_API_KEY)
                    .setToolbarColor(mainColor)
                    .setAccentColor(accentColor)
                    .setTheme(if (Settings.get(contentView.context).isCurrentlyDarkTheme)
                        ArticleIntent.THEME_DARK
                    else
                        ArticleIntent.THEME_LIGHT)
                    .setTextSize(Settings.get(contentView.context).mediumFont + 1)
                    .build()

            intent.launchUrl(contentView.context, Uri.parse(link))
            dismiss()
        }

        copyText.setOnClickListener { view ->
            val clipboard = activity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("messenger", link)
            clipboard.primaryClip = clip
            Toast.makeText(activity, R.string.message_copied_to_clipboard,
                    Toast.LENGTH_SHORT).show()

            dismiss()
        }

        return contentView
    }


    fun setLink(link: String) {
        this.link = link
    }

    fun setColors(mainColor: Int, accentColor: Int) {
        this.mainColor = mainColor
        this.accentColor = accentColor
    }

    private fun getShareIntent(url: String?): PendingIntent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        shareIntent.type = MimeType.TEXT_PLAIN
        return PendingIntent.getActivity(activity, Random().nextInt(Integer.MAX_VALUE), shareIntent, 0)
    }
}
