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
package io.trino.security;

import com.google.common.collect.ImmutableSet;
import io.trino.spi.TrinoException;
import io.trino.spi.connector.CatalogSchemaName;
import io.trino.spi.connector.CatalogSchemaTableName;
import io.trino.spi.connector.CatalogSchemaTablePrefix;
import io.trino.spi.security.GrantInfo;
import io.trino.spi.security.Identity;
import io.trino.spi.security.Privilege;
import io.trino.spi.security.RoleGrant;
import io.trino.spi.security.SystemSecurityContext;
import io.trino.spi.security.SystemSecurityMetadata;
import io.trino.spi.security.SystemSecurityMetadataFactory;
import io.trino.spi.security.TrinoPrincipal;

import java.util.Optional;
import java.util.Set;

import static io.trino.spi.StandardErrorCode.NOT_SUPPORTED;

public class DisabledSystemSecurityMetadata
        implements SystemSecurityMetadata
{
    @Override
    public boolean roleExists(SystemSecurityContext context, String role)
    {
        return false;
    }

    @Override
    public void createRole(SystemSecurityContext context, String role, Optional<TrinoPrincipal> grantor)
    {
        throw new TrinoException(NOT_SUPPORTED, "System roles are not enabled");
    }

    @Override
    public void dropRole(SystemSecurityContext context, String role)
    {
        throw new TrinoException(NOT_SUPPORTED, "System roles are not enabled");
    }

    @Override
    public Set<String> listRoles(SystemSecurityContext context)
    {
        return ImmutableSet.of();
    }

    @Override
    public Set<RoleGrant> listRoleGrants(SystemSecurityContext context, TrinoPrincipal principal)
    {
        return ImmutableSet.of();
    }

    @Override
    public void grantRoles(SystemSecurityContext context, Set<String> roles, Set<TrinoPrincipal> grantees, boolean adminOption, Optional<TrinoPrincipal> grantor)
    {
        throw new TrinoException(NOT_SUPPORTED, "System roles are not enabled");
    }

    @Override
    public void revokeRoles(SystemSecurityContext context, Set<String> roles, Set<TrinoPrincipal> grantees, boolean adminOption, Optional<TrinoPrincipal> grantor)
    {
        throw new TrinoException(NOT_SUPPORTED, "System roles are not enabled");
    }

    @Override
    public Set<RoleGrant> listApplicableRoles(SystemSecurityContext context, TrinoPrincipal principal)
    {
        return ImmutableSet.of();
    }

    @Override
    public Set<String> listEnabledRoles(Identity identity)
    {
        return ImmutableSet.of();
    }

    @Override
    public void grantSchemaPrivileges(
            SystemSecurityContext context,
            CatalogSchemaName schemaName,
            Set<Privilege> privileges,
            TrinoPrincipal grantee, boolean grantOption)
    {
        throw notSupportedException(schemaName.getCatalogName());
    }

    @Override
    public void denySchemaPrivileges(SystemSecurityContext context, CatalogSchemaName schemaName, Set<Privilege> privileges, TrinoPrincipal grantee)
    {
        throw notSupportedException(schemaName.getCatalogName());
    }

    @Override
    public void revokeSchemaPrivileges(
            SystemSecurityContext context,
            CatalogSchemaName schemaName,
            Set<Privilege> privileges,
            TrinoPrincipal grantee, boolean grantOption)
    {
        throw notSupportedException(schemaName.getCatalogName());
    }

    @Override
    public void grantTablePrivileges(SystemSecurityContext context, CatalogSchemaTableName tableName, Set<Privilege> privileges, TrinoPrincipal grantee, boolean grantOption)
    {
        throw notSupportedException(tableName.getCatalogName());
    }

    @Override
    public void denyTablePrivileges(SystemSecurityContext context, CatalogSchemaTableName tableName, Set<Privilege> privileges, TrinoPrincipal grantee)
    {
        throw notSupportedException(tableName.getCatalogName());
    }

    @Override
    public void revokeTablePrivileges(SystemSecurityContext context, CatalogSchemaTableName tableName, Set<Privilege> privileges, TrinoPrincipal grantee, boolean grantOption)
    {
        throw notSupportedException(tableName.getCatalogName());
    }

    @Override
    public Set<GrantInfo> listTablePrivileges(SystemSecurityContext context, CatalogSchemaTablePrefix prefix)
    {
        return ImmutableSet.of();
    }

    @Override
    public Optional<TrinoPrincipal> getSchemaOwner(SystemSecurityContext context, CatalogSchemaName schema)
    {
        return Optional.empty();
    }

    @Override
    public void setSchemaOwner(SystemSecurityContext context, CatalogSchemaName schema, TrinoPrincipal principal)
    {
        throw notSupportedException(schema.getCatalogName());
    }

    @Override
    public void setTableOwner(SystemSecurityContext context, CatalogSchemaTableName table, TrinoPrincipal principal)
    {
        throw notSupportedException(table.getCatalogName());
    }

    @Override
    public Optional<Identity> getViewRunAsIdentity(SystemSecurityContext context, CatalogSchemaTableName view)
    {
        return Optional.empty();
    }

    @Override
    public void setViewOwner(SystemSecurityContext context, CatalogSchemaTableName view, TrinoPrincipal principal)
    {
        throw notSupportedException(view.getCatalogName());
    }

    @Override
    public void schemaCreated(SystemSecurityContext context, CatalogSchemaName schema) {}

    @Override
    public void schemaRenamed(SystemSecurityContext context, CatalogSchemaName sourceSchema, CatalogSchemaName targetSchema) {}

    @Override
    public void schemaDropped(SystemSecurityContext context, CatalogSchemaName schema) {}

    @Override
    public void tableCreated(SystemSecurityContext context, CatalogSchemaTableName table) {}

    @Override
    public void tableRenamed(SystemSecurityContext context, CatalogSchemaTableName sourceTable, CatalogSchemaTableName targetTable) {}

    @Override
    public void tableDropped(SystemSecurityContext context, CatalogSchemaTableName table) {}

    private static TrinoException notSupportedException(String catalogName)
    {
        return new TrinoException(NOT_SUPPORTED, "Catalog does not support permission management: " + catalogName);
    }

    public static class Factory
            implements SystemSecurityMetadataFactory
    {
        @Override
        public String getName()
        {
            return "disabled";
        }

        @Override
        public SystemSecurityMetadata create()
        {
            return new DisabledSystemSecurityMetadata();
        }
    }
}
