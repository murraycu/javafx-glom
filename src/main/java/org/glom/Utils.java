/*
 * Copyright (C) 2011 Openismus GmbH
 *
 * This file is part of GWT-Glom.
 *
 * GWT-Glom is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * GWT-Glom is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GWT-Glom.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.glom;

import org.apache.commons.lang3.StringUtils;
import org.glom.libglom.Field.GlomFieldType;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class Utils {

    /*
     * This method safely converts longs from libglom into ints. This method was taken from stackoverflow:
     *
     * http://stackoverflow.com/questions/1590831/safely-casting-long-to-int-in-java
     */
    public static int safeLongToInt(final long value) {
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(value + " cannot be cast to int without changing its value.");
        }
        return (int) value;
    }

    /*
    public static String getFileName(final String fileURI) {
        final String[] splitURI = fileURI.split(File.separator);
        return splitURI[splitURI.length - 1];
    }
    */

    /**
     * Build a :-separated string to represent the path as a string.
     *
     * @param path
     * @return
     */
    public static String buildLayoutPath(int[] path) {
        if ((path == null) || (path.length == 0)) {
            return null;
        }

        String result = new String();
        for (int i : path) {
            if (!result.isEmpty()) {
                result += ":";
            }

            final String strIndex = Integer.toString(i);
            result += strIndex;
        }

        return result;
    }

    /**
     * Get an array of int indices from the :-separated string.
     * See buildLayoutPath().
     *
     * @param attrLayoutPath
     * @return The array of indices of the layout items.
     */
    public static int[] parseLayoutPath(final String attrLayoutPath) {
        if (StringUtils.isEmpty(attrLayoutPath)) {
            return null;
        }

        final String[] strIndices = attrLayoutPath.split(":");
        final int[] indices = new int[strIndices.length];
        for (int i = 0; i < strIndices.length; ++i) {
            final String str = strIndices[i];

            try {
                indices[i] = Integer.parseInt(str);
            } catch (final NumberFormatException nfe) {
                //TODO: Log the error.
                return null;
            }
        }

        return indices;
    }

    public static void transformUnknownToActualType(final TypedDataItem dataItem, final GlomFieldType actualType) {
        if (dataItem.getType() == actualType)
            return;

        String unknownText = dataItem.getUnknown();

        //Avoid repeated checks for null:
        if (unknownText == null) {
            unknownText = "";
        }

        switch (actualType) {
            case TYPE_NUMERIC:
                // TODO: Is this really locale-independent?
                double number = 0;
                if (!StringUtils.isEmpty(unknownText)) {
                    try {
                        number = Double.parseDouble(unknownText);
                    } catch (final NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                dataItem.setNumber(number);
                break;
            case TYPE_TEXT:
                dataItem.setText(unknownText);
                break;
            case TYPE_DATE:
                final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = formatter.parse(unknownText);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                dataItem.setDate(date);
                break;
            case TYPE_TIME:
            /*TODO :
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date date;
			try {
				date = formatter.parse(unknownText);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			setDate(date); 
			*/
                break;
            case TYPE_BOOLEAN:
                final boolean bool = unknownText.equals("true");
                dataItem.setBoolean(bool); //TODO
                break;
            case TYPE_IMAGE:
                dataItem.setImageDataUrl(unknownText);
                //setImageData(null);//TODO: Though this is only used for primary keys anyway.
                break;
            case TYPE_INVALID:
                break;
            default:
                break; //TODO: Warn because this is unusual?
        }
    }

}
