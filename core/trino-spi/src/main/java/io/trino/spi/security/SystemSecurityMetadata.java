/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.spi.security;

import io.trino.spi.connector.CatalogSchemaName;
import io.trino.spi.connector.CatalogSchemaTableName;
import io.trino.spi.connector.CatalogSchemaTablePrefix;

import java.util.Optional;
import java.util.Set;

public interface SystemSecurityMetadata
{
    /**
     * Does the specified role exist.
     */
    boolean roleExists(SystemSecurityContext context, String role);

    /**
     * Creates the specified role.
     *
     * @param grantor represents the principal specified by WITH ADMIN statement
     */
    void createRole(SystemSecurityContext context, String role, Optional<TrinoPrincipal> grantor);

    /**
     * Drops the specified role.
     */
    void dropRole(SystemSecurityContext context, String role);

    /**
     * List available roles.
     */
    Set<String> listRoles(SystemSecurityContext context);

    /**
     * List roles grants for a given principal, not recursively.
     */
    Set<RoleGrant> listRoleGrants(SystemSecurityContext context, TrinoPrincipal principal);

    /**
     * Grants the specified roles to the specified grantees.
     *
     * @param grantor represents the principal specified by GRANTED BY statement
     */
    void grantRoles(SystemSecurityContext context, Set<String> roles, Set<TrinoPrincipal> grantees, boolean adminOption, Optional<TrinoPrincipal> grantor);

    /**
     * Revokes the specified roles from the specified grantees.
     *
     * @param grantor represents the principal specified by GRANTED BY statement
     */
    void revokeRoles(SystemSecurityContext context, Set<String> roles, Set<TrinoPrincipal> grantees, boolean adminOption, Optional<TrinoPrincipal> grantor);

    /**
     * List applicable roles, including the transitive grants, for the specified principal
     */
    Set<RoleGrant> listApplicableRoles(SystemSecurityContext context, TrinoPrincipal principal);

    /**
     * List applicable roles, including the transitive grants, in given identity
     */
    Set<String> listEnabledRoles(Identity identity);

    /**
     * Grants the specified privilege to the specified user on the specified schema.
     */
    void grantSchemaPrivileges(SystemSecurityContext context, CatalogSchemaName schemaName, Set<Privilege> privileges, TrinoPrincipal grantee, boolean grantOption);

    /**
     * Denys the specified privilege to the specified user on the specified schema.
     */
    void denySchemaPrivileges(SystemSecurityContext context, CatalogSchemaName schemaName, Set<Privilege> privileges, TrinoPrincipal grantee);

    /**
     * Revokes the specified privilege on the specified schema from the specified user.
     */
    void revokeSchemaPrivileges(SystemSecurityContext context, CatalogSchemaName schemaName, Set<Privilege> privileges, TrinoPrincipal grantee, boolean grantOption);

    /**
     * Grants the specified privilege to the specified user on the specified table
     */
    void grantTablePrivileges(SystemSecurityContext context, CatalogSchemaTableName tableName, Set<Privilege> privileges, TrinoPrincipal grantee, boolean grantOption);

    /**
     * Denys the specified privilege to the specified user on the specified table
     */
    void denyTablePrivileges(SystemSecurityContext context, CatalogSchemaTableName tableName, Set<Privilege> privileges, TrinoPrincipal grantee);

    /**
     * Revokes the specified privilege on the specified table from the specified user
     */
    void revokeTablePrivileges(SystemSecurityContext context, CatalogSchemaTableName tableName, Set<Privilege> privileges, TrinoPrincipal grantee, boolean grantOption);

    /**
     * Gets the privileges for the specified table available to the given grantee considering the selected context role
     */
    Set<GrantInfo> listTablePrivileges(SystemSecurityContext context, CatalogSchemaTablePrefix prefix);

    /**
     * Set the owner of the specified schema
     */
    Optional<TrinoPrincipal> getSchemaOwner(SystemSecurityContext context, CatalogSchemaName schema);

    /**
     * Set the owner of the specified schema
     */
    void setSchemaOwner(SystemSecurityContext context, CatalogSchemaName schema, TrinoPrincipal principal);

    /**
     * Set the owner of the specified table
     */
    void setTableOwner(SystemSecurityContext context, CatalogSchemaTableName table, TrinoPrincipal principal);

    /**
     * Get the identity to run the view as
     */
    Optional<Identity> getViewRunAsIdentity(SystemSecurityContext context, CatalogSchemaTableName viewName);

    /**
     * Set the owner of the specified view
     */
    void setViewOwner(SystemSecurityContext context, CatalogSchemaTableName view, TrinoPrincipal principal);

    /**
     * A schema was created
     */
    void schemaCreated(SystemSecurityContext context, CatalogSchemaName schema);

    /**
     * A schema was renamed
     */
    void schemaRenamed(SystemSecurityContext context, CatalogSchemaName sourceSchema, CatalogSchemaName targetSchema);

    /**
     * A schema was dropped
     */
    void schemaDropped(SystemSecurityContext context, CatalogSchemaName schema);

    /**
     * A table or view was created
     */
    void tableCreated(SystemSecurityContext context, CatalogSchemaTableName table);

    /**
     * A table or view was renamed
     */
    void tableRenamed(SystemSecurityContext context, CatalogSchemaTableName sourceTable, CatalogSchemaTableName targetTable);

    /**
     * A table or view was dropped
     */
    void tableDropped(SystemSecurityContext context, CatalogSchemaTableName table);
}
