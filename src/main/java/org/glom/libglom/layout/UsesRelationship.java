/*
 * Copyright (C) 2012 Openismus GmbH
 *
 * This file is part of android-glom.
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

package org.glom.libglom.layout;

import org.glom.libglom.Relationship;

/**
 * @author Murray Cumming <murrayc@openismus.com>
 */
public interface UsesRelationship {

    Relationship getRelationship();

    void setRelationship(final Relationship relationship);

    /**
     * @return
     */
    boolean getHasRelationshipName();

    Relationship getRelatedRelationship();

    /**
     * @param relationship
     */
    void setRelatedRelationship(final Relationship relationship);

    boolean getHasRelatedRelationshipName();

    String getSqlJoinAliasName();

    String getSqlTableOrJoinAliasName(String tableName);

    /**
     * @param string
     * @return
     */
    String getTableUsed(String string);

    String getRelationshipNameUsed();

    String getTitleUsed(String parentTableTitle, String locale);
}