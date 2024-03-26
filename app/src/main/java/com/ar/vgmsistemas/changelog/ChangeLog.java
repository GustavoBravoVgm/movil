package com.ar.vgmsistemas.changelog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.WebView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import com.ar.vgmsistemas.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ChangeLog {

    private final Context context;
    private String lastVersion, thisVersion;

    private WebView _webView;

    // this is the key for storing the version name in SharedPreferences
    private static final String VERSION_KEY = "PREFS_VERSION_KEY";
    private static final String NO_VERSION = "";

    /**
     * Constructor
     * Retrieves the version names and stores the new version name in SharedPreferences
     *
     * @param context
     */
    public ChangeLog(Context context) {
        this(context, PreferenceManager.getDefaultSharedPreferences(context));
    }

    /**
     * Constructor
     * Retrieves the version names and stores the new version name in SharedPreferences
     *
     * @param context
     * @param sp      the shared preferences to store the last version name into
     */
    public ChangeLog(Context context, SharedPreferences sp) {
        this.context = context;

        // get version numbers from SharedPreferences
        this.lastVersion = sp.getString(VERSION_KEY, NO_VERSION);
        Log.d(TAG, "lastVersion: " + lastVersion);
        try {
            // Retorna el numero de version instalada en el celular (manifest)
            this.thisVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            this.thisVersion = NO_VERSION;
            Log.e(TAG, "could not get version name from manifest!");
            e.printStackTrace();
        }
        Log.d(TAG, "appVersion: " + this.thisVersion);
    }

    /**
     * @return The version name of the last installation of this app (as
     * described in the former manifest). This will be the same as
     * returned by <code>getThisVersion()</code> the second time this
     * version of the app is launched (more precisely: the second time
     * ChangeLog is instantiated).
     * @see "AndroidManifest.xml" #android:versionName
     */
    public String getLastVersion() {
        return this.lastVersion;
    }

    /**
     * @return The version name of this app as described in the manifest.
     * @see "AndroidManifest.xml"#android:versionName
     */
    public String getThisVersion() {
        return this.thisVersion;
    }

    /**
     * @return <code>true</code> if this version of your app is started the first time
     */
    public boolean firstRun() {
        return !this.lastVersion.equals(this.thisVersion);
    }

    /**
     * @return <code>true</code> if your app including ChangeLog is started the
     * first time ever. Also <code>true</code> if your app was
     * desinstalada and installed again.
     */
    public boolean firstRunEver() {
        return NO_VERSION.equals(this.lastVersion);
    }

    /**
     * @return An AlertDialog displaying the changes since the previous
     * installed version of your app (what's new). But when this is the
     * first run of your app including ChangeLog then the full log
     * dialog is show.
     */
    public AlertDialog getLogDialog() {
        return this.getDialog(this.firstRunEver());
    }

    /**
     * @return an AlertDialog with a full change log displayed
     */
    public AlertDialog getFullLogDialog() {
        return this.getDialog(true);
    }

    private AlertDialog getDialog(boolean full) {
        /*WebView wv = new WebView(this.context);*/
        WebView wv = _webView == null ? new WebView(this.context) : _webView;
        // wv.getSettings().setDefaultTextEncodingName("utf-8");
        wv.loadDataWithBaseURL(null, this.getLog(full), "text/html", "UTF-8", null);
        @SuppressLint("RestrictedApi") ContextThemeWrapper wrapper = new ContextThemeWrapper(this.context, R.style.MyTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
        builder.setTitle(
                        context.getResources().getString(
                                full ? R.string.changelog_full_title
                                        : R.string.changelog_title))
                .setView(wv)
                .setCancelable(false)
                // OK button
                /*.setPositiveButton(
                        context.getResources().getString(R.string.changelog_ok_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Actualizo la version de Preferencias cuando la
                                // actualizacion se realiza correctamente
                                // updateVersionInPreferences();
                            }
                        });*/
                .setPositiveButton(context.getResources().getString(R.string.changelog_ok_button),
                        (dialog, which) -> {
                            // Actualizo la version de Preferencias cuando la
                            // actualizacion se realiza correctamente
                            // updateVersionInPreferences();
                        });
        if (!full) {
            // "more ..." button
            builder.setNegativeButton(R.string.changelog_show_full,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getFullLogDialog().show();
                        }
                    });
        }
        return builder.create();
    }

    public void updateVersionInPreferences() {
        // save new version number to preferences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(VERSION_KEY, thisVersion);
        editor.apply();
    }

    /**
     * @return HTML displaying the changes since the previous installed version
     * of your app (what's new)
     */
    public String getLog() {
        return this.getLog(false);
    }

    /**
     * @return HTML which displays full change log
     */
    public String getFullLog() {
        return this.getLog(true);
    }

    /**
     * modes for HTML-Lists (bullet, numbered)
     */
    private enum ListMode {
        NONE, ORDERED, UNORDERED,
    }

    private ListMode listMode = ListMode.NONE;
    private StringBuffer sb = null;
    private static final String EOCL = "END_OF_CHANGE_LOG";

    private String getLog(boolean full) {
        // read changelog.txt file
        sb = new StringBuffer();
        try {
            InputStream ins = context.getResources().openRawResource(R.raw.changelog);
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));

            String line;
            boolean advanceToEOVS = false; // if true: ignore further version
            // sections
            while ((line = br.readLine()) != null) {
                line = line.trim();
                char marker = line.length() > 0 ? line.charAt(0) : 0;
                if (marker == '$') {
                    // begin of a version section
                    this.closeList();
                    String version = line.substring(1).trim();
                    // stop output?
                    if (!full) {
                        if (this.lastVersion.equals(version)) {
                            advanceToEOVS = true;
                        } else if (version.equals(EOCL)) {
                            advanceToEOVS = false;
                        }
                    }
                } else if (!advanceToEOVS) {
                    switch (marker) {
                        case '%':
                            // line contains version title
                            this.closeList();
                            sb.append("<div class='title'>").append(line.substring(1).trim()).append("</div>\n");
                            break;
                        case '_':
                            // line contains version title
                            this.closeList();
                            sb.append("<div class='subtitle'>").append(line.substring(1).trim()).append("</div>\n");
                            break;
                        case '!':
                            // line contains free text
                            this.closeList();
                            sb.append("<div class='freetext'>").append(line.substring(1).trim()).append("</div>\n");
                            break;
                        case '#':
                            // line contains numbered list item
                            this.openList(ListMode.ORDERED);
                            sb.append("<li>").append(line.substring(1).trim()).append("</li>\n");
                            break;
                        case '*':
                            // line contains bullet list item
                            this.openList(ListMode.UNORDERED);
                            sb.append("<li>").append(line.substring(1).trim()).append("</li>\n");
                            break;
                        default:
                            // no special character: just use line as is
                            this.closeList();
                            sb.append(line).append("\n");
                    }
                }
            }
            this.closeList();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String respuesta = sb.toString();
        return respuesta;
    }

    private void openList(ListMode listMode) {
        if (this.listMode != listMode) {
            closeList();
            if (listMode == ListMode.ORDERED) {
                sb.append("<div class='list'><ol>\n");
            } else if (listMode == ListMode.UNORDERED) {
                sb.append("<div class='list'><ul>\n");
            }
            this.listMode = listMode;
        }
    }

    private void closeList() {
        if (this.listMode == ListMode.ORDERED) {
            sb.append("</ol></div>\n");
        } else if (this.listMode == ListMode.UNORDERED) {
            sb.append("</ul></div>\n");
        }
        this.listMode = ListMode.NONE;
    }

    private static final String TAG = "ChangeLog";

    /**
     * manually set the last version name - for testing purposes only
     *
     * @param lastVersion
     */
    void setLastVersion(String lastVersion) {
        this.lastVersion = lastVersion;
    }

    public WebView getWebView() {
        return _webView;
    }

    public void setWebView(WebView _webView) {
        this._webView = _webView;
    }
}