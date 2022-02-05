package io.github.laminalfalah.inventory.payload.filter;

/*
 * Copyright (C) 2022 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.inventory.payload.filter
 *
 * This is part of the inventory.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.laminalfalah.inventory.annotation.QueryParameter;
import io.github.laminalfalah.inventory.model.enums.StatusActive;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laminalfalah on 25/01/22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFilter implements Serializable {

    @QueryParameter
    private String username;

    @QueryParameter
    private String email;

    @QueryParameter(parameter = "phone")
    @JsonProperty("phone")
    private String phoneNumber;

    @QueryParameter(enumClass = StatusActive.class)
    private StatusActive active;

    @QueryParameter
    @JsonProperty("first_name")
    private String firstName;

    @QueryParameter
    @JsonProperty("middle_name")
    private String middleName;

    @QueryParameter
    @JsonProperty("last_name")
    private String lastName;

}
