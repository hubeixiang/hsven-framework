package org.framework.hsven.datasource.pool.c3p0;

import com.mchange.v2.c3p0.ConnectionCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * @date 2019/12/19 11:29
 */
public class C3p0ConnectionCustomizer implements ConnectionCustomizer {
    public static final String DB_NAMEPREFIX_INFORMIX = "Informix";
    public static final String DB_NAMEPREFIX_SYBASE = "ASE";
    private static final Logger logger = LoggerFactory.getLogger("NOMALENV");

    @Override
    public void onAcquire(Connection con, String arg1) throws Exception {
        try {
            String databaseTypeName = con.getMetaData().getDatabaseProductName();
            logger.info("MyConnection databaseTypeName=" + databaseTypeName + ",arg1=" + arg1 + ",TransactionIsolation=" + con
                    .getTransactionIsolation());
            if (databaseTypeName.contains("\\/")) {
                databaseTypeName = databaseTypeName.substring(0, databaseTypeName.indexOf("/"));
            }
            if (databaseTypeName.startsWith(DB_NAMEPREFIX_INFORMIX)) {
                if (con.getTransactionIsolation() != Connection.TRANSACTION_READ_UNCOMMITTED) {
                    con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                    logger.info("set dirty read for:" + arg1 + ",TransactionIsolation=" + con.getTransactionIsolation());
                }
            } else if (databaseTypeName.equalsIgnoreCase(DB_NAMEPREFIX_SYBASE)) {
                if (con.getTransactionIsolation() != Connection.TRANSACTION_READ_UNCOMMITTED) {
                    con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                    logger.info("set dirty read for:" + arg1 + ",TransactionIsolation=" + con.getTransactionIsolation());
                }
            }
        } catch (Exception e) {
            logger.error("set dirty read error:", e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.mchange.v2.c3p0.ConnectionCustomizer#onCheckIn(java.sql.Connection,
     * java.lang.String)
     */
    @Override
    public void onCheckIn(Connection arg0, String arg1) throws Exception {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.mchange.v2.c3p0.ConnectionCustomizer#onCheckOut(java.sql.Connection,
     * java.lang.String)
     */
    @Override
    public void onCheckOut(Connection arg0, String arg1) throws Exception {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.mchange.v2.c3p0.ConnectionCustomizer#onDestroy(java.sql.Connection,
     * java.lang.String)
     */
    @Override
    public void onDestroy(Connection arg0, String arg1) throws Exception {
        // TODO Auto-generated method stub

    }

}
