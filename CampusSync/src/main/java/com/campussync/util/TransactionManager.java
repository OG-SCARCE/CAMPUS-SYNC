package com.campussync.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * TransactionManager
 * 
 * Centralized Transaction Management for JDBC operations involving multiple tables.
 * Ensures data integrity through automatic commit/rollback and proper resource cleanup.
 * Includes comprehensive error logging for debugging transaction issues.
 * 
 * Usage Pattern:
 * TransactionManager txn = new TransactionManager();
 * try {
 *     Connection conn = txn.beginTransaction();
 *     // Perform multiple database operations
 *     pdao.insertParent(conn, data);
 *     cdao.insertChild(conn, data);
 *     txn.commit();
 * } catch (SQLException e) {
 *     txn.rollback();
 *     throw new RuntimeException("Transaction failed", e);
 * } finally {
 *     txn.closeConnection();
 * }
 */
public class TransactionManager {
    private Connection connection;
    private boolean isTransactionActive = false;

    /**
     * Begins a new transaction by opening a connection with autocommit disabled.
     * 
     * @return Connection object to be used for subsequent database operations
     * @throws SQLException if connection cannot be established
     */
    public Connection beginTransaction() throws SQLException {
        if (isTransactionActive) {
            Logger.warn("Attempted to begin transaction when one is already active");
            throw new IllegalStateException("Transaction already active. Call closeConnection() first.");
        }
        
        try {
            this.connection = DBConnection.getConnection();
            this.connection.setAutoCommit(false);
            this.isTransactionActive = true;
            Logger.debug("Transaction started");
            return this.connection;
        } catch (SQLException e) {
            Logger.error("Failed to begin transaction", e);
            throw e;
        }
    }

    /**
     * Commits the current transaction, making all changes permanent.
     * 
     * @throws SQLException if commit fails
     */
    public void commit() throws SQLException {
        if (!isTransactionActive) {
            Logger.warn("Attempted to commit when no transaction is active");
            throw new IllegalStateException("No active transaction to commit.");
        }
        
        try {
            this.connection.commit();
            Logger.debug("Transaction committed successfully");
        } catch (SQLException e) {
            Logger.error("Commit failed, rolling back transaction", e);
            this.connection.rollback();
            throw e;
        }
    }

    /**
     * Rolls back the current transaction, undoing all changes.
     * Called automatically if an error occurs during transaction processing.
     * 
     * @throws SQLException if rollback fails
     */
    public void rollback() throws SQLException {
        if (isTransactionActive && this.connection != null) {
            try {
                this.connection.rollback();
                Logger.debug("Transaction rolled back successfully");
            } catch (SQLException e) {
                Logger.error("Rollback failed", e);
                throw e;
            }
        }
    }

    /**
     * Closes the connection and resets transaction state.
     * Should always be called in a finally block to ensure cleanup.
     */
    public void closeConnection() {
        if (this.connection != null) {
            try {
                if (!this.connection.isClosed()) {
                    this.connection.close();
                    Logger.debug("Connection closed successfully");
                }
            } catch (SQLException e) {
                Logger.error("Failed to close connection", e);
            } finally {
                this.isTransactionActive = false;
                this.connection = null;
            }
        }
    }

    /**
     * Gets the current active connection for use in DAO methods.
     * 
     * @return Current connection object
     * @throws IllegalStateException if no transaction is active
     */
    public Connection getConnection() {
        if (!isTransactionActive || this.connection == null) {
            Logger.warn("Attempted to get connection when no transaction is active");
            throw new IllegalStateException("No active transaction. Call beginTransaction() first.");
        }
        return this.connection;
    }
}
