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
package io.trino.debug;

import io.trino.security.AccessControl;
import io.trino.server.ProtocolConfig;
import io.trino.spi.connector.ConnectorMetadata;
import io.trino.spi.connector.ConnectorSession;
import io.trino.spi.connector.SchemaTablePrefix;

import java.util.Optional;

public class DebugA
{
    private DebugA() {}

    public static void foo1(ConnectorMetadata metadata,
            ConnectorSession session,
            SchemaTablePrefix prefix)
    {
        metadata.listTableColumns(session, prefix);
    }

    public static void foo2(ProtocolConfig config)
    {
        config.getAlternateHeaderName();
    }

    public static void foo3(AccessControl accessControl)
    {
        accessControl.checkCanSetUser(Optional.empty(), "");
    }
}
