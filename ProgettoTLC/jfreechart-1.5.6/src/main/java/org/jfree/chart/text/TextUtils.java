/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 */

package org.jfree.chart.text;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;
import java.text.BreakIterator;
import org.jfree.chart.ui.TextAnchor;

/**
 * Some utility methods for working with text in Java2D.
 */
public class TextUtils {

    /**
     * When this flag is set to {@code true}, strings will be drawn
     * as attributed strings with the attributes taken from the current font.
     * This allows for underlining, strike-out etc., but it means that
     * TextLayout will be used to render the text:
     * <p>
     * <a href="https://www.jfree.org/phpBB2/viewtopic.php?p=45459&highlight=#45459">https://www.jfree.org/phpBB2/viewtopic.php?p=45459&highlight=#45459"</a>
     */
    private static boolean drawStringsWithFontAttributes = false;
    
    /**
     * A flag that controls whether the rotated string workaround is
     * used.
     */
    private static boolean useDrawRotatedStringWorkaround = true;

    /**
     * A flag that controls whether the FontMetrics.getStringBounds() method
     * is used or a workaround is applied.
     */
    private static boolean useFontMetricsGetStringBounds = false;

    /**
     * Private constructor prevents object creation.
     */
    private TextUtils() {
        // prevent instantiation
    }

    /**
     * Creates a {@link TextBlock} from a {@code String}.  Line breaks
     * are added where the {@code String} contains '\n' characters.
     *
     * @param text  the text.
     * @param font  the font.
     * @param paint  the paint.
     *
     * @return A text block.
     */
    public static TextBlock createTextBlock(String text, Font font, 
            Paint paint) {
        if (text == null) {
            throw new IllegalArgumentException("Null 'text' argument.");
        }
        TextBlock result = new TextBlock();
        String input = text;
        boolean moreInputToProcess = !text.isEmpty();
        int start = 0;
        while (moreInputToProcess) {
            int index = input.indexOf("\n");
            if (index > start) {
                String line = input.substring(start, index);
                if (index < input.length() - 1) {
                    result.addLine(line, font, paint);
                    input = input.substring(index + 1);
                }
                else {
                    moreInputToProcess = false;
                }
            }
            else if (index == start) {
                if (index < input.length() - 1) {
                    input = input.substring(index + 1);
                }
                else {
                    moreInputToProcess = false;
                }
            }
            else {
                result.addLine(input, font, paint);
                moreInputToProcess = false;
            }
        }
        return result;
    }

    /**
     * Creates a new text block from the given string, breaking the
     * text into lines so that the {@code maxWidth} value is respected.
     *
     * @param text  the text.
     * @param font  the font.
     * @param paint  the paint.
     * @param maxWidth  the maximum width for each line.
     * @param measurer  the text measurer.
     *
     * @return A text block.
     */
    public static TextBlock createTextBlock(String text, Font font,
            Paint paint, float maxWidth, TextMeasurer measurer) {
        return createTextBlock(text, font, paint, maxWidth, Integer.MAX_VALUE,
                measurer);
    }

    /**
     * Creates a new text block from the given string, breaking the
     * text into lines so that the {@code maxWidth} value is
     * respected.
     *
     * @param text  the text.
     * @param font  the font.
     * @param paint  the paint.
     * @param maxWidth  the maximum width for each line.
     * @param maxLines  the maximum number of lines.
     * @param measurer  the text measurer.
     *
     * @return A text block.
     */
    public static TextBlock createTextBlock(String text, Font font,
            Paint paint, float maxWidth, int maxLines, TextMeasurer measurer) {

        TextBlock result = new TextBlock();
        BreakIterator iterator = BreakIterator.getLineInstance();
        iterator.setText(text);
        int current = 0;
        int lines = 0;
        int length = text.length();
        while (current < length && lines < maxLines) {
            int next = nextLineBreak(text, current, maxWidth, iterator,
                    measurer);
            if (next == BreakIterator.DONE) {
                result.addLine(text.substring(current), font, paint);
                return result;
            } else if (next == current) {
                next++; // we must take one more character or we'll loop forever
            }
            result.addLine(text.substring(current, next), font, paint);
            lines++;
            current = next;
            while (current < text.length()&& text.charAt(current) == '\n') {
                current++;
            }
        }
        if (current < length) {
            TextLine lastLine = result.getLastLine();
            TextFragment lastFragment = lastLine.getLastTextFragment();
            String oldStr = lastFragment.getText();
            String newStr = "...";
            if (oldStr.length() > 3) {
                newStr = oldStr.substring(0, oldStr.length() - 3) + "...";
            }

            lastLine.removeFragment(lastFragment);
            TextFragment newFragment = new TextFragment(newStr,
                    lastFragment.getFont(), lastFragment.getPaint());
            lastLine.addFragment(newFragment);
        }
        return result;
    }

    /**
     * Returns the character index of the next line break.  If the next
     * character is wider than {@code width]} this method will return
     * {@code start} - the caller should check for this case.
     *
     * @param text  the text ({@code null} not permitted).
     * @param start  the start index.
     * @param width  the target display width.
     * @param iterator  the word break iterator.
     * @param measurer  the text measurer.
     *
     * @return The index of the next line break.
     */
    private static int nextLineBreak(String text, int start, float width, 
            BreakIterator iterator, TextMeasurer measurer) {

        // this method is (loosely) based on code in JFreeReport's
        // TextParagraph class
        int current = start;
        int end;
        float x = 0.0f;
        boolean firstWord = true;
        int newline = text.indexOf('\n', start);
        if (newline < 0) {
            newline = Integer.MAX_VALUE;
        }
        while (((end = iterator.following(current)) != BreakIterator.DONE)) {
            x += measurer.getStringWidth(text, current, end);
            if (x > width) {
                if (firstWord) {
                    while (measurer.getStringWidth(text, start, end) > width) {
                        end--;
                        if (end <= start) {
                            return end;
                        }
                    }
                    return end;
                }
                else {
                    end = iterator.previous();
                    return end;
                }
            }
            else {
                if (end > newline) {
                    return newline;
                }
            }
            // we found at least one word that fits ...
            firstWord = false;
            current = end;
        }
        return BreakIterator.DONE;
    }

    /**
     * Returns the bounds for the specified text.
     *
     * @param text  the text ({@code null} permitted).
     * @param g2  the graphics context (not {@code null}).
     * @param fm  the font metrics (not {@code null}).
     *
     * @return The text bounds ({@code null} if the {@code text}
     *         argument is {@code null}).
     */
    public static Rectangle2D getTextBounds(String text, Graphics2D g2, 
            FontMetrics fm) {

        Rectangle2D bounds;
        if (TextUtils.useFontMetricsGetStringBounds) {
            bounds = fm.getStringBounds(text, g2);
            // getStringBounds() can return incorrect height for some Unicode
            // characters...see bug parade 6183356, let's replace it with
            // something correct
            LineMetrics lm = fm.getFont().getLineMetrics(text,
                    g2.getFontRenderContext());
            bounds.setRect(bounds.getX(), bounds.getY(), bounds.getWidth(),
                    lm.getHeight());
        }
        else {
            double width = fm.stringWidth(text);
            double height = fm.getHeight();
            bounds = new Rectangle2D.Double(0.0, -fm.getAscent(), width,
                    height);
        }
        return bounds;
    }


    /**
     * Returns the bounds of an aligned string.
     * 
     * @param text  the string ({@code null} not permitted).
     * @param g2  the graphics target ({@code null} not permitted).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param anchor  the anchor point that will be aligned to 
     *     {@code (x, y)} ({@code null} not permitted).
     * 
     * @return The text bounds (never {@code null}).
     */
    public static Rectangle2D calcAlignedStringBounds(String text,
            Graphics2D g2, float x, float y, TextAnchor anchor) {

        Rectangle2D textBounds = new Rectangle2D.Double();
        float[] adjust = deriveTextBoundsAnchorOffsets(g2, text, anchor,
                textBounds);
        // adjust text bounds to match string position
        textBounds.setRect(x + adjust[0], y + adjust[1] + adjust[2],
            textBounds.getWidth(), textBounds.getHeight());
        return textBounds;
    }
    
    /**
     * Draws a string such that the specified anchor point is aligned to the
     * given (x, y) location.
     *
     * @param text  the text.
     * @param g2  the graphics device.
     * @param x  the x coordinate (Java 2D).
     * @param y  the y coordinate (Java 2D).
     * @param anchor  the anchor location.
     *
     * @return The text bounds (adjusted for the text position).
     */
    public static Rectangle2D drawAlignedString(String text, Graphics2D g2, 
            float x, float y, TextAnchor anchor) {

        Rectangle2D textBounds = new Rectangle2D.Double();
        float[] adjust = deriveTextBoundsAnchorOffsets(g2, text, anchor,
                textBounds);
        // adjust text bounds to match string position
        textBounds.setRect(x + adjust[0], y + adjust[1] + adjust[2],
            textBounds.getWidth(), textBounds.getHeight());
        if (!drawStringsWithFontAttributes) {
            g2.drawString(text, x + adjust[0], y + adjust[1]);
        } else {
            AttributedString as = new AttributedString(text, 
                    g2.getFont().getAttributes());
            g2.drawString(as.getIterator(), x + adjust[0], y + adjust[1]);
        }
        return textBounds;
    }

    /**
     * A utility method that calculates the anchor offsets for a string.
     * Normally, the (x, y) coordinate for drawing text is a point on the
     * baseline at the left of the text string.  If you add these offsets to
     * (x, y) and draw the string, then the anchor point should coincide with
     * the (x, y) point.
     *
     * @param g2  the graphics device (not {@code null}).
     * @param text  the text.
     * @param anchor  the anchor point.
     * @param textBounds  the text bounds (if not {@code null}, this
     *                    object will be updated by this method to match the
     *                    string bounds).
     *
     * @return  The offsets.
     */
    private static float[] deriveTextBoundsAnchorOffsets(Graphics2D g2,
            String text, TextAnchor anchor, Rectangle2D textBounds) {

        float[] result = new float[3];
        FontRenderContext frc = g2.getFontRenderContext();
        Font f = g2.getFont();
        FontMetrics fm = g2.getFontMetrics(f);
        Rectangle2D bounds = TextUtils.getTextBounds(text, g2, fm);
        LineMetrics metrics = f.getLineMetrics(text, frc);
        float ascent = metrics.getAscent();
        result[2] = -ascent;
        float halfAscent = ascent / 2.0f;
        float descent = metrics.getDescent();
        float leading = metrics.getLeading();
        float xAdj = 0.0f;
        float yAdj = 0.0f;

        if (anchor.isHorizontalCenter()) {
            xAdj = (float) -bounds.getWidth() / 2.0f;
        }
        else if (anchor.isRight()) {
            xAdj = (float) -bounds.getWidth();
        }

        if (anchor.isTop()) {
            yAdj = -descent - leading + (float) bounds.getHeight();
        }
        else if (anchor.isHalfAscent()) {
            yAdj = halfAscent;
        }
        else if (anchor.isVerticalCenter()) {
            yAdj = -descent - leading + (float) (bounds.getHeight() / 2.0);
        }
        else if (anchor.isBaseline()) {
            yAdj = 0.0f;
        }
        else if (anchor.isBottom()) {
            yAdj = -metrics.getDescent() - metrics.getLeading();
        }
        if (textBounds != null) {
            textBounds.setRect(bounds);
        }
        result[0] = xAdj;
        result[1] = yAdj;
        return result;

    }

    /**
     * A utility method for drawing rotated text.
     * <P>
     * A common rotation is -Math.PI/2 which draws text 'vertically' (with the
     * top of the characters on the left).
     *
     * @param text  the text.
     * @param g2  the graphics device.
     * @param angle  the angle of the (clockwise) rotation (in radians).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     */
    public static void drawRotatedString(String text, Graphics2D g2,
            double angle, float x, float y) {
        drawRotatedString(text, g2, x, y, angle, x, y);
    }

    /**
     * A utility method for drawing rotated text.
     * <P>
     * A common rotation is -Math.PI/2 which draws text 'vertically' (with the
     * top of the characters on the left).
     *
     * @param text  the text.
     * @param g2  the graphics device.
     * @param textX  the x-coordinate for the text (before rotation).
     * @param textY  the y-coordinate for the text (before rotation).
     * @param angle  the angle of the (clockwise) rotation (in radians).
     * @param rotateX  the point about which the text is rotated.
     * @param rotateY  the point about which the text is rotated.
     */
    public static void drawRotatedString(String text, Graphics2D g2,
            float textX, float textY, 
            double angle, float rotateX, float rotateY) {

        if ((text == null) || (text.isEmpty())) {
            return;
        }
        if (angle == 0.0) {
            drawAlignedString(text, g2, textX, textY, TextAnchor.BASELINE_LEFT);
            return;
        }
        
        AffineTransform saved = g2.getTransform();
        AffineTransform rotate = AffineTransform.getRotateInstance(
                angle, rotateX, rotateY);
        g2.transform(rotate);

        if (useDrawRotatedStringWorkaround) {
            // workaround for JDC bug ID 4312117 and others...
            TextLayout tl = new TextLayout(text, g2.getFont(),
                    g2.getFontRenderContext());
            tl.draw(g2, textX, textY);
        }
        else {
            if (!drawStringsWithFontAttributes) {
                g2.drawString(text, textX, textY);
            } else {
                AttributedString as = new AttributedString(text, 
                        g2.getFont().getAttributes());
                g2.drawString(as.getIterator(), textX, textY);
            }
        }
        g2.setTransform(saved);

    }

    /**
     * Draws a string that is aligned by one anchor point and rotated about
     * another anchor point.
     *
     * @param text  the text.
     * @param g2  the graphics device.
     * @param x  the x-coordinate for positioning the text.
     * @param y  the y-coordinate for positioning the text.
     * @param textAnchor  the text anchor.
     * @param angle  the rotation angle.
     * @param rotationX  the x-coordinate for the rotation anchor point.
     * @param rotationY  the y-coordinate for the rotation anchor point.
     */
    public static void drawRotatedString(String text, Graphics2D g2, 
            float x, float y, TextAnchor textAnchor, 
            double angle, float rotationX, float rotationY) {

        if (text == null || text.isEmpty()) {
            return;
        }
        if (angle == 0.0) {
            drawAlignedString(text, g2, x, y, textAnchor);
        } else {
            float[] textAdj = deriveTextBoundsAnchorOffsets(g2, text, 
                    textAnchor);
            drawRotatedString(text, g2, x + textAdj[0], y + textAdj[1], angle,
                    rotationX, rotationY);
        }
    }

    /**
     * Draws a string that is aligned by one anchor point and rotated about
     * another anchor point.
     *
     * @param text  the text.
     * @param g2  the graphics device.
     * @param x  the x-coordinate for positioning the text.
     * @param y  the y-coordinate for positioning the text.
     * @param textAnchor  the text anchor.
     * @param angle  the rotation angle (in radians).
     * @param rotationAnchor  the rotation anchor.
     */
    public static void drawRotatedString(String text, Graphics2D g2, 
            float x, float y, TextAnchor textAnchor, 
            double angle, TextAnchor rotationAnchor) {

        if (text == null || text.isEmpty()) {
            return;
        }
        if (angle == 0.0) {
            drawAlignedString(text, g2, x, y, textAnchor);
        } else {
            float[] textAdj = deriveTextBoundsAnchorOffsets(g2, text, 
                    textAnchor);
            float[] rotateAdj = deriveRotationAnchorOffsets(g2, text, 
                    rotationAnchor);
            drawRotatedString(text, g2, x + textAdj[0], y + textAdj[1],
                    angle, x + textAdj[0] + rotateAdj[0],
                    y + textAdj[1] + rotateAdj[1]);
        }
    }

    /**
     * Returns a shape that represents the bounds of the string after the
     * specified rotation has been applied.
     *
     * @param text  the text ({@code null} permitted).
     * @param g2  the graphics device.
     * @param x  the x coordinate for the anchor point.
     * @param y  the y coordinate for the anchor point.
     * @param textAnchor  the text anchor.
     * @param angle  the angle.
     * @param rotationAnchor  the rotation anchor.
     *
     * @return The bounds (possibly {@code null}).
     */
    public static Shape calculateRotatedStringBounds(String text, Graphics2D g2, 
            float x, float y, TextAnchor textAnchor, 
            double angle, TextAnchor rotationAnchor) {

        if (text == null || text.isEmpty()) {
            return null;
        }
        float[] textAdj = deriveTextBoundsAnchorOffsets(g2, text, textAnchor);
        float[] rotateAdj = deriveRotationAnchorOffsets(g2, text, 
                rotationAnchor);
        return calculateRotatedStringBounds(text, g2,
                x + textAdj[0], y + textAdj[1], angle,
                x + textAdj[0] + rotateAdj[0], y + textAdj[1] + rotateAdj[1]);
    }

    /**
     * A utility method that calculates the anchor offsets for a string.
     * Normally, the (x, y) coordinate for drawing text is a point on the
     * baseline at the left of the text string.  If you add these offsets to
     * (x, y) and draw the string, then the anchor point should coincide with
     * the (x, y) point.
     *
     * @param g2  the graphics device (not {@code null}).
     * @param text  the text.
     * @param anchor  the anchor point.
     *
     * @return  The offsets.
     */
    private static float[] deriveTextBoundsAnchorOffsets(Graphics2D g2,
            String text, TextAnchor anchor) {

        float[] result = new float[2];
        FontRenderContext frc = g2.getFontRenderContext();
        Font f = g2.getFont();
        FontMetrics fm = g2.getFontMetrics(f);
        Rectangle2D bounds = getTextBounds(text, g2, fm);
        LineMetrics metrics = f.getLineMetrics(text, frc);
        float ascent = metrics.getAscent();
        float halfAscent = ascent / 2.0f;
        float descent = metrics.getDescent();
        float leading = metrics.getLeading();
        float xAdj = 0.0f;
        float yAdj = 0.0f;

        if (anchor.isHorizontalCenter()) {
            xAdj = (float) -bounds.getWidth() / 2.0f;
        }
        else if (anchor.isRight()) {
            xAdj = (float) -bounds.getWidth();
        }

        if (anchor.isTop()) {
            yAdj = -descent - leading + (float) bounds.getHeight();
        }
        else if (anchor.isHalfAscent()) {
            yAdj = halfAscent;
        }
        else if (anchor.isVerticalCenter()) {
            yAdj = -descent - leading + (float) (bounds.getHeight() / 2.0);
        }
        else if (anchor.isBaseline()) {
            yAdj = 0.0f;
        }
        else if (anchor.isBottom()) {
            yAdj = -metrics.getDescent() - metrics.getLeading();
        }
        result[0] = xAdj;
        result[1] = yAdj;
        return result;

    }

    /**
     * A utility method that calculates the rotation anchor offsets for a
     * string.  These offsets are relative to the text starting coordinate
     * ({@code BASELINE_LEFT}).
     *
     * @param g2  the graphics device.
     * @param text  the text.
     * @param anchor  the anchor point.
     *
     * @return The offsets.
     */
    private static float[] deriveRotationAnchorOffsets(Graphics2D g2,
            String text, TextAnchor anchor) {

        float[] result = new float[2];
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics metrics = g2.getFont().getLineMetrics(text, frc);
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D bounds = TextUtils.getTextBounds(text, g2, fm);
        float ascent = metrics.getAscent();
        float halfAscent = ascent / 2.0f;
        float descent = metrics.getDescent();
        float leading = metrics.getLeading();
        float xAdj = 0.0f;
        float yAdj = 0.0f;

        if (anchor.isLeft()) {
            xAdj = 0.0f;
        }
        else if (anchor.isHorizontalCenter()) {
            xAdj = (float) bounds.getWidth() / 2.0f;
        }
        else if (anchor.isRight()) {
            xAdj = (float) bounds.getWidth();
        }

        if (anchor.isTop()) {
            yAdj = descent + leading - (float) bounds.getHeight();
        }
        else if (anchor.isVerticalCenter()) {
            yAdj = descent + leading - (float) (bounds.getHeight() / 2.0);
        }
        else if (anchor.isHalfAscent()) {
            yAdj = -halfAscent;
        }
        else if (anchor.isBaseline()) {
            yAdj = 0.0f;
        }
        else if (anchor.isBottom()) {
            yAdj = metrics.getDescent() + metrics.getLeading();
        }
        result[0] = xAdj;
        result[1] = yAdj;
        return result;

    }

    /**
     * Returns a shape that represents the bounds of the string after the
     * specified rotation has been applied.
     *
     * @param text  the text ({@code null} permitted).
     * @param g2  the graphics device.
     * @param textX  the x coordinate for the text.
     * @param textY  the y coordinate for the text.
     * @param angle  the angle.
     * @param rotateX  the x coordinate for the rotation point.
     * @param rotateY  the y coordinate for the rotation point.
     *
     * @return The bounds ({@code null} if {@code text} is
     *         {@code null} or has zero length).
     */
    public static Shape calculateRotatedStringBounds(String text, Graphics2D g2,
            float textX, float textY, double angle, float rotateX, 
            float rotateY) {

        if ((text == null) || (text.isEmpty())) {
            return null;
        }
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D bounds = TextUtils.getTextBounds(text, g2, fm);
        AffineTransform translate = AffineTransform.getTranslateInstance(
                textX, textY);
        Shape translatedBounds = translate.createTransformedShape(bounds);
        AffineTransform rotate = AffineTransform.getRotateInstance(
                angle, rotateX, rotateY);
        return rotate.createTransformedShape(translatedBounds);
    }

    /**
     * Returns the flag that controls whether the FontMetrics.getStringBounds()
     * method is used or not.  If you are having trouble with label alignment
     * or positioning, try changing the value of this flag.
     *
     * @return A boolean.
     */
    public static boolean getUseFontMetricsGetStringBounds() {
        return useFontMetricsGetStringBounds;
    }

    /**
     * Sets the flag that controls whether the FontMetrics.getStringBounds()
     * method is used or not.  If you are having trouble with label alignment
     * or positioning, try changing the value of this flag.
     *
     * @param use  the flag.
     */
    public static void setUseFontMetricsGetStringBounds(boolean use) {
        useFontMetricsGetStringBounds = use;
    }

    /**
     * Returns the flag that controls whether a workaround is used for
     * drawing rotated strings.
     *
     * @return A boolean.
     */
    public static boolean isUseDrawRotatedStringWorkaround() {
        return useDrawRotatedStringWorkaround;
    }
 
    /**
     * Sets the flag that controls whether a workaround is used for
     * drawing rotated strings.  The related bug is on Sun's bug parade
     * (id 4312117) and the workaround involves using a {@code TextLayout}
     * instance to draw the text instead of calling the
     * {@code drawString()} method in the {@code Graphics2D} class.
     *
     * @param use  the new flag value.
     */
    public static void setUseDrawRotatedStringWorkaround(boolean use) {
        TextUtils.useDrawRotatedStringWorkaround = use;
    }
    
    /**
     * Returns the flag that controls whether strings are drawn using
     * the current font attributes (such as underlining, strikethrough etc.).
     * The default value is {@code false}.
     * 
     * @return A boolean.
     */
    public static boolean getDrawStringsWithFontAttributes() {
        return TextUtils.drawStringsWithFontAttributes;
    }
    
    /**
     * Sets the flag that controls whether strings are drawn using the
     * current font attributes.  This is a hack to allow underlining of titles
     * without big changes to the API.  See:
     * <a href="https://www.jfree.org/phpBB2/viewtopic.php?p=45459&amp;highlight=#45459">this forum post</a>.
     *
     * @param b  the new flag value.
     */
    public static void setDrawStringsWithFontAttributes(boolean b) {
        TextUtils.drawStringsWithFontAttributes = b;
    }

}

