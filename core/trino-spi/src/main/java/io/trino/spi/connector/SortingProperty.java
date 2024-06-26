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
package io.trino.spi.connector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public final class SortingProperty<E>
        implements LocalProperty<E>
{
    private final E column;
    private final SortOrder order;

    @JsonCreator
    public SortingProperty(
            @JsonProperty("column") E column,
            @JsonProperty("order") SortOrder order)
    {
        requireNonNull(column, "column is null");
        requireNonNull(order, "order is null");

        this.column = column;
        this.order = order;
    }

    @Override
    public boolean isOrderSensitive()
    {
        return true;
    }

    @JsonProperty
    public E getColumn()
    {
        return column;
    }

    @Override
    public Set<E> getColumns()
    {
        return Collections.singleton(column);
    }

    @JsonProperty
    public SortOrder getOrder()
    {
        return order;
    }

    /**
     * Returns Optional.empty() if the column could not be translated
     */
    @Override
    public <T> Optional<LocalProperty<T>> translate(Function<E, Optional<T>> translator)
    {
        return translator.apply(column)
                .map(translated -> new SortingProperty<>(translated, order));
    }

    @Override
    public boolean isSimplifiedBy(LocalProperty<E> known)
    {
        return known instanceof ConstantProperty || known.equals(this);
    }

    @Override
    public String toString()
    {
        String ordering = "";
        String nullOrdering = switch (order) {
            case ASC_NULLS_FIRST -> {
                ordering = "\u2191";
                yield "\u2190";
            }
            case ASC_NULLS_LAST -> {
                ordering = "\u2191";
                yield "\u2192";
            }
            case DESC_NULLS_FIRST -> {
                ordering = "\u2193";
                yield "\u2190";
            }
            case DESC_NULLS_LAST -> {
                ordering = "\u2193";
                yield "\u2192";
            }
        };

        return "S" + ordering + nullOrdering + "(" + column + ")";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SortingProperty<?> that = (SortingProperty<?>) o;
        return Objects.equals(column, that.column) &&
                order == that.order;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(column, order);
    }
}
