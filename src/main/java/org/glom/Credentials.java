/*
 * Copyright (C) 2015 Murray Cumming
 *
 * This file is part of swing-glom
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
import java.sql.SQLException;

import org.glom.libglom.Document;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.glom.libglom.Logger;

/**
 * @author Murray Cumming <murrayc@murrayc.com>
 *
 */
public class Credentials {
	public final Document document;
	public final String username;
	public final String password;
	private ComboPooledDataSource cpds;

	public Credentials(final Document document, final String username, final String password, final ComboPooledDataSource cpds) {
		this.document = document;
		this.username = username;
		this.password = password;
		
		setConnection(cpds);
	}
	
	private void setConnection(final ComboPooledDataSource cpds) {
		this.cpds = cpds;
	}

	/**
	 * @return
	 */
	public ComboPooledDataSource getConnection() {
		if(cpds != null) {
			return cpds;
		}
		
		// Try to recreate the connection,
		// which might have been invalidated after some time:
		ComboPooledDataSource authenticatedConnection = null;
		try
		{
			authenticatedConnection = SqlUtils.tryUsernameAndPassword(document, username, password);
		} catch (final SQLException e) {
			Logger.log("Unknown SQL Error checking the database authentication.", e);
			return null;
		}
		
		if(authenticatedConnection == null) {
			return null;
		}
		
		//Remember it for a while:
		setConnection(authenticatedConnection);
		return authenticatedConnection;
	}
	
}
