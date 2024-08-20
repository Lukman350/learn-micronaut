package example.micronaut.util;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;

@Serdeable
public class SortingAndOrderArguments {

    @Nullable
    @PositiveOrZero
    private Integer offset;

    @Nullable
    @Positive
    private Integer max;

    @Nullable
    @Pattern(regexp = "id|name")
    private String sort;

    @Nullable
    @Pattern(regexp = "asc|ASC|desc|DESC")
    private String order;

    public SortingAndOrderArguments(@Nullable Integer offset, @Nullable Integer max, @Nullable String sort, @Nullable String order) {
        this.offset = offset;
        this.max = max;
        this.sort = sort;
        this.order = order;
    }

    public Optional<Integer> getOffset() {
        return Optional.ofNullable(offset);
    }

    public void setOffset(@Nullable Integer offset) {
        this.offset = offset;
    }

    public Optional<Integer> getMax() {
        return Optional.ofNullable(max);
    }

    public void setMax(@Nullable Integer max) {
        this.max = max;
    }

    public Optional<String> getSort() {
        return Optional.ofNullable(sort);
    }

    public void setSort(@Nullable String sort) {
        this.sort = sort;
    }

    public Optional<String> getOrder() {
        return Optional.ofNullable(order);
    }

    public void setOrder(@Nullable String order) {
        this.order = order;
    }
}