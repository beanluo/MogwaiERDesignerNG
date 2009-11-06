/**
 * Mogwai ERDesigner. Copyright (C) 2002 The Mogwai Project.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package de.erdesignerng.dialect.msaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import de.erdesignerng.dialect.DataType;
import de.erdesignerng.dialect.JDBCReverseEngineeringStrategy;
import de.erdesignerng.dialect.NameCastType;
import de.erdesignerng.dialect.SQLGenerator;
import de.erdesignerng.dialect.sql92.SQL92Dialect;

/**
 *
 * @author p000010
 */
public class MSAccessDialect extends SQL92Dialect {

    @Override
    public Connection createConnection(ClassLoader aClassLoader, String aDriver, String aUrl, String aUser, String aPassword, boolean aPromptForPassword) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
//        path = "C:\\System.mdw";
//        File workgroupFile = new File("de/erdesignerng/System.mdw");
//;SystemDB=" + workgroupFile.getAbsoluteFile()
        String database = "jdbc:odbc:Driver={" + aDriver + "};DBQ=" + aUrl + ";ExtendedAnsiSQL=1" + ";";
        return DriverManager.getConnection(database, aUser, aPassword);
    }

    public MSAccessDialect() {
        setSpacesAllowedInObjectNames(true);
        setCaseSensitive(false);
        setMaxObjectNameLength(255);
        setNullablePrimaryKeyAllowed(false);
        setCastType(NameCastType.NOTHING);
        setSupportsDomains(false);
        setSupportsSchemaInformation(false);

        registerType(createDataType("integer", "", Types.INTEGER));       //LONG
        registerType(createDataType("varchar", "$size", Types.VARCHAR));  //TEXT
        registerType(createDataType("counter", "", true, Types.INTEGER)); //AUTOINCREMENT
        registerType(createDataType("datetime", "", Types.DATE));         //DATE
        registerType(createDataType("byte", "", Types.TINYINT));          //BYTE
        registerType(createDataType("bit", "", Types.BOOLEAN));           //YESNO
        registerType(createDataType("longchar", "", Types.LONGNVARCHAR)); //MEMO

        seal();
}

    @Override
    public JDBCReverseEngineeringStrategy getReverseEngineeringStrategy() {
        return new MSAccessReverseEngineeringStrategy(this);
    }

    @Override
    public String getUniqueName() {
        return "MSAccessDialect";
    }

    @Override
    public String getDefaultUserName() {
        return "Admin";
    }

    @Override
    public String getDriverClassName() {
        return "Microsoft Access Driver (*.mdb)";
    }

    @Override
    public String getDriverURLTemplate() {
        return "C:\\<db>.mdb";
    }

    @Override
    public SQLGenerator createSQLGenerator() {
        return new MSAccessSQLGenerator(this);
    }

    @Override
    public Class getHibernateDialectClass() {
        throw new UnsupportedOperationException("MSAccessDialect.getHibernateDialectClass() is not supported yet.");
    }

    @Override
    public DataType createDataType(String aName, String aDefinition, int... aJdbcType) {
        return new MSAccessDataType(aName, aDefinition, aJdbcType);
    }

    @Override
    public DataType createDataType(String aName, String aDefinition, boolean aIdentity, int... aJdbcType) {
        return new MSAccessDataType(aName, aDefinition, aIdentity, aJdbcType);
    }
}