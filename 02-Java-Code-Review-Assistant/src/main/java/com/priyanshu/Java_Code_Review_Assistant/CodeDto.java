package com.priyanshu.Java_Code_Review_Assistant;

import javax.validation.constraints.NotNull;

public record CodeDto(
        @NotNull
        String code
) {
}
