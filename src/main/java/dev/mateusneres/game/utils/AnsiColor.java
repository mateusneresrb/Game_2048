/**
 * Usage:
 * <li>String msg = AnsiColor.Red.and(AnsiColor.BgYellow).format("Hello %s", name)</li>
 * <li>String msg = AnsiColor.Blink.colorize("BOOM!")</li>
 * <p>
 * Or, if you are adverse to that, you can use the constants directly:
 * <li>String msg = new AnsiColor(AnsiColor.ITALIC, AnsiColor.GREEN).format("Green money")</li>
 * Or, even:
 * <li>String msg = AnsiColor.BLUE + "scientific"</li>
 * <p>
 * NOTE: Nothing stops you from combining multiple FG colors or BG colors,
 * but only the last one will display.
 *
 * @author dain
 */

package dev.mateusneres.game.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnsiColor {

    // Color code strings from:
    // http://www.topmudsites.com/forums/mud-coding/413-java-AnsiColor.html
    public static final String SANE = "\u001B[0m";

    public static final String HIGH_INTENSITY = "\u001B[1m";
    public static final String LOW_INTENSITY = "\u001B[2m";

    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String BLINK = "\u001B[5m";
    public static final String RAPID_BLINK = "\u001B[6m";
    public static final String REVERSE_VIDEO = "\u001B[7m";
    public static final String INVISIBLE_TEXT = "\u001B[8m";

    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BACKGROUND_BLACK = "\u001B[40m";
    public static final String BACKGROUND_RED = "\u001B[41m";
    public static final String BACKGROUND_GREEN = "\u001B[42m";
    public static final String BACKGROUND_YELLOW = "\u001B[43m";
    public static final String BACKGROUND_BLUE = "\u001B[44m";
    public static final String BACKGROUND_MAGENTA = "\u001B[45m";
    public static final String BACKGROUND_CYAN = "\u001B[46m";
    public static final String BACKGROUND_WHITE = "\u001B[47m";

    public static final AnsiColor HighIntensity = new AnsiColor(HIGH_INTENSITY);
    public static final AnsiColor Bold = HighIntensity;
    public static final AnsiColor LowIntensity = new AnsiColor(LOW_INTENSITY);
    public static final AnsiColor Normal = LowIntensity;

    public static final AnsiColor Italic = new AnsiColor(ITALIC);
    public static final AnsiColor Underline = new AnsiColor(UNDERLINE);
    public static final AnsiColor Blink = new AnsiColor(BLINK);
    public static final AnsiColor RapidBlink = new AnsiColor(RAPID_BLINK);

    public static final AnsiColor Black = new AnsiColor(BLACK);
    public static final AnsiColor Red = new AnsiColor(RED);
    public static final AnsiColor Green = new AnsiColor(GREEN);
    public static final AnsiColor Yellow = new AnsiColor(YELLOW);
    public static final AnsiColor Blue = new AnsiColor(BLUE);
    public static final AnsiColor Magenta = new AnsiColor(MAGENTA);
    public static final AnsiColor Cyan = new AnsiColor(CYAN);
    public static final AnsiColor White = new AnsiColor(WHITE);

    public static final AnsiColor BgBlack = new AnsiColor(BACKGROUND_BLACK);
    public static final AnsiColor BgRed = new AnsiColor(BACKGROUND_RED);
    public static final AnsiColor BgGreen = new AnsiColor(BACKGROUND_GREEN);
    public static final AnsiColor BgYellow = new AnsiColor(BACKGROUND_YELLOW);
    public static final AnsiColor BgBlue = new AnsiColor(BACKGROUND_BLUE);
    public static final AnsiColor BgMagenta = new AnsiColor(BACKGROUND_MAGENTA);
    public static final AnsiColor BgCyan = new AnsiColor(BACKGROUND_CYAN);
    public static final AnsiColor BgWhite = new AnsiColor(BACKGROUND_WHITE);

    final private String[] codes;
    final private String codes_str;

    public AnsiColor(String... codes) {
        this.codes = codes;
        String _codes_str = "";
        for (String code : codes) {
            _codes_str += code;
        }
        codes_str = _codes_str;
    }

    public AnsiColor and(AnsiColor other) {
        List<String> both = new ArrayList<String>();
        Collections.addAll(both, codes);
        Collections.addAll(both, other.codes);
        return new AnsiColor(both.toArray(new String[]{}));
    }

    public String colorize(String original) {
        return codes_str + original + SANE;
    }

    public String format(String template, Object... args) {
        return colorize(String.format(template, args));
    }

}
