package Utils;

import android.app.Activity;
import android.content.Intent;

import com.phimy.R;

/**
 * Created by wpenia on 28/10/18.
 */

public class ThemeUtils {
    private static int cTheme;
    public final static int APP = 1;
    public final static int APP_ALTERNATIVA1 = 0;
    public final static int APP_ALTERNATIVA2 = 1;
    public final static int APP_ALTERNATIVA3 = 2;

    public static void changeToTheme(Activity activity, int theme) {
        cTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void setcTheme(int cTheme) {
        ThemeUtils.cTheme = cTheme;
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (cTheme){
            default:
            case APP_ALTERNATIVA1:
                activity.setTheme(R.style.AppGreyTheme);
                break;
            case APP_ALTERNATIVA2:
                activity.setTheme(R.style.AppAmberTheme);
                break;
            case APP_ALTERNATIVA3:
                activity.setTheme(R.style.AppThemeMedia);
                break;
        }

    }

 }

