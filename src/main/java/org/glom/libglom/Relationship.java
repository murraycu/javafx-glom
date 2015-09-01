/*
 * Copyright (C) 2012 Openismus GmbH
 *
 * This file is part of android-glom
 *
 * android-glom is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * android-glom is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with android-glom.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.glom.libglom;

import org.apache.commons.lang3.StringUtils;

public class Relationship extends Translatable {
    private String fromTable = "";
    private String fromField = "";
    private String toTable = "";
    private String toField = "";

    /**
     * @return
     */
    public boolean getHasToTable() {
        return !StringUtils.isEmpty(toTable);
    }

    /**
     * @return
     */
    public String getToField() {
        return toField;
    }

    /**
     * @param
     */
    public void setToField(final String name) {
        toField = name;
    }

    /**
     * @return
     */
    public boolean getHasFields() {
        return !StringUtils.isEmpty(toField) && !StringUtils.isEmpty(toTable) && !StringUtils.isEmpty(fromField)
                && !StringUtils.isEmpty(fromTable);
    }

    /**
     * @return
     */
    public String getToTable() {
        return toTable;
    }

    /**
     * @param
     */
    public void setToTable(final String name) {
        toTable = name;
    }

    /**
     * @return
     */
    public String getFromTable() {
        return fromTable;
    }

    /**
     * @param
     */
    public void setFromTable(final String name) {
        fromTable = name;
    }

    /**
     * @return
     */
    public String getFromField() {
        return fromField;
    }

    /**
     * @param
     */
    public void setFromField(final String name) {
        fromField = name;
    }

    public boolean equals(final Relationship b) {
        if (b == null) {
            return false;
        }

        if (!StringUtils.equals(this.getName(), b.getName())) {
            return false;
        }

        if (!StringUtils.equals(this.fromTable, b.fromTable)) {
            return false;
        }

        if (!StringUtils.equals(this.fromField, b.fromField)) {
            return false;
        }

        if (!StringUtils.equals(this.toTable, b.toTable)) {
            return false;
        }

        return StringUtils.equals(this.toField, b.toField);

    }

}
